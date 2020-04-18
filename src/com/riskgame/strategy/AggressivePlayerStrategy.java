package com.riskgame.strategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;

import com.riskgame.controller.AttackController;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.GameUtility;

public class AggressivePlayerStrategy implements PlayerStrategy {

	private GameUtility gameUtility = new GameUtility();
	private DiceUtility diceUtility = new DiceUtility();
	Board board = Board.getInstance();

	@Override
	public void runSetupPhase(Player activePlayer) {
		int tempReinforcementCount = 0;
		try {
			// Sort the activePlayers territory
			HashSet<Territory> playersTerritorySet = activePlayer.getCountriesOwned();
			playersTerritorySet = gameUtility.sortTerritoryByArmiesASC(playersTerritorySet);

			if (playersTerritorySet != null && !playersTerritorySet.isEmpty()) {
				Iterator<Territory> playersTerritoryIterator = playersTerritorySet.iterator();
				while (playersTerritoryIterator.hasNext()) {
					if (tempReinforcementCount < 3 && activePlayer.getArmiesHeld() > 0) {
						Territory targetTerritory = playersTerritoryIterator.next();
						System.out.println("*****" + activePlayer.getName() + " places army in "
								+ targetTerritory.getCountryName() + "*****");
						int oldTerritoryArmyCount = targetTerritory.getArmyCount();
						targetTerritory.setArmyCount(oldTerritoryArmyCount + 1);
						int oldArmiesCount = activePlayer.getArmiesHeld();
						activePlayer.setArmiesHeld(oldArmiesCount - 1);
						tempReinforcementCount = tempReinforcementCount + 1;

						if ((activePlayer.getArmiesHeld() <= 0 || tempReinforcementCount >= 3)
								&& gameUtility.playersHaveArmies()) {
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

	@Override
	public void runReinforcePhase(Player activePlayer) {
		System.out.println("***[START] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
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
	}

	@Override
	public void runAttackPhase(Player activePlayer) {
		System.out.println("***[START] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		AttackController attackController = new AttackController();
		try {
			// Get attacker territory with highest armies
			HashSet<Territory> attackerTerritories = activePlayer.getCountriesOwned();
			attackerTerritories = gameUtility.sortTerritoryByArmiesDESC(attackerTerritories);

			if (attackerTerritories != null && !attackerTerritories.isEmpty()) {

				Iterator<Territory> attackerTerrIterator = attackerTerritories.iterator();
				while (attackerTerrIterator.hasNext()) {
					Territory attacker = attackerTerrIterator.next();
					if (attacker.getArmyCount() > 1) {
						// Get Defender territory with least armies
						HashSet<Territory> defenderTerritories = gameUtility.getDefenderTerritories(attacker);
						defenderTerritories = gameUtility.sortTerritoryByArmiesASC(defenderTerritories);

						Iterator<Territory> defenderTerrIter = defenderTerritories.iterator();

						while (defenderTerrIter.hasNext()) {
							Territory defender = defenderTerrIter.next();

							if (attacker.getArmyCount() > 1) {
								// START BATTLE BY ROLLING DICE
								NavigableSet<Integer> attackerDiceRoll = diceUtility.autoRollDice(3);
								NavigableSet<Integer> defenderDiceRoll = diceUtility.autoRollDice(2);

								attackController.decideAttackWinner(attacker, attackerDiceRoll, defender,
										defenderDiceRoll);
							} else {
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("***[END] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");

	}

	@Override
	public void runFortifyPhase(Player activePlayer) {
		System.out.println("***[START] Auto Fortify phase for Player " + activePlayer.getPlayerName() + " *****");
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
								.getDestinationTerritories(sourceTerritory);
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

	}

}
