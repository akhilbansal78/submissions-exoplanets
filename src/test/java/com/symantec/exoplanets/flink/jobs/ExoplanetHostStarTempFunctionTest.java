

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import java.util.List;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;


public class ExoplanetHostStarTempFunctionTest {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Tested ExoplanetHostStarTempFunction exoplanetHostStarTempFunction;

    @Mocked Collector<Tuple2<String, Integer>> collector;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test(dataProvider = "exoplanets-hottest-star", dataProviderClass = ExoplanetsDataProvider.class)
    public void testFlatMap(final List<ExoplanetData> exoplanetsDataSet) throws Exception {
        // expectations
        new Expectations() {{
            for (ExoplanetData e : exoplanetsDataSet) {
                collector.collect(new Tuple2<>(e.getPlanetIdentifier(), (e.getHostStarTempK() == null) ? -1 : e.getHostStarTempK()));
            }
        }};

        // replay
        for (ExoplanetData e : exoplanetsDataSet) {
            exoplanetHostStarTempFunction.flatMap(e, collector);
        }

        // verifications
        new Verifications() {{
            collector.collect((Tuple2<String, Integer>) any); times = exoplanetsDataSet.size();
        }};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

