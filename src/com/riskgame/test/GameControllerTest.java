package com.riskgame.test;
import static org.hamcrest.CoreMatchers.hasItems;
import com.riskgame.controller.GameController;
import com.riskgame.model.*;
import com.riskgame.utility.GameUtility;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

//import org.junit.jupiter.api.Test;

public class GameControllerTest {

		
	@Test
	public void testAssignArmiesToPlayer() throws Exception {
		ArrayList<Player> playerList = new ArrayList<>();
		Player p1 = new Player(1, "Evelyn");
		Player p2 = new Player(2, "Fred");
		p2.setStartDiceNo(2);
		Player p3 = new Player(3, "Anna");
		p3.setStartDiceNo(3);
		Player p4 = new Player(4, "Bikka");
		p4.setStartDiceNo(4);
		Player p5 = new Player(5, "Chris");
		p5.setStartDiceNo(5);
		Player p6 = new Player(6, "Dilip");
		p6.setStartDiceNo(6);

		playerList.add(p1);
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);
		playerList.add(p5);
		playerList.add(p6);
		
		int noOfArmies;
		boolean condition = false;
		GameController gameController = new GameController(false);
		gameController.assignArmiesToPlayer(playerList);
		noOfArmies = p1.getArmiesHeld(); 
		if(noOfArmies>0) {
			condition = true;
			}
		assertTrue(condition);
		  
		 
	}

}
