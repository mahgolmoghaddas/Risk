package com.riskgame.model;

import java.util.LinkedList;
import java.util.List;

import com.riskgame.utility.CardType;
import com.riskgame.utility.RiskUtility;

public class Game {
	
	private List<Card> cardDeck ;

	RiskUtility utility = new RiskUtility();

	public Game() {
		
		this.cardDeck = new LinkedList<Card>();
	}
	
	/**
	 * This method creates a new card and add it to the card Deck.
	 * @param territoryName
	 * @throws Exception
	 */
	public void addNewCardToDeck(String territoryName) throws Exception{
		CardType cardType = utility.generateRandomCardType();
		Card card = new Card(territoryName, cardType);
		cardDeck.add(card);
	}
	
}