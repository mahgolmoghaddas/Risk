
package com.riskgame.model;

import java.util.*;

/**
 * This class gives information about the Territory and methods to set and get its properties
 *
 */

public class Territory {
	private String countryName;
	private HashSet<String> neighborsTerritory;
	private Coordinates territoryPosition;
	private Player owner;
	private int armyCount;

	/**
	 * Creates the territory instance
	 * @param countryName
	 * @param territoryPosition
	 * @param neighborsTerritory
	 */
	public Territory(String countryName, Coordinates territoryPosition, HashSet<String> neighborsTerritory) {
		this.countryName = countryName;
		this.territoryPosition = territoryPosition;
		this.neighborsTerritory = neighborsTerritory;
	}
	public Territory() {
		
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
	public int getArmyCount() {
		return this.armyCount;
	}

	/**
	 * Setter for assigning the quantity of the armies in the Country.
	 * @param armyCount quantity of armies
	 */
	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}

	/**
	 * Getter for determining neighbors.
	 * @return neighbors.
	 */
	public HashSet<String> getNeighborsTerritory() {
		return neighborsTerritory;
	}

	/**
	 * Getter for determining TerritoryPosition.
	 * @return neighbors.
	 */
	public Coordinates getTerritoryPosition() {
		
		return territoryPosition;
	}

	
	
	
	/**
	 * Getter for naming the Country.
	 * @return countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName=countryName;
	}
	public void setTerritoryPosition(Coordinates territoryPosition) {
		// TODO Auto-generated method stub
		this.territoryPosition=territoryPosition;
	}

}
