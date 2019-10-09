

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;


import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.symantec.exoplanets.dataproviders.ExecutorsExoplanetsDataProvider;

import mockit.Deencapsulation;
import mockit.Tested;


public class ExoplanetsTimelineStateTest {

    // CONSTANTS ------------------------------------------------------
    public static final int NUM_THREADS = 1000;

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Tested ExoplanetsTimelineState exoplanetsTimelineState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @BeforeMethod
    public void setup() {
        exoplanetsTimelineState = new ExoplanetsTimelineState();
    }

    @Test(dataProvider = "executors-timeline-years", dataProviderClass = ExecutorsExoplanetsDataProvider.class)
    public void testExoplanetsTimelineStateYears(Integer year, String[] sizes, int s, int m, int l, int u) throws Exception {
        for (String size : sizes) {
            exoplanetsTimelineState.increment(year, size);
        }

        Map<String, Map<String, Integer>> map = (Map<String, Map<String, Integer>>) Deencapsulation.getField(exoplanetsTimelineState, "map");
        String y = (year == null) ? "-1" : String.valueOf(year);
        Assert.assertEquals(map.size(), 1);
        Assert.assertNotNull(map.get(y));
        Assert.assertEquals(map.get(y).size(), 4);
        Assert.assertEquals(map.get(y).get("1").intValue(), s);
        Assert.assertEquals(map.get(y).get("2").intValue(), m);
        Assert.assertEquals(map.get(y).get("3").intValue(), l);
        Assert.assertEquals(map.get(y).get("U").intValue(), u);
    }

    @Test(dataProvider = "executors-timeline-sizes", dataProviderClass = ExecutorsExoplanetsDataProvider.class)
    public void testExoplanetsTimelineStateSizes(String[] sizes, int s, int m, int l, int u) throws Exception {
        for (String size : sizes) {
            exoplanetsTimelineState.increment(null, size);
        }

        Map<String, Map<String, Integer>> map = (Map<String, Map<String, Integer>>) Deencapsulation.getField(exoplanetsTimelineState, "map");
        Assert.assertEquals(map.size(), 1);
        Assert.assertNotNull(map.get("-1"));
        Assert.assertEquals(map.get("-1").size(), 4);
        Assert.assertEquals(map.get("-1").get("1").intValue(), s);
        Assert.assertEquals(map.get("-1").get("2").intValue(), m);
        Assert.assertEquals(map.get("-1").get("3").intValue(), l);
        Assert.assertEquals(map.get("-1").get("U").intValue(), u);
    }

    @Test
    public void testExoplanetsTimelineStateConcurrency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean running = new AtomicBoolean();
        AtomicInteger overlaps = new AtomicInteger();

        // populate random data for each thread
        Integer[] y = new Integer[NUM_THREADS];
        String[] s = new String[NUM_THREADS];
        Map<String, Map<String, Integer>> expectedMap = new TreeMap<String, Map<String, Integer>>(); // expected map
        Random r = new Random();
        for (int i = 0; i < NUM_THREADS; i++) {
            y[i] = (r.nextInt(2) == 0) ? null : r.nextInt(2019) + 1;
            s[i] = (r.nextInt(2) == 0) ? null : String.valueOf(r.nextInt(3) + 1);

            String year = (y[i] == null) ? "-1" : String.valueOf(y[i]);
            if (expectedMap.get(year) == null) {
                Map<String, Integer> sMap = new TreeMap<String, Integer>();
                sMap.put("1", 0);
                sMap.put("2", 0);
                sMap.put("3", 0);
                sMap.put("U", 0);
                expectedMap.put(year, sMap);
            }
            Map<String, Integer> sMap = expectedMap.get(year);
            String size = (s[i] == null ) ? "U" : s[i];
            sMap.put(size, sMap.get(size) + 1);
        }

        // setup jobs
        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            final Integer year = y[i];
            final String size = s[i];
            service.submit(() -> {
                if (running.get()) {
                    overlaps.incrementAndGet();
                }
                running.set(true);
                exoplanetsTimelineState.increment(year, size);
                running.set(false);
            });
        }
        // unleash all thread
        latch.countDown();
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        Map<String, Map<String, Integer>> map = (Map<String, Map<String, Integer>>) Deencapsulation.getField(exoplanetsTimelineState, "map");
        Assert.assertEquals(map.size(), expectedMap.size());
        for (Map.Entry<String, Map<String, Integer>> e : expectedMap.entrySet()) {
            Assert.assertEquals(map.get(e.getKey()), expectedMap.get(e.getKey()));
            Assert.assertEquals(map.get(e.getKey()).size(), expectedMap.get(e.getKey()).size());
            Assert.assertEquals(map.get(e.getKey()).get("1").intValue(), e.getValue().get("1").intValue());
            Assert.assertEquals(map.get(e.getKey()).get("2").intValue(), e.getValue().get("2").intValue());
            Assert.assertEquals(map.get(e.getKey()).get("3").intValue(), e.getValue().get("3").intValue());
            Assert.assertEquals(map.get(e.getKey()).get("U").intValue(), e.getValue().get("U").intValue());
        }
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

