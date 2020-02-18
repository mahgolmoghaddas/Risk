package com.riskgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.riskgame.utility.PlayerDiceNumberComparator;
import com.riskgame.utility.TurnPhase;

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
	private TurnPhase currentPhase;
	public TurnPhase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(TurnPhase currentPhase) {
		this.currentPhase = currentPhase;
	}

	private List<Player> playerList;
	private static List<Player> designedTurnList;
	private List<Player> tempturnList;

	public TurnManager(List<Player> playerList) {
		this.currentPhase = TurnPhase.SETUP;
		this.playerList = playerList;
	}

	public TurnPhase GetNextPhase() {
		switch (this.currentPhase) {
		case START:
			return TurnPhase.REINFORCE;
		case REINFORCE:
			return TurnPhase.ATTACK;
		case ATTACK:
			return TurnPhase.FORTIFY;
		case FORTIFY:
			return TurnPhase.PICKCARD;
		default:
			return TurnPhase.START;
		}
	}

	public void initTurnList() {
		tempturnList = new ArrayList<Player>(this.designedTurnList);
	}

	public Player getNextPlayer() throws Exception {
		if (TurnPhase.SETUP.equals(this.currentPhase)) {
			designedTurnList = new ArrayList<Player>();

			int indexOfFirstPlayer = playerList.indexOf(getPlayerWithMaxDiceNumber());

			for (int i = indexOfFirstPlayer; i < playerList.size(); i++) {
				designedTurnList.add(playerList.get(i));
			}
			for (int i = 0; i < indexOfFirstPlayer ; i++) {
				designedTurnList.add(playerList.get(i));
			}
			return pickNextPlayer();
		}

		return pickNextPlayer();
	}

	public Player pickNextPlayer() {
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

	public Player getPlayerWithMaxDiceNumber() throws Exception {
		List<Player> sortedPlayerList = new ArrayList<>(playerList);

		Collections.sort(sortedPlayerList, new PlayerDiceNumberComparator());
		Collections.reverse(sortedPlayerList);
		return sortedPlayerList.get(0);
	}

}
