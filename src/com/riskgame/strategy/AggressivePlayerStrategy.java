package com.riskgame.strategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;

import com.riskgame.controller.AttackController;
import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.GameUtility;


/**
 * This class gives concrete implementation of Aggressive Player's Strategy
 *
 * @author Himani
 * 
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

	private static final long serialVersionUID = 3200131977682089249L;
	private transient GameUtility gameUtility = new GameUtility();
	private transient DiceUtility diceUtility = new DiceUtility();
	transient GameLogs gameLogs = GameLogs.getInstance();

		
	@Override
	public void runReinforcePhase(Player activePlayer,Board board) {
		System.out.println("***[START] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
		System.out.println("*** RUNNING REINFORCE IN AGGRESIVE MODE*****");
		
		if(GameController.getInstance().isSavedGame()) {
			initializeTransientVariable();
		}
		
		gameLogs.log("***[START] Auto Reinforcement phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("*** RUNNING REINFORCE IN AGGRESIVE MODE*****");
		try {
			gameUtility.calculateReinforcementForPlayers(activePlayer,board);
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
	public void runAttackPhase(Player activePlayer,Board board) {
		System.out.println("***[START] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		System.out.println("*** RUNNING REINFORCE IN AGGRESIVE MODE*****");
		if(GameController.getInstance().isSavedGame()) {
			initializeTransientVariable();
		}
		gameLogs.log("***[START] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
		gameLogs.log("*** RUNNING REINFORCE IN AGGRESIVE MODE*****");
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
						HashSet<Territory> defenderTerritories = gameUtility.getDefenderTerritories(attacker,board);
						defenderTerritories = gameUtility.sortTerritoryByArmiesASC(defenderTerritories);

						Iterator<Territory> defenderTerrIter = defenderTerritories.iterator();

						while (defenderTerrIter.hasNext()) {
							Territory defender = defenderTerrIter.next();

							if (attacker.getArmyCount() > 1) {
								// START BATTLE BY ROLLING DICE
								NavigableSet<Integer> attackerDiceRoll = diceUtility.autoRollDice(3);
								NavigableSet<Integer> defenderDiceRoll = diceUtility.autoRollDice(2);

								String attackResult = attackController.decideAttackWinner(attacker, attackerDiceRoll,
										defender, defenderDiceRoll);
								gameLogs.log("Attack Result::" + attackResult);
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
		gameLogs.log("***[END] Auto Attack phase for Player " + activePlayer.getPlayerName() + " *****");
	}

	private void initializeTransientVariable() {
		gameUtility = new GameUtility();
		diceUtility = new DiceUtility();
		gameLogs = GameLogs.getInstance();

	}
}
