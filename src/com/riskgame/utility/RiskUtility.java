package com.riskgame.utility;

/**
 * This class has the reusable methods to be used in the RISK Game
 * @author gauta
 *
 */
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

}
