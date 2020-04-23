package com.riskgame.model;

import java.io.Serializable;

/**
 * This class maintains the score of the player in terms of number of territories occupied by player and the number of reinforcements as received by the Player.
 * @author gauta
 *
 */
public class Score implements Serializable {

	private static final long serialVersionUID = -2805593872256048617L;

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
