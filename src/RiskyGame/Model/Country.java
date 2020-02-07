package RiskyGame.Model;



import java.util.*;



/**
 * Creating a new country and defining the properties
 *
 */

public class Country {
    private String countryName;
    private List<String> neighbors;
    private Player owner;
    private int armyQuantity;
    private double X;
    private double Y;

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
     * Getter for countryName
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * Setter for countryName attribute.
     * @param countryName Country Name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for owner name
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Setter for owner attribute.
     * @param owner owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Getter for number of armies in the country
     * @return armyQuantity
     */
    public int getNoOfArmiesPresent() {
        return this.armyQuantity;
    }

    /**
     * Setter for setting armies into country.
     * @param noOfArmiesPresent number of armies
     */
    public void setNoOfArmiesPresent(int noOfArmiesPresent) {
        this.armyQuantity = noOfArmiesPresent;
    }

    /**
     * Getter for list of neighbours for the country
     * @return listOfNeighbours
     */
    public List<String> getListOfNeighbours() {
        return neighbors;
    }

    /**
     * Setter for setting neighbours attribute.
     * @param listOfNeighbours list of neighbours
     */
    public void setListOfNeighbours(List<String> listOfNeighbours) {
        this.neighbors = listOfNeighbours;
    }

    /**
     * Getter for X
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
     * Getter for Y
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
     * Adds the quantity of armies  asoociated with a country.
     */
    public void addToCountryQuant() {
        this.armyQuantity++;
    }

    /**
     * Removes quantity of armies  asoociated with a country.
     */
    public void minusToCountryQuant() {
        this.armyQuantity--;
    }

}

