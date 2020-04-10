package com.riskgame.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ScoreConfiguration {

	private static HashMap<String, Integer> territoryReinforcementMap;
	
	private static HashMap<Integer,Integer> winnigCriteria;

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

}
