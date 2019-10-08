

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * This state class is used to store the consolidated exoplanets timeline state
 * for the exoplanets being processed.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetsTimelineState {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private final Map<String, Map<String, Integer>> map = new TreeMap<String, Map<String,Integer>>();

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * This method adds a new entry for the year and all sizes. This will overwrite
     * the current entry in the map, if already present.
     * </p>
     * 
     * @param year
     * @param size
     * @param count
     */
    synchronized public void put(Integer year) {
        String y = (year == null) ? "-1" : String.valueOf(year);

        Map<String, Integer> sizeMap = new TreeMap<String, Integer>();
        sizeMap.put("1", 0);
        sizeMap.put("2", 0);
        sizeMap.put("3", 0);
        sizeMap.put("U", 0);
        this.map.put(y, sizeMap);
    }

    /**
     * <p>
     * This method increments the counter for the year and size. If the year and/or
     * size is not present a new counter initialized to 1 is added.
     * </p>
     * 
     * @param year
     * @param size
     */
    synchronized public void increment(Integer year, String size) {
        String y = (year == null) ? "-1" : String.valueOf(year);
        String s = (size == null || size.isEmpty()) ? "U" : size;

        // add a new entry if not already present for the year
        if (!this.map.containsKey(y)) {
            this.put(year);
        }

        // add new entry for size or increment the current value
        Map<String, Integer> sizeMap = this.map.get(y);
        sizeMap.put(s, sizeMap.get(s) + 1);
    }

    /**
     * <p>
     * Returns the string representation of the current timeline.
     * </p>
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Integer>> e : this.map.entrySet()) {
            sb.append("Year: " + getYear(e.getKey()));
            for (Map.Entry<String, Integer> ve : e.getValue().entrySet()) {
                sb.append(", " + getSize(ve.getKey()) + ": " + ve.getValue());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------
    private String getSize(String size) {
        if ("1".equals(size)) return "S";
        if ("2".equals(size)) return "M";
        if ("3".equals(size)) return "L";
        return "U";
    }

    private String getYear(String year) {
        if ("-1".equals(year)) return "Unknown";
        return year;
    }

    // ACCESSOR METHODS -----------------------------------------------

}

