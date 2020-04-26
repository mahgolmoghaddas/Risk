package com.riskgame.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.strategy.AggressivePlayerStrategy;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.PlayerType;

public class AggressiveStrategyTest {
	
	String worldName;
	World world;
	MapReader map=new MapReader();
	Player player;
	Board board;
	AggressivePlayerStrategy aggressive;
	HashSet<Territory>  countriesOwned;
	ArrayList<Territory> countries=new ArrayList<Territory>();
	@BeforeClass
	public void beforeClass() {
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
	}
	
	@Before
	public void init() {
	board=new Board();
	player=new Player(98, "Mahgol", PlayerType.AGGRESIVE);

	player.setCountriesOwned(countriesOwned);
	countries.add(world.getTerritoryByName("India"));
	countries.add(world.getTerritoryByName("Alberta"));
	countries.add(world.getTerritoryByName("Ontario"));
	countries.add(world.getTerritoryByName("Quebec"));
	countriesOwned=new HashSet<Territory> (countries);
	
}
	
	
	@Test
	public void aggressiveStrategy() {
		aggressive.runAttackPhase(player, board);
		String resultOfAttack=aggressive.attackResult;
		assertNotNull(resultOfAttack);
	}
}
