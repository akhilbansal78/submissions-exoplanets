

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;


/**
 * <p>
 * This state class is used to store the consolidated count of the orphaned
 * planets.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class OrphanedExoplanetsState {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private int count = 0;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * This method increments the current count by 1;
     * </p>
     */
    synchronized public void increment() {
        count++;
    }

    /**
     * <p>
     * Returns the string representation of the current count of orphaned planets.
     * </p>
     */
    public String toString() {
        return "Number of orphaned planets: " + count;
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

