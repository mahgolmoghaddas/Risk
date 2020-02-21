package com.riskgame.model;

import java.util.List;
import java.util.*;
import java.util.Map;

/**
 * This class is used for player module which contains variables for name of the
 * Player, Armies it has held etc and methods to change the status of the
 * object.
 * 
 * @author Himani
 * @version 1.0.0.0
 */
public class Player {

	private int player_id;
	private String player_name;
	private int armiesHeld;
	private int armiesFromCards;
	private Score playerScore;
	private ArrayList<Country> countriesOwned;



	public HashMap<String, Continent> getContinentHeld() {
		return continentHeld;
	}

	private List<Card> cardsHeld;
	private HashMap<String, Continent> continentHeld;
	private Integer startDiceNo;

	/**
	 * The parameterized constructor takes player id and name as parameters
	 * 
	 * @param player_id   of type integer which is the Id of the player
	 * @param player_name of type string which is the name of the player
	 */
	public Player(int player_id, String player_name) {
		this.player_id = player_id;
		this.player_name = player_name;
		armiesHeld = 0;
		armiesFromCards = 0;
		countriesOwned=new ArrayList<>();
	}

	/**
	 * This method gets the player Id
	 * 
	 * @return of type integer, is the id of the player
	 */
	public int GetId() {
		return this.player_id;
	}

	/**
	 * This method returns the player name
	 * 
	 * @return of type string, is the name of the player
	 */
	public String GetName() {
		return this.player_name;
	}

	/**
	 * The method to set the armies of the player
	 * 
	 * @param armiesHeld of type integer, will set the armies to the player
	 */
	public void SetArmiesToplayer(int armiesHeld) {
		this.armiesHeld = armiesHeld;
	}

	/**
	 * this method gets the number of armies that the player has
	 * 
	 * @param player_id is the id of the player
	 * @return the number of armies of the player
	 */
	public int GetArmies() {
		return this.armiesHeld;
	}

	/**
	 * The method to set the player's armies
	 * 
	 * @param armiesHeld is the number of new armies
	 */
	public void SetArmies(int armiesHeld) {
		this.armiesHeld = armiesHeld;
	}

	/**
	 * this method adds the armies exchanged with cards
	 * 
	 * @param armiesFromCards, which is the number of armies that will be added to
	 *        the player
	 */
	public void AddArmiesFromCards(int armiesFromCards) {
		this.armiesFromCards += armiesFromCards;
		this.armiesHeld += armiesFromCards;
	}

	/**
	 * this method returns the number of armiesFromCards that the player has
	 * 
	 * @param player_id, id of the player
	 * @return the number of armies of the player
	 */
	public int GetArmiesFromCards() {
		return this.armiesFromCards;
	}

	public Integer getStartDiceNo() {
		return startDiceNo;
	}


	public ArrayList<Country> getCountriesOwned() {
		return countriesOwned;
	}

	public void setCountriesOwned(ArrayList<Country> countriesOwned) {
		this.countriesOwned = countriesOwned;
	}
	public void setStartDiceNo(Integer startUpDiceNo) {
		this.startDiceNo = startUpDiceNo;
	}

	/**
	 * Set number of continents held by the player.
	 * 
	 * @param continentHeld
	 */
	public void setContinentHeld(HashMap<String, Continent> continentHeld) {
		this.continentHeld = continentHeld;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public Score getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(Score playerScore) {
		this.playerScore = playerScore;
	}

	public List<Card> getCardsHeld() {
		return cardsHeld;
	}

	public void setCardsHeld(List<Card> cardsHeld) {
		this.cardsHeld = cardsHeld;
	}

}
