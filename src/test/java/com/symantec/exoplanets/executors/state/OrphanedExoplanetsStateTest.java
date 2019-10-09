

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors.state;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

import mockit.Deencapsulation;
import mockit.Tested;



public class OrphanedExoplanetsStateTest {

    // CONSTANTS ------------------------------------------------------
    public static final int NUM_THREADS = 2000;

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @Tested OrphanedExoplanetsState orphanedExoplanetState;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    @Test
    public void testIncrement() throws Exception {
        // replay
        orphanedExoplanetState.increment();

        // verifications
        Assert.assertEquals(Deencapsulation.getField(orphanedExoplanetState, "count"), Integer.valueOf(1));
    }

    @Test
    public void testIncrementConcurrency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean running = new AtomicBoolean();
        AtomicInteger overlaps = new AtomicInteger();

        // setup jobs
        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            service.submit(() -> {
                if (running.get()) {
                    overlaps.incrementAndGet();
                }
                running.set(true);
                orphanedExoplanetState.increment();
                running.set(false);
            });
        }
        // unleash all threads
        latch.countDown();
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        Assert.assertEquals(Deencapsulation.getField(orphanedExoplanetState, "count"), Integer.valueOf(NUM_THREADS));
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

