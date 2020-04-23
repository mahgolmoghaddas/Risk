package com.riskgame.strategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.GameUtility;

/**
 * This class gives Player's Strategy details
 *
 * @author Jasmeet
 * 
 */
public abstract class PlayerStrategy implements Serializable {

	private static final long serialVersionUID = -7011407573519619426L;
	private transient GameUtility gameUtility = new GameUtility();
	transient GameLogs gameLogs = GameLogs.getInstance();

	public void runSetupPhase(Player activePlayer,Board board) {
		int tempReinforcementCount = 0;
		try {
			// Sort the activePlayers territory
			if (GameController.getInstance().isSavedGame()) {
				initializeTransientVariable();
			}
			HashSet<Territory> playersTerritorySet = activePlayer.getCountriesOwned();
			playersTerritorySet = gameUtility.sortTerritoryByArmiesASC(playersTerritorySet);

			if (playersTerritorySet != null && !playersTerritorySet.isEmpty()) {
				Iterator<Territory> playersTerritoryIterator = playersTerritorySet.iterator();
				while (playersTerritoryIterator.hasNext()) {
					if (tempReinforcementCount < 3 && activePlayer.getArmiesHeld() > 0) {
						Territory targetTerritory = playersTerritoryIterator.next();
						System.out.println("*****" + activePlayer.getName() + " places army in "
								+ targetTerritory.getCountryName() + "*****");
						gameLogs.log("*****" + activePlayer.getName() + " places army in "
								+ targetTerritory.getCountryName() + "*****");
						int oldTerritoryArmyCount = targetTerritory.getArmyCount();
						targetTerritory.setArmyCount(oldTerritoryArmyCount + 1);
						int oldArmiesCount = activePlayer.getArmiesHeld();
						activePlayer.setArmiesHeld(oldArmiesCount - 1);
						tempReinforcementCount = tempReinforcementCount + 1;

						if ((activePlayer.getArmiesHeld() <= 0 || tempReinforcementCount >= 3)
								&& gameUtility.playersHaveArmies(board)) {
							tempReinforcementCount = 0;
							activePlayer = board.getNextPlayer();
							break;
						}
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runFortifyPhase(Player activePlayer,Board board) {
		System.out.println("***[START] Auto Fortify phase for Player " + activePlayer.getPlayerName() + " *****");
		if (GameController.getInstance().isSavedGame()) {
			initializeTransientVariable();
		}
		gameLogs.log("***[START] Auto Fortify phase for Player " + activePlayer.getPlayerName() + " *****");
		boolean hasMoved = false;
		try {
			// Get source territory with highest armies
			HashSet<Territory> sourceTerritories = activePlayer.getCountriesOwned();
			sourceTerritories = gameUtility.sortTerritoryByArmiesDESC(sourceTerritories);

			if (sourceTerritories != null && !sourceTerritories.isEmpty()) {
				Iterator<Territory> sourceTerrIterator = sourceTerritories.iterator();
				while (sourceTerrIterator.hasNext() && !hasMoved) {
					Territory sourceTerritory = sourceTerrIterator.next();
					if (sourceTerritory.getArmyCount() > 1) {
						// Get destination territory with least armies
						HashSet<Territory> destinationTerritories = gameUtility
								.getDestinationTerritories(sourceTerritory, board);
						destinationTerritories = gameUtility.sortTerritoryByArmiesASC(destinationTerritories);

						Iterator<Territory> destinatinTerrIter = destinationTerritories.iterator();

						while (destinatinTerrIter.hasNext()) {
							Territory destinationTerritory = destinatinTerrIter.next();

							if (sourceTerritory.getArmyCount() > destinationTerritory.getArmyCount()) {
								int sourceArmyCnt = sourceTerritory.getArmyCount();
								int destArmyCnt = destinationTerritory.getArmyCount();
								sourceTerritory.setArmyCount(sourceArmyCnt - 1);
								destinationTerritory.setArmyCount(destArmyCnt + 1);
								System.out.println("****Auto Moved 1 army from " + sourceTerritory.getCountryName()
										+ " to " + destinationTerritory.getCountryName() + "***");
								gameLogs.log("****Auto Moved 1 army from " + sourceTerritory.getCountryName() + " to "
										+ destinationTerritory.getCountryName() + "***");
								hasMoved = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("***[END] Auto Fortify phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("***[END] Auto Fortify phase for Player " + activePlayer.getPlayerName() + " *****");

	}

	public abstract void runReinforcePhase(Player player,Board board);

	public abstract void runAttackPhase(Player attacker,Board board);

	private void initializeTransientVariable() {
		gameUtility = new GameUtility();
		gameLogs = GameLogs.getInstance();
	}

}
