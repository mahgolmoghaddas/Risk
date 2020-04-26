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

import junit.framework.Assert;

public class PlayerTest {
	static String worldName;
	static World world;
	static MapReader map=new MapReader();
	Player player;
	AggressivePlayerStrategy aggressive;
	HashSet<Territory>  countriesOwned;
	ArrayList<Territory> temp=new ArrayList<Territory>();
	
	@BeforeClass
	public static void beforeClass() {
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
	}
	
	@Before
	public void init() {
		Territory[] countries= {world.getTerritoryByName("India"),world.getTerritoryByName("Alberta"), world.getTerritoryByName("Ontario"),world.getTerritoryByName("Quebec")};
		for(Territory c:countries) {
			temp.add(c);
		}
		countriesOwned=new HashSet<Territory>(temp);
	
}
	
	@Test
	public void PlayerTestMethod() {
		player=new Player(98, "Mahgol", PlayerType.AGGRESIVE);

		player.setCountriesOwned(countriesOwned);
		
		HashSet<Territory> attackerTerritories = player.getCountriesOwned();
		assertNotNull(attackerTerritories);
	}


}
