package com.riskgame.utility;

/**
 * This enum specifies the types of the card used in the Risk Game
 * @author pushpa
 *
 */
public enum CardType {
	INFANTRY("Infantry"), CAVALRY("Cavalry"), ARTILLERY("Artillery");
	
	private String cardType;
	
	CardType(String cardType) {
		this.cardType =cardType;
	}
	
	public String getCardTypeValue() {
		return this.cardType;
	}
}
