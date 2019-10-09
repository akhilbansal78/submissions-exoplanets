

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.dataproviders;


import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.symantec.exoplanets.model.ExoplanetData;


public class ExecutorsExoplanetsDataProvider {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------
    private static final ExoplanetData DEFAULT_EXOPLANET_HOTTEST_STAR = new ExoplanetData();
    private static final Integer DEFAULT_TEMP = 20;

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    public static ExoplanetData getDefaultExoplanetHottestStarState() {
        DEFAULT_EXOPLANET_HOTTEST_STAR.setHostStarTempK(DEFAULT_TEMP);
        return DEFAULT_EXOPLANET_HOTTEST_STAR;
    }

    @DataProvider(name = "executors-hottest-star")
    public static Object[][] getExoplanetsDataForHottestStar() {
        ExoplanetData a1 = new ExoplanetData();
        ExoplanetData e1 = DEFAULT_EXOPLANET_HOTTEST_STAR;

        ExoplanetData a2 = new ExoplanetData();
        a2.setHostStarTempK(DEFAULT_TEMP);
        ExoplanetData e2 = DEFAULT_EXOPLANET_HOTTEST_STAR;

        ExoplanetData a3 = new ExoplanetData();
        a3.setHostStarTempK(DEFAULT_TEMP - 1);
        ExoplanetData e3 = DEFAULT_EXOPLANET_HOTTEST_STAR;

        ExoplanetData a4 = new ExoplanetData();
        a4.setHostStarTempK(DEFAULT_TEMP + 1);
        ExoplanetData e4 = a4;

        return new Object[][] {{a1, e1}, {a2, e3}, {a3, e3}, {a4, e4}};
    }

    @DataProvider(name = "executors-timeline-years")
    public static Object[][] getExoplanetsTimelineYearData() {
        Integer[] years = new Integer[2];
        String[][] sizes = new String[2][];

        years[0] = null;
        sizes[0] = new String[4];
        sizes[0][2] = "1";
        sizes[0][3] = "3";
        int s1 = 1;
        int m1 = 0;
        int l1 = 1;
        int u1 = 2;

        years[1] = 2019;
        sizes[1] = new String[5];
        sizes[1][0] = "U";
        sizes[1][1] = "2";
        sizes[1][2] = "2";
        int s2 = 0;
        int m2 = 2;
        int l2 = 0;
        int u2 = 3;

        return new Object[][] {{years[0], sizes[0], s1, m1, l1, u1}, {years[1], sizes[1], s2, m2, l2, u2}};
    }

    @DataProvider(name = "executors-timeline-sizes")
    public static Object[][] getExoplanetsTimelineSizeData() {
        String[][] sizes = new String[2][];

        // no S, M
        sizes[0] = new String[4];
        sizes[0][0] = "3";
        sizes[0][1] = "3";
        sizes[0][2] = "U";

        // no L, U
        sizes[1] = new String[4];
        sizes[1][0] = "1";
        sizes[1][1] = "1";
        sizes[1][2] = "2";
        sizes[1][3] = "2";

        return new Object[][] {{sizes[0], 0, 0, 2, 2}, {sizes[1], 2, 2, 0, 0}};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

