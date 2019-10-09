

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.jobs;


import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.executors.state.OrphanedExoplanetsState;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;


public class OrpahnedExoplanetsJobTest {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Mocked OrphanedExoplanetsState orphanedExoplanetsState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test (dataProvider = "exoplanets-orphaned", dataProviderClass = ExoplanetsDataProvider.class)
    public void testOrphanedExoplanets(final ExoplanetData e) throws Exception {
        // Expectations
        new Expectations() {{
            orphanedExoplanetsState.increment();
        }};

        // replay
        OrphanedExoplanetsJob orphanedExoplanetsJob = new OrphanedExoplanetsJob(orphanedExoplanetsState, e);
        orphanedExoplanetsJob.run();

        // Verifications
        new Verifications() {{
            orphanedExoplanetsState.increment(); times = 1;
        }};
    }

    @Test (dataProvider = "exoplanets-not-orphaned", dataProviderClass = ExoplanetsDataProvider.class)
    public void testNotOrphanedExoplanets(final ExoplanetData e) throws Exception {
        // replay
        OrphanedExoplanetsJob orphanedExoplanetsJob = new OrphanedExoplanetsJob(orphanedExoplanetsState, e);
        orphanedExoplanetsJob.run();

        // Verifications
        new Verifications() {{
            orphanedExoplanetsState.increment(); times = 0;
        }};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

