

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Tested;


public class OrphanedExoplanetsFunctionTest {

    // CONSTANTS ------------------------------------------------------
    @Tested OrphanedExoplanetFunction orphanedExoplanetFunction;

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test(dataProvider = "exoplanets-not-orphaned", dataProviderClass = ExoplanetsDataProvider.class)
    public void testFilterFalse(ExoplanetData e) throws Exception {
        Assert.assertFalse(this.orphanedExoplanetFunction.filter(e), "Planet should not be classified as orphaned, " + e);
    }

    @Test(dataProvider = "exoplanets-orphaned", dataProviderClass = ExoplanetsDataProvider.class)
    public void testFilterTrue(ExoplanetData e) throws Exception {
        Assert.assertTrue(this.orphanedExoplanetFunction.filter(e), "Planet should be classified as orphaned, " + e);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

