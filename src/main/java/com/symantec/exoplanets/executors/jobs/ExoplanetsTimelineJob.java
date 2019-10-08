

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;


import com.symantec.exoplanets.executors.state.ExoplanetsTimelineState;
import com.symantec.exoplanets.model.ExoplanetData;


/**
 * <p>
 * This job process the exoplanet data for exoplanets timeline, recording its
 * state in the shared ExoplanetsTimelineState instance.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetsTimelineJob implements Runnable {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private final ExoplanetsTimelineState exoplanetsTimelineState;
    private final ExoplanetData e;

    // CONSTRUCTORS ---------------------------------------------------
    public ExoplanetsTimelineJob(ExoplanetsTimelineState exoplanetsTimelineState, ExoplanetData e) {
        this.exoplanetsTimelineState = exoplanetsTimelineState;
        this.e = e;
    }

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * This method updates the current exoplanets timeline state for this exoplanet.
     * </p>
     */
    @Override
    public void run() {
        String size = (e.getPlanetaryMassJpt() == null) ? null : (e.getPlanetaryMassJpt() < 1) ? "1" : (e.getPlanetaryMassJpt() < 2) ? "2" : "3";
        this.exoplanetsTimelineState.increment(e.getDiscoveryYear(), size);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

