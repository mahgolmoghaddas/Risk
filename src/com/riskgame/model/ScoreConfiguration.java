package com.riskgame.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class provides the data for getting bonus score as per the number of
 * territories occupied by the player. It also provides the winningCriteria for
 * the game
 * 
 * @author gauta
 *
 */
public class ScoreConfiguration implements Serializable {

	private static final long serialVersionUID = 4655217509828827732L;

	private static HashMap<String, Integer> territoryReinforcementMap;

	private static HashMap<Integer, Integer> winnigCriteria;

	private static HashMap<String, Integer> cardReinforcementBonusMap;

	static {
		territoryReinforcementMap = new HashMap<>();
		territoryReinforcementMap.put("1-11", 3);
		territoryReinforcementMap.put("12-14", 4);
		territoryReinforcementMap.put("15-17", 5);
		territoryReinforcementMap.put("18-20", 6);
		territoryReinforcementMap.put("21-23", 7);
		territoryReinforcementMap.put("24-26", 8);
		territoryReinforcementMap.put("27-29", 9);
		territoryReinforcementMap.put("30-32", 10);
		territoryReinforcementMap.put("33-35", 11);
		territoryReinforcementMap.put("36-38", 12);
		territoryReinforcementMap.put("39-41", 13);

		cardReinforcementBonusMap = new HashMap<>();
		cardReinforcementBonusMap.put("Infantry", 4);
		cardReinforcementBonusMap.put("Cavalry", 6);
		cardReinforcementBonusMap.put("Artillery", 8);
		cardReinforcementBonusMap.put("All", 10);

	}

	public int getOccupiedTerritoryBonus(int occupiedTerritories) {
		int reinforcement = 0;
		try {
			if (occupiedTerritories > 0) {

				Iterator<Entry<String, Integer>> territoryMapIter = territoryReinforcementMap.entrySet().iterator();

				while (territoryMapIter.hasNext()) {

					Entry<String, Integer> entry = territoryMapIter.next();
					String key = entry.getKey();

					String[] keyRange = key.split("-");

					Integer startRange = Integer.valueOf(keyRange[0]);
					Integer endRange = Integer.valueOf(keyRange[1]);

					if (occupiedTerritories >= startRange && occupiedTerritories <= endRange) {
						reinforcement = entry.getValue();
						break;
					}

				}
			}

			System.out
					.println("Reinforcement for Occupied Territories " + occupiedTerritories + " is " + reinforcement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reinforcement;
	}

	public static int getCardBonus(String cardType) {
		return cardReinforcementBonusMap.get(cardType);
	}
}
