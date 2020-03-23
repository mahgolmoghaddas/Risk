package com.riskgame.utility;

/**
 * This class provides the reusable method for dice used in the Risk Game
 * @author pushpa
 *
 */
public class DiceUtility {
	/**
	 * This method randomly generates a dice number from 1 to 6
	 * @return dice number.
	 * @throws Exception
	 */
	public int rollDice() throws Exception {
		int diceNumber = 0, max = 6, min = 1;

		int range = max - min + 1;
		diceNumber = (int) (Math.random() * range) + min;

		return diceNumber;
	}
}
