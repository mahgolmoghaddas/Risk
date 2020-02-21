package com.riskgame.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.model.Card;
import com.riskgame.model.Game;
import com.riskgame.utility.CardType;
import com.riskgame.utility.RiskUtility;

public class LoadGameTest {
	Game game;
	List<String> cardTypeList = Arrays.asList("Infantry", "Cavalry", "Artillery");
	List<Card> deck;

	@Before
	public void setup() {
		try {
			game = new Game();
			game.addNewCardToDeck("Altanta");
			game.addNewCardToDeck("Nepal");
			game.addNewCardToDeck("Iran");
			game.addNewCardToDeck("India");
			deck = game.getCardDeck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNumberOfCardsCreated() {
		try {
			assertThat(deck.size(), is(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCardTypesForGeneratedCard() {

		try {
			for (int i = 0; i < deck.size(); i++) {
				Card card = deck.get(i);
				org.hamcrest.MatcherAssert.assertThat(cardTypeList, hasItems(card.getCardType().getCardTypeValue()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
