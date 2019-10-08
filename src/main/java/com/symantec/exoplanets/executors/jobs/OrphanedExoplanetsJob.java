

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;

import com.symantec.exoplanets.executors.state.OrphanedExoplanetsState;
import com.symantec.exoplanets.model.ExoplanetData;

/**
 * <p>
 * This job keeps the count of the orphaned planets.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class OrphanedExoplanetsJob implements Runnable {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private final OrphanedExoplanetsState orphanedExoplanetsState;
    private final ExoplanetData e;

    // CONSTRUCTORS ---------------------------------------------------
    public OrphanedExoplanetsJob(OrphanedExoplanetsState orphanedExoplanetsState, ExoplanetData e) {
        this.orphanedExoplanetsState = orphanedExoplanetsState;
        this.e = e;
    }

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * Updates the count of the orphaned planets if this exoplanet is an orphan.
     * </p>
     */
    @Override
    public void run() {
        if (this.e.getHostStarMassSlrMass() == null && this.e.getHostStarRadiusSlrRad() == null
                && this.e.getHostStarMetallicity() == null && this.e.getHostStarTempK() == null && this.e.getHostStarAgeGyr() == null) {

            this.orphanedExoplanetsState.increment();
        }
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

