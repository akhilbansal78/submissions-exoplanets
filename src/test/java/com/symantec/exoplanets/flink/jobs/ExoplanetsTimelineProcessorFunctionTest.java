

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.util.Collector;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExoplanetsDataProvider;
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;


public class ExoplanetsTimelineProcessorFunctionTest {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Tested ExoplanetsTimelineProcessorFunction exoplanetsTimelineProcessorFunction;

    @Mocked Collector<Tuple5<Integer, Integer, Integer, Integer, Integer>> collector;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test(dataProvider = "exoplanets-timeline", dataProviderClass = ExoplanetsDataProvider.class)
    public void testFlatMap(final List<ExoplanetData> exoplanetsDataSet) throws Exception {
        final List<Tuple5<Integer, Integer, Integer, Integer, Integer>> collectTuples = new ArrayList<Tuple5<Integer, Integer, Integer, Integer, Integer>>();

        // expectations
        new Expectations() {{
            collector.collect(withCapture(collectTuples)); times = exoplanetsDataSet.size();
        }};

        // replay
        for (ExoplanetData e : exoplanetsDataSet) {
            this.exoplanetsTimelineProcessorFunction.flatMap(e, collector);
        }

        // verify
        new Verifications() {{
            collector.collect((Tuple5<Integer, Integer, Integer, Integer, Integer>) any); times = exoplanetsDataSet.size();
        }};
        for (Tuple5<Integer, Integer, Integer, Integer, Integer> t : collectTuples) {
            Assert.assertTrue(t.f0 >= -1, "Year in the tuple should be either -1 or a positive integer");
            Assert.assertTrue(t.f1 + t.f2 + t.f3 + t.f4 == 1, "Planet should be classified only in one size bucket");
        }
    }

    @Test
    public void testFlatMapSmall() throws Exception {
        // expectations
        new Expectations() {{
            collector.collect(new Tuple5<Integer, Integer, Integer, Integer, Integer>(-1, 1, 0, 0, 0));
        }};

        // replay
        ExoplanetData e = new ExoplanetData();
        e.setPlanetaryMassJpt(0.3f);
        this.exoplanetsTimelineProcessorFunction.flatMap(e, collector);

        // verify
        new Verifications() {{
            collector.collect((Tuple5<Integer, Integer, Integer, Integer, Integer>) any); times = 1;
        }};
    }

    @Test
    public void testFlatMapMedium() throws Exception {
        // expectations
        new Expectations() {{
            collector.collect(new Tuple5<Integer, Integer, Integer, Integer, Integer>(-1, 0, 1, 0, 0));
        }};

        // replay
        ExoplanetData e = new ExoplanetData();
        e.setPlanetaryMassJpt(1f);
        this.exoplanetsTimelineProcessorFunction.flatMap(e, collector);

        // verify
        new Verifications() {{
            collector.collect((Tuple5<Integer, Integer, Integer, Integer, Integer>) any); times = 1;
        }};
    }

    @Test
    public void testFlatMapLarge() throws Exception {
        // expectations
        new Expectations() {{
            collector.collect(new Tuple5<Integer, Integer, Integer, Integer, Integer>(-1, 0, 0, 1, 0));
        }};

        // replay
        ExoplanetData e = new ExoplanetData();
        e.setPlanetaryMassJpt(2f);
        this.exoplanetsTimelineProcessorFunction.flatMap(e, collector);

        // verify
        new Verifications() {{
            collector.collect((Tuple5<Integer, Integer, Integer, Integer, Integer>) any); times = 1;
        }};
    }

    @Test
    public void testFlatMapUnknown() throws Exception {
        // expectations
        new Expectations() {{
            collector.collect(new Tuple5<Integer, Integer, Integer, Integer, Integer>(-1, 0, 0, 0, 1));
        }};

        // replay
        ExoplanetData e = new ExoplanetData();
        this.exoplanetsTimelineProcessorFunction.flatMap(e, collector);

        // verify
        new Verifications() {{
            collector.collect((Tuple5<Integer, Integer, Integer, Integer, Integer>) any); times = 1;
        }};
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

