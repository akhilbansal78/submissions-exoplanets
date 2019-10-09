

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;


import java.util.Random;
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
import com.symantec.exoplanets.model.ExoplanetData;

import mockit.Deencapsulation;
import mockit.Tested;


public class ExoplanetWithHottestStarStateTest {

    // CONSTANTS ------------------------------------------------------
    public static final int NUM_THREADS = 1000;

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Tested ExoplanetWithHottestStarState exoplanetWithHottestStarState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------

    @BeforeMethod
    public void setup() {
        exoplanetWithHottestStarState = new ExoplanetWithHottestStarState();
        exoplanetWithHottestStarState.addExoplanetWithHottestStar(ExecutorsExoplanetsDataProvider.getDefaultExoplanetHottestStarState());
    }

    @Test
    public void testAddExoplanetWithHottestStarNull() throws Exception {
        Assert.assertEquals(((ExoplanetData) Deencapsulation.getField(exoplanetWithHottestStarState, "exoplanet")).getHostStarTempK(),
                ExecutorsExoplanetsDataProvider.getDefaultExoplanetHottestStarState().getHostStarTempK());
    }

    @Test (dataProvider = "executors-hottest-star", dataProviderClass = ExecutorsExoplanetsDataProvider.class)
    public void testAddExoplanetWithHottestStar(ExoplanetData e, ExoplanetData expected) throws Exception {
        exoplanetWithHottestStarState.addExoplanetWithHottestStar(e);

        Assert.assertEquals(((ExoplanetData) Deencapsulation.getField(exoplanetWithHottestStarState, "exoplanet")).getHostStarTempK(), expected.getHostStarTempK());
    }

    @Test
    public void testAddExoplanetWithHottestStarConcurrency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean running = new AtomicBoolean();
        AtomicInteger overlaps = new AtomicInteger();

        // populate random data for each thread
        final ExoplanetData[] exoplanets = new ExoplanetData[NUM_THREADS];
        ExoplanetData max = null; // expected max exoplanet
        Random r = new Random();
        for (int i = 0; i < NUM_THREADS; i++) {
            exoplanets[i] = new ExoplanetData();
            exoplanets[i].setHostStarTempK(r.nextInt());
            if ((max == null) || (max.getHostStarTempK() == null) || (max.getHostStarTempK() < exoplanets[i].getHostStarTempK())) {
                max = exoplanets[i];
            }
        }

        // setup jobs
        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            final ExoplanetData e = exoplanets[i];
            service.submit(() -> {
                if (running.get()) {
                    overlaps.incrementAndGet();
                }
                running.set(true);
                exoplanetWithHottestStarState.addExoplanetWithHottestStar(e);
                running.set(false);
            });
        }
        // unleash all threads
        latch.countDown();
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        Assert.assertEquals(Deencapsulation.getField(exoplanetWithHottestStarState, "exoplanet"), max);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

