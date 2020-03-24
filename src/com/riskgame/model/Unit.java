package com.riskgame.model;
import java.util.ArrayList;

/**
 * This class is used to maintain territories and methods used to store and retrieve information.
 * @author Himani
 */

public class Unit {

    private String territoryName;
    private int Position_x;
    private int Position_y;
    private ArrayList<String> adjacentTerritories;
    private int armiesHeld;
    private Boolean hasPlayer;
    private String player_name;
    private String continentName;
    
    /**
     * Constructor with parameters.
     * @param territoryName
     * @param Position_x
     * @param Position_y
     * @param armiesHeld
     * @param hasPlayer
     */
    public Unit(String territoryName, int Position_x, int Position_y, int armiesHeld, Boolean hasPlayer) {
        this.territoryName = territoryName;
        this.Position_x = Position_x;
        this.Position_y = Position_y;
        this.armiesHeld = armiesHeld;
        this.hasPlayer = hasPlayer;
    }

    /**
     * Constructor with parameters.
     * @param territoryName
     * @param Position_x
     * @param Position_y
     * @param continentName
     * @param adjacentTerritories
     */
    public Unit(String territoryName, int Position_x, int Position_y, String continentName, ArrayList<String> adjacentTerritories) {
        this.territoryName = territoryName;
        this.Position_x = Position_x;
        this.Position_y = Position_y;
        this.continentName = continentName;
        this.adjacentTerritories = adjacentTerritories;
    }

    /**
     * To set the territory name.
     * @param territoryName
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * To set x-coordinate of the territory.
     * @param Position_x
     */
    public void setX_Position(int Position_x) {
        this.Position_x = Position_x;
    }

    /**
     * To set y-coordinate of the territory.
     * @param Position_y
     */
    public void setY_Position(int Position_y) {
        this.Position_y = Position_y;
    }

    /**
     * To set the number of armies.
     * @param armiesHeld
     */
    public void setArmiesHeld(int armiesHeld) {
        this.armiesHeld = armiesHeld;
    }

    public void setHasPlayer(Boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    /**
     * Set the player occupied the territory.
     * @param player_name
     */
    public void setPlayer(String player_name) {
        this.player_name = player_name;
    }

    /**
     * set the adjacent territories.
     * @param adjacentTerritories
     */
    public void setAdjacentTerritories(ArrayList<String> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
    }

    /**
     * To set the continent name.
     * @param continentName
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * get the territory name.
     * @return territorieName
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * get the x-coordinate.
     * @return Position_x
     */
    public int getX_Position() {
        return Position_x;
    }

    /**
     * get the y-coordinate.
     * @return Position_y
     */
    public int getY_Position() {
        return Position_y;
    }

    /**
     * get the number of armies held by the territory.
     * @return armiesHeld
     */
    public int getArmiesHeld() {
        return armiesHeld;
    }

    /**
     * return true if the territory has a player.
     * @return hasPlayer
     */
    public Boolean getHasPlayer() {
        return hasPlayer;
    }

    /**
     * return the player occupied the territory.
     * @return player
     */
    public String getPlayer() {
        return player_name;
    }

    /**
     * return the adjacent territories.
     * @return adjacentTerritories
     */
    public ArrayList<String> getAdjacentTerritories() {
        return adjacentTerritories;
    }

    /**
     * To return the continent name.
     * @return continentName
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * To increment the army count by 1.
     */
    public void incrementarmyCountby1() {
        armiesHeld += 1;
    }

    /**
     * To decrement army count by 1;
     */
    public void decrementarmyCountby1() {
        armiesHeld -= 1;
    }

    /**
     * To decrement army count by value
     * @param value
     */
    public void decreaseArmyCountByvalue(int value) {
        armiesHeld -= value;
    }

}