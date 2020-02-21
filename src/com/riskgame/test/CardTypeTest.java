package com.riskgame.test;

import static org.hamcrest.CoreMatchers.hasItems;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import com.riskgame.model.Player;


import org.junit.Test;

import com.riskgame.utility.CardType;
import com.riskgame.utility.RiskUtility;

public class CardTypeTest {

	@Test
	public void generateRandomCardTypeTest() {

		List<String> cardTypeList = Arrays.asList("Infantry","Cavalry","Artillery");

		RiskUtility utility = new RiskUtility();

		CardType cardType = utility.generateRandomCardType();

		org.hamcrest.MatcherAssert.assertThat(cardTypeList,hasItems(cardType.getCardTypeValue()));

	}
	//The rolldice method generates a random dice number, this test case verifies if the random number generated for 
	//the dice is less than 6. 
	@Test
	public void rollDiceTest () throws Exception
	{
		RiskUtility util= new RiskUtility();
		int dice= util.rollDice();
		boolean condition= false;
		
		if(dice<=6) {condition=true;}
		assertTrue("Truth", condition);
		
	}
	

	
}
