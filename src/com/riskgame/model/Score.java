package com.riskgame.model;

/**
 * This class maintains the score of the player in terms of number of territories occupied by player and the number of reinforcements as received by the Player.
 * @author gauta
 *
 */
public class Score {

	private int noOfOccupiedTerritories;

	private int noOfReinforcements;
	
	public int getNoOfReinforcements() {
		return noOfReinforcements;
	}

	public void setNoOfReinforcements(int noOfReinforcements) {
		this.noOfReinforcements = noOfReinforcements;
	}

	public int getNoOfOccupiedTerritories() {
		return noOfOccupiedTerritories;
	}

	public void setNoOfOccupiedTerritories(int noOfOccupiedTerritories) {
		this.noOfOccupiedTerritories = noOfOccupiedTerritories;
	}
	
}
