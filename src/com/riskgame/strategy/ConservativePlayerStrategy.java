package com.riskgame.strategy;

import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Board;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.GameUtility;

public class ConservativePlayerStrategy extends PlayerStrategy {
	private static final long serialVersionUID = 6297428007418162704L;
	private transient GameUtility gameUtility = new GameUtility();
	Board board = Board.getInstance();
	transient GameLogs gameLogs = GameLogs.getInstance();

	@Override
	public void runReinforcePhase(Player activePlayer) {
		System.out.println("***[START] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("***[START] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
		try {
			gameUtility.calculateReinforcementForPlayers(activePlayer);
			// Sort the activePlayers territory
			HashSet<Territory> playersTerritorySet = activePlayer.getCountriesOwned();
			playersTerritorySet = gameUtility.sortTerritoryByArmiesASC(playersTerritorySet);

			if (playersTerritorySet != null && !playersTerritorySet.isEmpty()) {
				Iterator<Territory> playersTerritoryIterator = playersTerritorySet.iterator();
				while (playersTerritoryIterator.hasNext()) {
					if (activePlayer.getArmiesHeld() > 0) {
						Territory targetTerritory = playersTerritoryIterator.next();
						System.out.println("*****" + activePlayer.getName() + " places army in "
								+ targetTerritory.getCountryName() + "*****");
						gameLogs.log("*****" + activePlayer.getName() + " places army in "
								+ targetTerritory.getCountryName() + "*****");
						int oldTerritoryArmyCount = targetTerritory.getArmyCount();
						targetTerritory.setArmyCount(oldTerritoryArmyCount + 1);
						int oldArmiesCount = activePlayer.getArmiesHeld();
						activePlayer.setArmiesHeld(oldArmiesCount - 1);
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("***[END] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("***[END] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
	}

	@Override
	public void runAttackPhase(Player activePlayer) {
		System.out.println("***[Start] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		System.out.println("Player " + activePlayer.getPlayerName() + " CHOOSE NOT TO ATTACK***");
		System.out.println("***[END] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("***[Start] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("Player " + activePlayer.getPlayerName() + " CHOOSE NOT TO ATTACK***");
		gameLogs.log("***[END] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");

	}

}
