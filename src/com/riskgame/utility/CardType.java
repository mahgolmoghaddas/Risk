package com.riskgame.utility;

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
