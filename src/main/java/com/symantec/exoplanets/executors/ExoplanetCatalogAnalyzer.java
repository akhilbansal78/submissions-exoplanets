

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.executors;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.symantec.exoplanets.executors.jobs.ExoplanetWithHottestStarJob;
import com.symantec.exoplanets.executors.jobs.ExoplanetsTimelineJob;
import com.symantec.exoplanets.executors.jobs.OrphanedExoplanetsJob;
import com.symantec.exoplanets.executors.state.ExoplanetWithHottestStarState;
import com.symantec.exoplanets.executors.state.ExoplanetsTimelineState;
import com.symantec.exoplanets.executors.state.OrphanedExoplanetsState;
import com.symantec.exoplanets.model.ExoplanetData;
import com.symantec.exoplanets.reader.ExoplanetDataReader;


/**
 * <p>
 * The main class to run the Executors based solution to process exoplanets data
 * set. This class finds - the count of orphaned planets, the planet with the hottest
 * star and the exoplanets timeline based on the year of discovery and size of the
 * planet.</p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetCatalogAnalyzer {

    // CONSTANTS ------------------------------------------------------
    public static final String DEFAULT_SAMPLE_DATA_URL = "https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
    
    public static final int DEFAULT_THREAD_POOL_SIZE = 10;

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * Main method to start and orchestrate the flink jobs.
     * </p>
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // get the list of exoplanets
        String url = (args.length >= 1) ? args[0] : DEFAULT_SAMPLE_DATA_URL;
        List<ExoplanetData> exoplanetsDataSet = ExoplanetDataReader.getExoplanetDataReader(url).getExoplanetDataSet();

        int nThreads = (args.length == 2) ? Integer.valueOf(args[1]) : DEFAULT_THREAD_POOL_SIZE;
        (new ExoplanetCatalogAnalyzer()).processData(exoplanetsDataSet, nThreads);
    }

    /**
     * <p>
     * This method process the incoming data set of exoplanets to generate the
     * report for - number of orphaned planets, planet with the hottest star and
     * exoplanets timeline for year of discovery and size.
     * </p>
     * 
     * @param exoplanetsDataSet
     */
    public void processData(List<ExoplanetData> exoplanetsDataSet, int nThreads) {
        // create an executors pool for our purpose
        ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);

        // process exoplanet records as independent jobs
        OrphanedExoplanetsState orphanedExoplanetState = new OrphanedExoplanetsState();
        ExoplanetWithHottestStarState exoplanetWithHottestStarState = new ExoplanetWithHottestStarState();
        ExoplanetsTimelineState exoplanetsTimelineState = new ExoplanetsTimelineState();
        for (ExoplanetData e : exoplanetsDataSet) {
            // job - orphaned planets count
            threadPool.execute(new OrphanedExoplanetsJob(orphanedExoplanetState, e));

            // job - find planet with hottest host star
            threadPool.execute(new ExoplanetWithHottestStarJob(exoplanetWithHottestStarState, e));

            // job - process exoplanets timeline
            threadPool.execute(new ExoplanetsTimelineJob(exoplanetsTimelineState, e));
        }

        // wait completion of all jobs
        this.shutdownAndAwaitTermination(threadPool);

        // print output
        System.out.println(orphanedExoplanetState);
        System.out.println();

        System.out.println(exoplanetWithHottestStarState);
        System.out.println();

        System.out.println(exoplanetsTimelineState);
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------
    /**
     * <p>
     * Shutdown the thread pool and wait termination of all executing jobs.
     * </p>
     * 
     * @param pool
     */
    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    // ACCESSOR METHODS -----------------------------------------------

}

