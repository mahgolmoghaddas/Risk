package com.riskgame.utility;

import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * This class provides the reusable method for dice used in the Risk Game
 * 
 * @author pushpa
 *
 */
public class DiceUtility {
	/**
	 * This method randomly generates a dice number from 1 to 6
	 * 
	 * @return dice number.
	 * @throws Exception @param
	 */
	public int rollDice() throws Exception {
		int diceNumber = 0, max = 6, min = 1;

		int range = max - min + 1;
		diceNumber = (int) (Math.random() * range) + min;

		return diceNumber;
	}

	public NavigableSet<Integer> autoRollDice(int rollCount) {
		NavigableSet<Integer> diceRollSet = new TreeSet<>();
		try {
			for (int i = 1; i <= rollCount; i++) {
				diceRollSet.add(rollDice());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diceRollSet;
	}
}
