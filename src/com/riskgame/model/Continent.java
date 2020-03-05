package com.riskgame.model;

import java.util.*;

/**
 * Creation of new Continent and defining features and properties.
 */
public class Continent {
	private String continentName;
	private List<Territory> territoryList;
	private double bonusPoint;

	public Continent(String continentName, List<Territory> territoryList, double bonusPoint) {
		this.continentName = continentName;
		this.territoryList = territoryList;
		this.bonusPoint = bonusPoint;
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
	public List<Territory> getTerritoryList() {
		return territoryList;
	}

	/**
	 * getter method for the determining the list of the territories contained in the
	 * Continent.
	 * 
	 * @return continentName
	 */
	public String getContinentName() {
		return continentName;
	}

}
