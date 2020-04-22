package com.riskgame.model;

import java.io.Serializable;

import com.riskgame.utility.CardType;

/**
 * This class specifies the property a particular card possess i.e Territory
 * Name and Card Type
 * 
 * @author gautam
 *
 */
public class Card implements Serializable {
	
	private static final long serialVersionUID = 4673103732889053076L;
	private String territoryName;
	private CardType cardType;

	public Card(String territoryName, CardType cardType) {
		this.territoryName = territoryName;
		this.cardType = cardType;
	}

	public String getTerritoryName() {
		return territoryName;
	}

	public CardType getCardType() {
		return cardType;
	}
}
