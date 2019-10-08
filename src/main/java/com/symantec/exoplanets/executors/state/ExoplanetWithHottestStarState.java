

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;

import com.symantec.exoplanets.model.ExoplanetData;

/**
 * <p>
 * This state class is used to store the current state for the job to process
 * exoplanets catalog to find the planet with the hottest star.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetWithHottestStarState {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private ExoplanetData exoplanet = null;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * This method updates the planet with the hottest host star if the specified
     * planet has hotter host star
     * </p>
     * 
     * @param exoplanet
     */
    synchronized public void addExoplanetWithHottestStar(ExoplanetData exoplanet) {
        if ((this.exoplanet == null) || ((exoplanet.getHostStarTempK() != null) && (exoplanet.getHostStarTempK() > this.exoplanet.getHostStarTempK()))) {
            this.exoplanet = exoplanet;
        }
    }

    /**
     * <p>
     * Returns the string representation for the current planet with hottest host
     * star.</p>
     */
    public String toString() {
        return "Planet Identifier: " + exoplanet.getPlanetIdentifier() + ", Temp of Host Star: " + exoplanet.getHostStarTempK();
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

