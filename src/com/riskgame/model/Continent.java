package com.riskgame.model;

import java.util.*;

/**
 * Creation of new Continent and defining features and properties.
 */
public class Continent {
	private String continentName;
	private HashSet<Territory> territoryList;
	private double bonusPoint;
	private Territory territory;
	public Continent(String continentName, HashSet<Territory> territoryList, double bonusPoint) {
		this.continentName = continentName;
		this.territoryList = territoryList;
		this.bonusPoint = bonusPoint;
	}



	public Continent() {
		bonusPoint=0;
		territoryList=new HashSet<Territory>();
	}

	/**
	 * getter method for the Bonus value of the Continent.
	 *
	 * @return controlValue
	 */
	public double getBonusPoint() {
		return bonusPoint;
	}

	/**
	 * setter method for setting the Bonus value of the Continent.
	 */
	public void setBonusPoint(double bonusPoint) {
		this.bonusPoint = bonusPoint;
	}

	/**
	 * getter method for the determining the list of the territories contained in the
	 * Continent.
	 * @return containedCountries
	 */
	public HashSet<Territory> getTerritoryList() {
		return territoryList;
	}


	/**
	 * setter method for the determining the list of the territories contained in the
	 * @param territoryList
	 */
	public void setTerritoryList(HashSet territoryList) {
		this.territoryList=territoryList;
	}
	/**
	 * getter method for the name of the continent
	 *
	 * @return continentName
	 */
	public String getContinentName() {
		return continentName;
	}
	/**
	 * setter method for determining continent name
	 * @param continentName
	 */
	public void setContinentName(String continentName) {
		this.continentName=continentName;
	}
	public void setTerritory(Territory territory) {
		this.territory=territory;
	}
	
	public Territory getTerritory() {
		return territory;
	}
	
	public void addTerritory(Territory territory) {
		getTerritoryList().add(territory);
	}
	public void deleteTerritory(Territory territory) {
		getTerritoryList().remove(territory);
	}
	
	public Territory findTerritory(String territory) {
		for(Territory t: getTerritoryList()) {
			if(t.getCountryName().equals(territory)) {
				return t;
			}
		}
		return null;
	}

}
