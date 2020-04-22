package com.riskgame.model;

import java.io.Serializable;
import java.util.*;

import com.riskgame.utility.CardType;

/**
 * The class help to build the world and defines the properties to define the
 * world map.
 */
public class World implements Serializable{

	private static final long serialVersionUID = 5938160142339842530L;
	private HashSet<Continent> continents;
	private String author;
	private String image;
	private String wrap;
	private String scroll;
	private String warn;
	private String mapName;
	private HashSet<Territory> territorySet;
	private HashMap<String,Territory> territoryMap;

	public World(HashSet<Continent> continents) {
		this.continents = continents;
		setTerritorySet();
	}
	public World() {
		continents=new HashSet<Continent>();
		author="";

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
	 * @return the HahSet
	 */
	public HashSet<Territory> getTerritories() {
		return this.territorySet;
	}

	private void setTerritorySet() {
		territorySet = new HashSet<Territory>();
		territoryMap = new HashMap<String,Territory>();
		if (this.continents != null && !this.continents.isEmpty()) {
			Iterator<Continent> continentIterator = this.continents.iterator();

			while (continentIterator.hasNext()) {

				HashSet<Territory> territories = continentIterator.next().getTerritoryList();

				if (territories != null && !territories.isEmpty()) {

					territorySet.addAll(territories);
					
					Iterator<Territory> territoryIterator = territorySet.iterator();
					
					while(territoryIterator.hasNext()) {
						Territory territory= territoryIterator.next();
						territoryMap.put(territory.getCountryName(), territory);
					}
					
				}
			}
		}
	}
	
	public Territory getTerritoryByName(String territoryName) {
		Territory territory = null;
		
		if(territoryMap!=null && !territorySet.isEmpty()) {
			territory = territoryMap.get(territoryName);
		}
		return territory;
	}
	
    public void addContinent(Continent continent){
    	
    	getContinents().add(continent);
    	
    }
    
    

    /**
     * Getter for name
     * @return name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Setter for Name
     * @param mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
    
    
    public void removeContinent(Continent continent) {
        getContinents().remove(continent);
    }
	
    

}
