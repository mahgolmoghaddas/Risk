package com.riskgame.model;

import java.awt.Color;
import java.util.*;
/**
 * This class is used for player module which contains variables for name of the
 * Player, Armies it has held etc and methods to change the status of the
 * object.
 * 
 * @author Himani
 * @version 1.0.0.0
 */
public class Player extends Observable {

	private int playerId;
	private String playerName;
	private Color color;
	private int armiesHeld;
	private Score playerScore;
	private HashSet<Territory> countriesOwned;
	private HashSet<Continent> continentsOwned;
	private List<Card> cardsHeld;
	private Integer startDiceNo;

	/**
	 * The parameterized constructor takes player id and name as parameters
	 * 
	 * @param playerId   of type integer which is the Id of the player
	 * @param playerName of type string which is the name of the player
	 */
	public Player(int playerId, String playerName) {
		this.playerId = playerId;
		this.playerName = playerName;
		armiesHeld = 0;
		countriesOwned = new HashSet<>();
		continentsOwned = new HashSet<>();
		cardsHeld = new ArrayList<Card>();
		playerScore = new Score();
	}

	/**
	 * This method gets the player Id
	 * 
	 * @return of type integer, is the id of the player
	 */
	public int getId() {
		return this.playerId;
	}

	/**
	 * This method returns the player name
	 * 
	 * @return of type string, is the name of the player
	 */
	public String getName() {
		return this.playerName;
	}

	/**
	 * The method to set the armies of the player
	 * 
	 * @param armiesHeld of type integer, will set the armies to the player
	 */
	public void setArmiesToplayer(int armiesHeld) {
		this.armiesHeld = armiesHeld;
	}

	/**
	 * this method gets the number of armies that the player has
	 * 
	 * @param playerId is the id of the player
	 * @return the number of armies of the player
	 */
	public int getArmiesHeld() {
		return this.armiesHeld;
	}

	/**
	 * The method to set the player's armies
	 * 
	 * @param armiesHeld is the number of new armies
	 */
	public void setArmiesHeld(int armiesHeld) {
		this.armiesHeld = armiesHeld;
	}

	/**
	 * this method adds the armies exchanged with cards
	 * 
	 * @param armiesFromCards, which is the number of armies that will be added to
	 *                         the player
	 */
	public void addArmiesFromCards(int armiesFromCards) {
		this.armiesHeld += armiesFromCards;
		playerDataChanged();		
	}

	public Integer getStartDiceNo() {
		return startDiceNo;
	}

	public void setStartDiceNo(Integer startUpDiceNo) {
		this.startDiceNo = startUpDiceNo;
		playerDataChanged();
	}

	public HashSet<Territory> getCountriesOwned() {
		return countriesOwned;
	}
	

	public void setCountriesOwned(HashSet<Territory> countriesOwned) {
		this.countriesOwned = countriesOwned;
		playerDataChanged();
	}

	public Score getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(Score playerScore) {
		this.playerScore = playerScore;
		playerDataChanged();
	}

	public List<Card> getCardsHeld() {
		return cardsHeld;
	}

	public void setCardsHeld(List<Card> cardsHeld) {
		this.cardsHeld = cardsHeld;
		playerDataChanged();
	}

	public String getPlayerName() {
		return playerName;
	}

	public HashSet<Continent> getContinentsOwned() {
		return continentsOwned;
	}

	public void setContinentsOwned(HashSet<Continent> continentsOwned) {
		this.continentsOwned = continentsOwned;
		playerDataChanged();
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void playerDataChanged() {
		System.out.println("Notifying the observers "+this.countObservers());
		setChanged();
		notifyObservers();
	}
	
	

}
