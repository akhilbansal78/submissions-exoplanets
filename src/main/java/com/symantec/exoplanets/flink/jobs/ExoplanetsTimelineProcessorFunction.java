

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.util.Collector;

import com.symantec.exoplanets.model.ExoplanetData;


/**
 * <p>
 * This functions process each exoplanet record to extract the year of discovery,
 * its classification bucket in terms of mass as compared to Jupiter, and a count
 * variable for the extracting the count.
 * </p>
 * 
 * <p>
 * Based on the data set, there are planets where year of discovery is unknown.
 * All such planets will be classified in a single bucket. Also there are planets
 * where their mass is unknown. For every year, such planets will also be classified
 * in a separate bucket.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetsTimelineProcessorFunction implements FlatMapFunction<ExoplanetData, Tuple5<Integer, Integer, Integer, Integer, Integer>> {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * For each exoplanet classify it in the bucket for the discovery year and
     * the size category based on the mass. If the year is unknown, use value of
     * -1. If the mass is unknown, classify the record in the 4th counter.
     * </p>
     */
    @Override
    public void flatMap(ExoplanetData exoplanet, Collector<Tuple5<Integer, Integer, Integer, Integer, Integer>> out) throws Exception {
        int year = (exoplanet.getDiscoveryYear() != null) ? exoplanet.getDiscoveryYear() : -1;
        Tuple5<Integer, Integer, Integer, Integer, Integer> t = (exoplanet.getPlanetaryMassJpt() == null) ? new Tuple5<>(year, 0, 0, 0, 1)
                : (exoplanet.getPlanetaryMassJpt() < 1) ? new Tuple5<>(year, 1, 0, 0, 0)
                        : (exoplanet.getPlanetaryMassJpt() < 2) ? new Tuple5<>(year, 0, 1, 0, 0) : new Tuple5<>(year, 0, 0, 1, 0);
        out.collect(t);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

