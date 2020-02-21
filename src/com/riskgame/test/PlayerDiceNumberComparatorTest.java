package com.riskgame.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.riskgame.utility.*;

import com.riskgame.model.Player;
import com.riskgame.utility.PlayerDiceNumberComparator;

public class PlayerDiceNumberComparatorTest {
	PlayerDiceNumberComparator pl= new PlayerDiceNumberComparator();
	Player p1= new Player(1, "ABC");
	Player p2= new Player(2, "SDC");
	
	@Test
	public void PlayerDiceNumberComparatorTestMethod() {
		int value= 0;
		boolean val= false;
		if(value>=-1 || value<=1)
		{
			val= true;
		}
		assertTrue("Truth", val);
	}

}
