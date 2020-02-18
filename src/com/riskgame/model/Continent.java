package com.riskgame.model;
import java.util.*;
/**
 * Creation of new Continent and defining features and properties.
 */
public class Continent {
    private String continentName;
    private List<Country> containedCountries;
    private int controlValue;


    public Continent() {
        controlValue = 0;
        containedCountries = new ArrayList<Country>();
    }
    /**
     * getter method for the control value of the Continent.
     * @return controlValue
     */
    public int getControlValue() {
        return controlValue;
    }
    /**
     * setter method for setting the control value of the Continent.
     */
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }
    /**
     * getter method for the determining the list of the countries contained in the Continent.
     * @return containedCountries
     */
    public List<Country> getContainedCountries() {
        return containedCountries;
    }
    /**
     * setter method for setting the list of the countries contained in the Continent.
     */
    public void setContainedCountries(List<Country> containedCountries) {
        this.containedCountries = containedCountries;
    }
    /**
     * getter method for the determining the list of the countries contained in the Continent.
     * @return continentName
     */
    public String getContinentName() {
        return continentName;
    }
    /**
     * setter method for setting the name of the Continent.
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }



}
