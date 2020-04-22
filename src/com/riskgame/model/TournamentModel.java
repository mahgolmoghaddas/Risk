package com.riskgame.model;

import java.util.ArrayList;
import java.util.List;


/**
 * This class gives information about the Tournament Model and methods to set and get its properties
 *
 */
public class TournamentModel {

	private int noOfGame;
	private int totalTurns;
	private List<World> worldList = new ArrayList<World>();
	private ArrayList<Player> playerList = new ArrayList<>();
	
	
	public TournamentModel(List<World> worldList, ArrayList<Player> playerList, int noOfGame,int totalTurns) {
		this.noOfGame = noOfGame;
		this.totalTurns =totalTurns;
		this.worldList = worldList;
		this.playerList = playerList;
	}
	
	public int getNoOfGame() {
		return noOfGame;
	}
	
	public int getTotalTurns() {
		return totalTurns;
	}
	public List<World> getWorldList() {
		return worldList;
	}
	
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
}
