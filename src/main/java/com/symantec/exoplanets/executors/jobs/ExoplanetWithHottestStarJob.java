

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;


import com.symantec.exoplanets.executors.state.ExoplanetWithHottestStarState;
import com.symantec.exoplanets.model.ExoplanetData;


/**
 * <p>
 * This job process the exoplanet to find the planet with hottest host star.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetWithHottestStarJob implements Runnable {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private final ExoplanetWithHottestStarState exoplanetWithHottestStarState;
    private final ExoplanetData e;

    // CONSTRUCTORS ---------------------------------------------------
    public ExoplanetWithHottestStarJob(ExoplanetWithHottestStarState exoplanetWithHottestStarState, ExoplanetData e) {
        this.exoplanetWithHottestStarState = exoplanetWithHottestStarState;
        this.e = e;
    }

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * This method checks the temp of the host star of this exoplanet with the
     * current state of the hottest star, updating the state if required.
     * </p>
     */
    @Override
    public void run() {
        this.exoplanetWithHottestStarState.addExoplanetWithHottestStar(this.e);
    }
    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

