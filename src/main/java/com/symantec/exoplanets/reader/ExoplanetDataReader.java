

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.reader;


import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.symantec.exoplanets.model.ExoplanetData;
import com.symantec.exoplanets.utils.JSONHelper;


public class ExoplanetDataReader {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    private final List<ExoplanetData> exoplanetData;

    // CONSTRUCTORS ---------------------------------------------------
    public ExoplanetDataReader(URL url) throws Exception {
        String exoplanetsCatalog = IOUtils.toString(url, StandardCharsets.UTF_8);
        this.exoplanetData = JSONHelper.convertJsonToCollection(exoplanetsCatalog, List.class, ExoplanetData.class);
    }

    public ExoplanetDataReader(File file) throws Exception {
        String exoplanetsCatalog = IOUtils.toString(new FileReader(file));
        this.exoplanetData = JSONHelper.convertJsonToCollection(exoplanetsCatalog, List.class, ExoplanetData.class);
    }

    // PUBLIC METHODS -------------------------------------------------
    public static ExoplanetDataReader getExoplanetDataReader(String url) throws Exception {
        if (url == null) url = "sample_data\\exoplanets.json";
        if (url.startsWith("http")) {
            return new ExoplanetDataReader(new URL(url));
        }
        return new ExoplanetDataReader(new File(url));
    }

    public List<ExoplanetData> getExoplanetDataSet() {
        return this.exoplanetData;
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

