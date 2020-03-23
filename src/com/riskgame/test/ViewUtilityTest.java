package com.riskgame.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.riskgame.model.World;
import com.riskgame.utility.ViewUtility;

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
}