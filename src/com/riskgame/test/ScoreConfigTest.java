package com.riskgame.test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.riskgame.model.ScoreConfiguration;

public class ScoreConfigTest {

	ScoreConfiguration scoreConfig = new ScoreConfiguration();

	@Test
	public void getOccupiedTerritoryBonus_Test() {
		
		int bonus = scoreConfig.getOccupiedTerritoryBonus(10);
		assertEquals(bonus, 3);
		
		bonus =scoreConfig.getOccupiedTerritoryBonus(25);
		assertEquals(bonus, 8);
		
		bonus =scoreConfig.getOccupiedTerritoryBonus(0);
		assertEquals(bonus, 0);
	}
}
