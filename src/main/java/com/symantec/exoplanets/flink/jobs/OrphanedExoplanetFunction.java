

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import org.apache.flink.api.common.functions.FilterFunction;

import com.symantec.exoplanets.model.ExoplanetData;


/**
 * <p>
 * This function filters the exoplanets that are orphaned.
 * </p>
 * 
 * @author Akhil_Bansal
 */
public class OrphanedExoplanetFunction implements FilterFunction<ExoplanetData> {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * Returns true for the exoplanets where no details regarding the host star is
     * found. This exoplanet will be classified as an orphaned planet.
     * </p>
     */
    @Override
    public boolean filter(ExoplanetData exoplanet) throws Exception {
        return (exoplanet.getHostStarMassSlrMass() == null && exoplanet.getHostStarRadiusSlrRad() == null
                && exoplanet.getHostStarMetallicity() == null && exoplanet.getHostStarTempK() == null && exoplanet.getHostStarAgeGyr() == null);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

