package com.riskgame.model;

import java.util.*;

import com.riskgame.utility.CardType;

/**
 * The class help to build the world and defines the properties to define the
 * world map.
 */
public class World {

	private HashSet<Continent> continents;
	private String author;
	private String image;
	private String wrap;
	private String scroll;
	private String warn;
	private HashSet<Territory> territorySet;

	public World(HashSet<Continent> continents) {
		this.continents = continents;
		setTerritorySet();
	}

	/**
	 * The class help to build the world and defines the properties to define the
	 * world map.
	 */
	public HashSet<Continent> getContinents() {
		return continents;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWrap() {
		return wrap;
	}

	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

	public String getScroll() {
		return scroll;
	}

	public void setScroll(String scroll) {
		this.scroll = scroll;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	/**
	 * This method returns the set of territories in the world
	 * 
	 * @return the Hashset
	 */
	public HashSet<Territory> getTerritories() {
		return this.territorySet;
	}

	private void setTerritorySet() {
		territorySet = new HashSet<Territory>();
		if (this.continents != null && !this.continents.isEmpty()) {
			Iterator<Continent> continentIterator = this.continents.iterator();

			while (continentIterator.hasNext()) {

				HashSet<Territory> territories = continentIterator.next().getTerritoryList();

				if (territories != null && !territories.isEmpty()) {

					territorySet.addAll(territories);
				}
			}
		}
	}

}
