package com.riskgame.strategy;

import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.GameUtility;

public class ConservativePlayerStrategy implements PlayerStrategy{
	private int tempReinforcementCount = 0;
	private GameUtility gameUtility = new GameUtility();
	Board board = Board.getInstance();

	@Override
	public void runSetupPhase(Player activePlayer) {

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
	}

	@Override
	public void runReinforcePhase(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runAttackPhase(Player attacker) {
		System.out.println("Player "+attacker.getPlayerName()+" chooses not to attack..");
	}

	@Override
	public void runFortifyPhase(Player player) {
		// TODO Auto-generated method stub
		
	}

}
