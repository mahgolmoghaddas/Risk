package com.riskgame.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.GameUtility;
/**
 * This class provides test cases for Game Utility class for number of armies for each player and
 * check the next phase of the game.
 * 
 * @author Himani
 *
 */
public class GameUtilityTest {

	GameUtility gameUtility = new GameUtility();
	World world;
	List<String> cardTypeList = Arrays.asList("Infantry", "Cavalry", "Artillery");
	List<Card> deck;

	@Before
	public void setUp() {

		Territory territory = new Territory();
		territory.setCountryName("Atlanta");
		Territory territory1 = new Territory();
		territory1.setCountryName("Egypt");
		Territory territory2 = new Territory();
		territory2.setCountryName("Canda");
		
		HashSet<Territory> territorySet = new HashSet<>();
		territorySet.add(territory);
		territorySet.add(territory1);
		territorySet.add(territory2);
		
		HashSet<Continent> continentSet = new HashSet<>();
		continentSet.add(new Continent("Asia",territorySet,2));
		world = new World(continentSet);
		
		deck = gameUtility.buildCardDeck(world);

	}

	@Test
	public void getNumberOfArmiesForEachPlayerTest() {

		int noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(2);
		assertEquals(noOfArmies, 40);

		noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(3);
		assertEquals(noOfArmies, 35);

		noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(4);
		assertEquals(noOfArmies, 30);

		noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(5);
		assertEquals(noOfArmies, 25);

		noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(6);
		assertEquals(noOfArmies, 20);

		noOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(7);
		assertEquals(noOfArmies, 0);

	}

	@Test
	public void getNextPhaseTest() {

		GamePhase gamePhase = gameUtility.getNextPhase(GamePhase.ATTACK);

		assertEquals(gamePhase, GamePhase.FORTIFY);

		gamePhase = gameUtility.getNextPhase(GamePhase.START);

		assertEquals(gamePhase, GamePhase.SETUP);

		gamePhase = gameUtility.getNextPhase(GamePhase.PICKCARD);

		assertEquals(gamePhase, GamePhase.REINFORCE);

	}
	
	@Test
	public void testNumberOfCardsCreated() {
		try {
			assertThat(deck.size(), is(3));
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
