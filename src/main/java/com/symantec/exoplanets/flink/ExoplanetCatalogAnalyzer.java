

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.flink;


import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextOutputFormat.TextFormatter;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem.WriteMode;

import com.symantec.exoplanets.flink.jobs.OrphanedExoplanetFunction;
import com.symantec.exoplanets.flink.jobs.ExoplanetHostStarTempFunction;
import com.symantec.exoplanets.flink.jobs.ExoplanetsTimelineProcessorFunction;
import com.symantec.exoplanets.model.ExoplanetData;
import com.symantec.exoplanets.reader.ExoplanetDataReader;


/**
 * <p>
 * The main class to run the Flink solution to process exoplanets data set. This
 * class finds - the count of orphaned planets, the planet with the hottest star
 * and the exoplanets timeline based on the year of discovery and size of the
 * planet.</p>
 * 
 * @author Akhil_Bansal
 */
public class ExoplanetCatalogAnalyzer {

    // CONSTANTS ------------------------------------------------------
    public static final String DEFAULT_SAMPLE_DATA_URL = "https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";

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
        // Set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        ExoplanetDataReader exoplanetDataReader;
        if (params.has("input")) {
            exoplanetDataReader = ExoplanetDataReader.getExoplanetDataReader(params.get("input"));
        } else {
            exoplanetDataReader = ExoplanetDataReader.getExoplanetDataReader(DEFAULT_SAMPLE_DATA_URL);
        }
        DataSet<ExoplanetData> exoplanetsDataSet = env.fromCollection(exoplanetDataReader.getExoplanetDataSet());

        // print count of orphaned planets
        DataSet<Long> orphanedPlanetsCount = env.fromElements(exoplanetsDataSet.filter(new OrphanedExoplanetFunction()).count());

        // find planet with hottest host star
        DataSet<Tuple2<String, Integer>> exoplanetWithHottestHostStar = exoplanetsDataSet.flatMap(new ExoplanetHostStarTempFunction()).maxBy(1);

        // process exoplanets timeline
        DataSet<Tuple5<Integer, Integer, Integer, Integer, Integer>> exoplanetsTimelineDataSet = exoplanetsDataSet.flatMap(new ExoplanetsTimelineProcessorFunction()).groupBy(0)
                .sum(1).andSum(2).andSum(3).andSum(4)
                .sortPartition(0, Order.ASCENDING).setParallelism(1);

        // print output - on file or stdout
        if (params.has("output")) {
            orphanedPlanetsCount.writeAsFormattedText(params.get("output") + "1", WriteMode.OVERWRITE, getOrphanedExoplanetsCountOutputFormatter());

            exoplanetWithHottestHostStar.writeAsFormattedText(params.get("output") + "2", WriteMode.OVERWRITE, getExoplanetWithHottestHostStarOutputFormatter());

            exoplanetsTimelineDataSet.writeAsFormattedText(params.get("output") + "3", WriteMode.OVERWRITE, getExoplanetsTimelineOutputFormatter());

            env.execute();
        } else {
            orphanedPlanetsCount.print();
            System.out.println();
            System.out.println();

            exoplanetWithHottestHostStar.print();
            System.out.println();
            System.out.println();

            exoplanetsTimelineDataSet.print();
        }
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------
    private static TextFormatter<Long> getOrphanedExoplanetsCountOutputFormatter() {
        return new TextFormatter<Long>() {
            @Override
            public String format(Long c) {
                return "Number of orphaned planets: " + c;
            }
        };
    }

    private static TextFormatter<Tuple2<String, Integer>> getExoplanetWithHottestHostStarOutputFormatter() {
        return new TextFormatter<Tuple2<String, Integer>>() {
            @Override
            public String format(Tuple2<String, Integer> t) {
                return "Planet Identifier: " + t.f0 + ", Temp of Host Star: " + t.f1;
            }
        };
    }

    private static TextFormatter<Tuple5<Integer, Integer, Integer, Integer, Integer>> getExoplanetsTimelineOutputFormatter() {
        return new TextFormatter<Tuple5<Integer, Integer, Integer, Integer, Integer>>() {
            @Override
            public String format(Tuple5<Integer, Integer, Integer, Integer, Integer> t) {
                return "Year: " + ((t.f0 == -1) ? "Unknown" : t.f0) + ", S: " + t.f1 + ", M: " + t.f2 + ", L: " + t.f3 + ", U: " + t.f4;
            }
        };
    }

    // ACCESSOR METHODS -----------------------------------------------

}

