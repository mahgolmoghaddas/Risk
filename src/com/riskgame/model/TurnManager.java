package com.riskgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.riskgame.utility.PlayerDiceNumberComparator;
import com.riskgame.utility.GamePhase;

/**
 * This class manages the turn among the players.When the manager is
 * initialized, it expects a list of player with the dice number they get at the
 * SETUP phase. From those list of players, it schedules the turn in round robin
 * fashion, first being the player with maximum dice number at SETUP phase.
 * 
 * @author pushpa
 *
 */
public class TurnManager {

	private Player currentPlayer;
	private GamePhase currentPhase;

	public GamePhase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(GamePhase currentPhase) {
		this.currentPhase = currentPhase;
	}

	private List<Player> playerList;
	private static List<Player> designedTurnList;
	private List<Player> tempturnList;

	public TurnManager(List<Player> playerList) {
		this.currentPhase = GamePhase.SETUP;
		this.playerList = playerList;
	}

	/**
	 * This method returns the next phase of the game based on the current phase of the game
	 * @return GamePhase
	 */
	public GamePhase getNextPhase() {
		switch (this.currentPhase) {
		case START:
			return GamePhase.SETUP;
		case SETUP:
			return GamePhase.REINFORCE;
		case REINFORCE:
			return GamePhase.ATTACK;
		case ATTACK:
			return GamePhase.FORTIFY;
		case FORTIFY:
			return GamePhase.PICKCARD;
		default:
			return GamePhase.START;
		}
	}

	public void initTurnList() {
		tempturnList = new ArrayList<Player>(this.designedTurnList);
	}

	/**
	 * Returns the nextplayer using round-robin algorithm
	 * @return Player
	 * @throws Exception
	 */
	public Player getNextPlayer() throws Exception {
		if (GamePhase.SETUP.equals(this.currentPhase)) {
			designedTurnList = new ArrayList<Player>();

			int indexOfFirstPlayer = playerList.indexOf(getPlayerWithMaxDiceNumber());

			for (int i = indexOfFirstPlayer; i < playerList.size(); i++) {
				designedTurnList.add(playerList.get(i));
			}
			for (int i = 0; i < indexOfFirstPlayer; i++) {
				designedTurnList.add(playerList.get(i));
			}
			return pickNextPlayer();
		}

		return pickNextPlayer();
	}

	private Player pickNextPlayer() {
		Player nextPlayer;
		if (tempturnList == null || tempturnList.size() < 1) {
			initTurnList();
		}
		nextPlayer = tempturnList.get(0);
		tempturnList.remove(0);
		return nextPlayer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	private Player getPlayerWithMaxDiceNumber() throws Exception {
		List<Player> sortedPlayerList = new ArrayList<>(playerList);

		Collections.sort(sortedPlayerList, new PlayerDiceNumberComparator());
		Collections.reverse(sortedPlayerList);
		return sortedPlayerList.get(0);
	}

}
