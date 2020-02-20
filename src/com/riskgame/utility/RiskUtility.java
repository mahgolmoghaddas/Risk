package com.riskgame.utility;

public class RiskUtility {

	/**
	 * This method generates the random Card type.
	 * @return card Type i.e. INFANTRY OR CAVALRY OR ARTILLERY
	 */
	public CardType generateRandomCardType() {

		int min = 1;
		int max = 3;
		int range = max - min + 1;
		int randomNumber = (int) (Math.random() * range) + min;
		if (randomNumber == 1) {
			return CardType.INFANTRY;
		} else if (randomNumber == 2) {
			return CardType.CAVALRY;
		} else {
			return CardType.ARTILLERY;
		}
	}

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
