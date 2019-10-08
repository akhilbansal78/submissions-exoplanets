

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * <p>
 * This data model class represents the exoplanet records as read from the JSON
 * array of raw data.</p>
 * 
 * <p>
 * The class is designed to ignore missing/undefined attributes.</p>
 * 
 * <p>
 * Note - currently only the required fields are parsed, rest are being ignored.</p>
 * 
 * @author Akhil_Bansal
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExoplanetData {

    // CONSTANTS ------------------------------------------------------

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------
    @JsonProperty("PlanetIdentifier")
    private String planetIdentifier;

    @JsonProperty("PlanetaryMassJpt")
    private Float planetaryMassJpt;

    @JsonProperty("DiscoveryYear")
    private Integer discoveryYear;

    @JsonProperty("HostStarMassSlrMass")
    private Float hostStarMassSlrMass;

    @JsonProperty("HostStarRadiusSlrRad")
    private Float hostStarRadiusSlrRad;

    @JsonProperty("HostStarMetallicity")
    private Float hostStarMetallicity;

    @JsonProperty("HostStarTempK")
    private Integer hostStarTempK;

    @JsonProperty("HostStarAgeGyr")
    private Float hostStarAgeGyr;

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------
    public String getPlanetIdentifier() {
        return planetIdentifier;
    }

    public void setPlanetIdentifier(String planetIdentifier) {
        this.planetIdentifier = planetIdentifier;
    }

    public Float getPlanetaryMassJpt() {
        return planetaryMassJpt;
    }

    public void setPlanetaryMassJpt(Float planetaryMassJpt) {
        this.planetaryMassJpt = planetaryMassJpt;
    }

    public Integer getDiscoveryYear() {
        return discoveryYear;
    }

    public void setDiscoveryYear(Integer discoveryYear) {
        this.discoveryYear = discoveryYear;
    }

    public Float getHostStarMassSlrMass() {
        return hostStarMassSlrMass;
    }

    public void setHostStarMassSlrMass(Float hostStarMassSlrMass) {
        this.hostStarMassSlrMass = hostStarMassSlrMass;
    }

    public Float getHostStarRadiusSlrRad() {
        return hostStarRadiusSlrRad;
    }

    public void setHostStarRadiusSlrRad(Float hostStarRadiusSlrRad) {
        this.hostStarRadiusSlrRad = hostStarRadiusSlrRad;
    }

    public Float getHostStarMetallicity() {
        return hostStarMetallicity;
    }

    public void setHostStarMetallicity(Float hostStarMetallicity) {
        this.hostStarMetallicity = hostStarMetallicity;
    }

    public Integer getHostStarTempK() {
        return hostStarTempK;
    }

    public void setHostStarTempK(Integer hostStarTempK) {
        this.hostStarTempK = hostStarTempK;
    }

    public Float getHostStarAgeGyr() {
        return hostStarAgeGyr;
    }

    public void setHostStarAgeGyr(Float hostStarAgeGyr) {
        this.hostStarAgeGyr = hostStarAgeGyr;
    }

}

