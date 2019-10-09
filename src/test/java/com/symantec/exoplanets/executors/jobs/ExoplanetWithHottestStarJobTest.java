

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;


import java.util.List;

import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.executors.state.ExoplanetWithHottestStarState;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;


public class ExoplanetWithHottestStarJobTest {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Mocked ExoplanetWithHottestStarState exoplanetWithHottestStarState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test (dataProvider = "exoplanets-hottest-star", dataProviderClass = ExoplanetsDataProvider.class)
    public void testHottestStar(final List<ExoplanetData> exoplanets) throws Exception {
        // replay
        for (ExoplanetData e: exoplanets) {
            ExoplanetWithHottestStarJob exoplanetWithHottestStarJob = new ExoplanetWithHottestStarJob(exoplanetWithHottestStarState, e);
            exoplanetWithHottestStarJob.run();
        }

        new Verifications() {{
            for (ExoplanetData e : exoplanets) {
                exoplanetWithHottestStarState.addExoplanetWithHottestStar(e); times = 1;
            }
        }};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

