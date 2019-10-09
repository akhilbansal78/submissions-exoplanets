

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.dataproviders;


import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.symantec.exoplanets.model.ExoplanetData;


public class ExoplanetsDataProvider {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @DataProvider(name = "exoplanets-hottest-star")
    public static Object[][] exoplanetsHottestStarDataProvider() {
        List<ExoplanetData> emptyDataSet = new ArrayList<ExoplanetData>();

        List<ExoplanetData> exoplanetsDataSet = new ArrayList<ExoplanetData>();
        ExoplanetData e1 = new ExoplanetData();
        e1.setPlanetIdentifier("A");
        exoplanetsDataSet.add(e1);

        ExoplanetData e2 = new ExoplanetData();
        e2.setPlanetIdentifier("B");
        e2.setHostStarTempK(1);
        exoplanetsDataSet.add(e2);

        ExoplanetData e3 = new ExoplanetData();
        e3.setPlanetIdentifier("C");
        e3.setHostStarTempK(5);
        exoplanetsDataSet.add(e3);

        return new Object[][] {{emptyDataSet}, {exoplanetsDataSet}};
    }

    @DataProvider(name = "exoplanets-timeline")
    public static Object[][] exoplanetsTimelineDataProvider() {
        List<ExoplanetData> emptyDataSet = new ArrayList<ExoplanetData>();

        List<ExoplanetData> exoplanetsDataSet = new ArrayList<ExoplanetData>();
        ExoplanetData e1 = new ExoplanetData();
        e1.setPlanetIdentifier("A");
        e1.setPlanetaryMassJpt(0.3f);
        exoplanetsDataSet.add(e1);

        ExoplanetData e2 = new ExoplanetData();
        e2.setPlanetIdentifier("B");
        e2.setPlanetaryMassJpt(1f);
        exoplanetsDataSet.add(e2);

        ExoplanetData e3 = new ExoplanetData();
        e3.setPlanetIdentifier("C");
        e3.setPlanetaryMassJpt(2f);
        exoplanetsDataSet.add(e3);

        ExoplanetData e4 = new ExoplanetData();
        e4.setPlanetIdentifier("D");
        exoplanetsDataSet.add(e4);

        return new Object[][] {{emptyDataSet}, {exoplanetsDataSet}};
    }

    @DataProvider(name = "executors-exoplanets-timeline")
    public static Object[][] executorsExoplanetsTimelineDataProvider() {
        List<ExoplanetData> emptyDataSet = new ArrayList<ExoplanetData>();
        List<String> emptySizes = new ArrayList<String>();

        List<ExoplanetData> exoplanetsDataSet = new ArrayList<ExoplanetData>();
        List<String> sizes = new ArrayList<String>();
        ExoplanetData e1 = new ExoplanetData();
        e1.setPlanetIdentifier("A");
        e1.setPlanetaryMassJpt(0.3f);
        exoplanetsDataSet.add(e1);
        sizes.add("1");

        ExoplanetData e2 = new ExoplanetData();
        e2.setPlanetIdentifier("B");
        e2.setPlanetaryMassJpt(1f);
        exoplanetsDataSet.add(e2);
        List<String> s2 = new ArrayList<String>();
        sizes.add("2");

        ExoplanetData e3 = new ExoplanetData();
        e3.setPlanetIdentifier("C");
        e3.setPlanetaryMassJpt(2f);
        exoplanetsDataSet.add(e3);
        List<String> s3 = new ArrayList<String>();
        sizes.add("3");

        ExoplanetData e4 = new ExoplanetData();
        e4.setPlanetIdentifier("D");
        exoplanetsDataSet.add(e4);
        List<String> s4 = new ArrayList<String>();
        sizes.add("NULL");

        return new Object[][] {{emptyDataSet, emptySizes}, {exoplanetsDataSet, sizes}};
    }

    @DataProvider(name = "exoplanets-not-orphaned")
    public static Object[][] exoplanetsNotOrphaned() {
        ExoplanetData e1 = new ExoplanetData();
        e1.setHostStarAgeGyr(2f);

        ExoplanetData e2 = new ExoplanetData();
        e2.setHostStarMassSlrMass(3f);

        ExoplanetData e3 = new ExoplanetData();
        e3.setHostStarMetallicity(0.3f);

        ExoplanetData e4 = new ExoplanetData();
        e4.setHostStarRadiusSlrRad(4f);

        ExoplanetData e5 = new ExoplanetData();
        e5.setHostStarTempK(5);

        ExoplanetData e6 = new ExoplanetData();
        e6.setHostStarAgeGyr(2f);
        e6.setHostStarMassSlrMass(3f);
        e6.setHostStarMetallicity(0.3f);
        e6.setHostStarRadiusSlrRad(4f);
        e6.setHostStarTempK(5);

        return new Object[][] {{e1}, {e2}, {e3}, {e4}, {e5}, {e6}};
    }

    @DataProvider(name = "exoplanets-orphaned")
    public static Object[][] exoplanetsOrphaned() {
        ExoplanetData e1 = new ExoplanetData();

        return new Object[][] {{e1}};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

