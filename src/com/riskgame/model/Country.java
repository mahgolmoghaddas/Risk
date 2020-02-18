package com.riskgame.model;

import java.util.*;

/**
 * Creating a new country and defining the properties
 *
 */

public class Country {
    private String countryName;
    private ArrayList<String> neighbors;
    private Player owner;
    private int armyQuantity;
    private double X, Y;

    /**
     * Constructor of Country

     * @param countryName the country name
     * @param continent the continent associated with the country
     */
    public Country(String countryName, Continent continent) {
        this.setCountryName(countryName);
        this.neighbors = new ArrayList<String>();
        this.armyQuantity=0;

    }


    /**
     * Getter for naming the Country.
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * Setter for sset the Country name.
     * @param countryName Country Name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for owner of the Country.
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Setter for owner of the Country
     * @param owner owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Getter for Quantity of armies in the Country
     * @return armyQuantity
     */
    public int getQuantityOfArmies() {
        return this.armyQuantity;
    }

    /**
     * Setter for assigning the quantity of the armies in the Country.
     * @param armyQuantity quantity of armies
     */
    public void setQuantityOfArmies(int armyQuantity) {
        this.armyQuantity = armyQuantity;
    }

    /**
     * Getter for determining neighbors.
     * @return neighbors.
     */
    public List<String> getNeighbors() {
        return neighbors;
    }

    /**
     * Setter for determining neighbors.
     * @param neighbors list of neighbours
     */
    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Getter for X attribute.
     * @return X
     */
    public double getCoordinateX() {
        return X;
    }

    /**
     * Setter for X attribute.
     * @param X X value
     */
    public void setCoordinateX( double X) {
        X = X;
    }

    /**
     * Getter for Y attribute.
     * @return Y
     */
    public double getCoordianteY() {
        return Y;
    }

    /**
     * Setter for Y attribute.
     * @param Y Y value
     */
    public void setCoordinateY(double Y) {
        Y = Y;
    }


    /**
     * Adds the quantity of armies  associated with a country.
     */
    public void addToCountryQuant() {
        this.armyQuantity++;
    }

    /**
     * Removes quantity of armies  associated with a country.
     */
    public void minusToCountryQuant() {
        this.armyQuantity--;
    }

}

