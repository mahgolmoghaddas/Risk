package com.riskgame.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.riskgame.model.*;
import com.riskgame.utility.*;

public class ViewUtilityTest {
	ViewUtility vu = new ViewUtility();

	@Test
	public void nullWorldTest() throws Exception {
		World world = null;
		boolean val = false;
		if (world == null)
			val = true;
		assertTrue("world is null", val);
	}

	@Test
	public void nullContinentTest() throws Exception {
		boolean val = false;
		World world = new World();
		if (world.getContinents() != null)
			val = true;
		assertTrue("Continent is null", val);
	}

	@Test
	public void emptyContinentTest() throws Exception {
		boolean val = false;
		World world = new World();
		if (world.getContinents().isEmpty())
			val = true;
		assertTrue("Continent is empty", val);
	}
	
	@Test
	public void notNullStartDiceNo() throws Exception {
		List<Player> playerList = new ArrayList<>();
		Player p1 = new Player(1,"Jasmeet");
		p1.setStartDiceNo(1);
		playerList.add(p1);
		boolean val = false;
		if (p1.getStartDiceNo() != null) 
			val = true;
		assertTrue(val);
	}
	

	@Test
	public void nullStartDiceNo() throws Exception {
		List<Player> playerList = new ArrayList<>();
		Player p1 = new Player(1,"Jasmeet");
		p1.setStartDiceNo(null);
		playerList.add(p1);
		boolean val = false;
		if (p1.getStartDiceNo() == null) 
			val = true;
		assertTrue(val);
	}
}