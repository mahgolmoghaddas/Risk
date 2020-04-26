package com.riskgame.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
	
	Board board=new Board();
	static String worldName;
	static World world;
	static MapReader map=new MapReader();
	Player player;
	AggressivePlayerStrategy aggressive=new AggressivePlayerStrategy();
	HashSet<Territory>  countriesOwnedAttacker;
	HashSet<Territory>  countriesOwnedDefender;
	ArrayList<Territory> temp=new ArrayList<Territory>();
	
	@BeforeClass
	public static void beforeClass() {
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
	}
	
	@Before
	public void init() {
		
		int i=0;
		Territory[] countriesAttacker= {world.getTerritoryByName("India"),world.getTerritoryByName("Alberta")};
		for(Territory c:countriesAttacker) {
			c.setArmyCount(i++);
			temp.add(c);
		}
		countriesOwnedAttacker=new HashSet<Territory>(temp);
		
		
		int j=3;
		Territory[] countriesDefender= { world.getTerritoryByName("Ontario"),world.getTerritoryByName("Quebec")};
		for(Territory c:countriesDefender) {
			c.setArmyCount(j++);
			temp.add(c);
		}
		countriesOwnedDefender=new HashSet<Territory>(temp);
}
	
	
	@Test
	public void aggressiveStrategy() {
		Player attacker=new Player(98, "Mahgol", PlayerType.AGGRESIVE);
		attacker.setCountriesOwned(countriesOwnedAttacker);
		Player defender=new Player(88, "Sara");
		defender.setCountriesOwned(countriesOwnedDefender);
		aggressive.runAttackPhase(attacker ,board);
		String resultOfAttack=aggressive.attackResult;
		assertNull(resultOfAttack);
		
	}
}
