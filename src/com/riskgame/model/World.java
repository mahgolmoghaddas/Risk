package com.riskgame.model;
import java.util.*;

/**
 * The class help to build the world and defines the properties to define the world map.
 */
public class World {

    private String mapName, mapPath;
    private ArrayList<Continent> continents;



    public World() {
        continents = new ArrayList<Continent>();
        mapPath = "";
        mapName = "";

    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public List<Continent> getContinents() {
        return continents;
    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public void setContinents(ArrayList<Continent> continents) {
        this.continents = continents;
    }

    public String getMapPath() {
        return mapPath;
    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public String getMapName() {
        return mapName;
    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * List of country names.
     *
     * @return the list
     */
    public List<String> countryNamesList() {
        List<String> countryNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            for (Country country : continent.getContainedCountries()) {
                countryNames.add(country.getCountryName());
            }
        }
        return countryNames;
    }


    /**
     * List of continent names.
     *
     * @return the list
     */
    public List<String> continentNamesList() {
        List<String> continentNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            continentNames.add(continent.getContinentName());
        }
        return continentNames;
    }

}
