package com.riskgame.model;
import java.util.*;

/**
 * The class help to build the world and defines the properties to define the world map.
 */
public class World {

    private ArrayList<Continent> continents;
    private String author;
    private String image;
    private String wrap;
    private String scroll;
    private String warn;

    public World(ArrayList<Continent> continents) {
        this.continents = continents;
    }
    /**
     * The class help to build the world and defines the properties to define the world map.
     */
    public List<Continent> getContinents() {
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
     * List of country names.
     *
     * @return the list
     */
    public List<String> countryNamesList() {
        List<String> countryNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
//            for (Territory country : continent.getContainedCountries()) {
//                countryNames.add(country.getCountryName());
//            }
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



    public List<Continent> getOccupiedContinents(String playerName) {
        List<Continent> occupiedContinents=new ArrayList<Continent>();
        for(Continent continent:continents)
        {
            boolean owned=true;
//            for(Territory country:continent.getContainedCountries()) {
//                if(!country.getOwner().getPlayer_name().equals(playerName))
//                {owned=false;
//                    break;
//                }
//            }
            if(owned)
                occupiedContinents.add(continent);

        }
        return occupiedContinents;

    }

    /**
     * Return the continents owned by a particular player.
     *
     * @param playerName the player name
     * @return the list of continents owned by the player
     */
    public List<Territory> getOwnedCountries(String playerName) {
        List<Territory> ocuupiedCountries=new ArrayList<Territory>();
        for(Continent continent:continents)
        {
//            for(Territory country:continent.getContainedCountries()) {
//                if(country.getOwner().getPlayer_name().equals(playerName))
//                {
//                    ocuupiedCountries.add(country);
//                }
//            }

        }
        return ocuupiedCountries;

    }

}
