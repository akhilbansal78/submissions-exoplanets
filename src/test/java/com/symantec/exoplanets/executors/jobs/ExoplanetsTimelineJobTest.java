

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;


import java.util.List;

import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.executors.state.ExoplanetsTimelineState;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;


public class ExoplanetsTimelineJobTest {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Mocked ExoplanetsTimelineState exoplanetsTimelineJobState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test (dataProvider = "executors-exoplanets-timeline", dataProviderClass = ExoplanetsDataProvider.class)
    public void testExoplanetsTimeline(final List<ExoplanetData> exoplanets, final List<String> sizes) throws Exception {
        // expectations
        new Expectations() {{
            for (int i = 0; i < exoplanets.size(); i++) {
                exoplanetsTimelineJobState.increment(exoplanets.get(i).getDiscoveryYear(), ("NULL".equals(sizes.get(0)) ? null : sizes.get(0)));
            }
        }};

        // replay
        for (ExoplanetData e : exoplanets) {
            ExoplanetsTimelineJob exoplanetsTimelineJob = new ExoplanetsTimelineJob(exoplanetsTimelineJobState, e);
            exoplanetsTimelineJob.run();
        }

        // verifications
        new Verifications() {{
            for (int i = 0; i < exoplanets.size(); i++) {
                exoplanetsTimelineJobState.increment(exoplanets.get(i).getDiscoveryYear(), ("NULL".equals(sizes.get(0)) ? (String) withNull() : sizes.get(0)));
            }
        }};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

