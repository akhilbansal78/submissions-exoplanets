

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink.jobs;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import com.symantec.exoplanets.model.ExoplanetData;


/**
 * <p>
 * This function process the exoplanet record to get the host star temperature. If
 * the host star temperature is not available, it returns -1.</p>
 * 
 * <p>
 * This class will generate a map of tuple (planet ID, host star temp).</p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetHostStarTempFunction implements FlatMapFunction<ExoplanetData, Tuple2<String, Integer>> {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * <p>
     * Flink job to process individual Exoplanet record to get its host star temp. 
     * </p>
     */
    @Override
    public void flatMap(ExoplanetData exoplanet, Collector<Tuple2<String, Integer>> out) throws Exception {
        out.collect(new Tuple2<>(exoplanet.getPlanetIdentifier(), (exoplanet.getHostStarTempK() != null) ? exoplanet.getHostStarTempK() : -1));
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

