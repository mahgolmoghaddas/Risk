package com.riskgame.test;

import static org.hamcrest.CoreMatchers.hasItems;

import java.util.Arrays;
import java.util.List;

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
}
