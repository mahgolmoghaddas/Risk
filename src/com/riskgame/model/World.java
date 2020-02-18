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
}
