package com.riskgame.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.riskgame.model.Player;
import com.riskgame.model.TurnManager;

public class TurnManagerTest {

	@Test
	public void getNextPlayerTest() {

		try {
			List<Player> playerList = new ArrayList<>();
			Player p1 = new Player(1,"Evelyn");
			p1.setStartDiceNo(1);
			Player p2 = new Player(2,"Fred");
			p2.setStartDiceNo(2);
			Player p3 = new Player(3,"Anna");
			p3.setStartDiceNo(3);
			Player p4 =new Player(4,"Bikka");
			p4.setStartDiceNo(4);
			Player p5 = new Player(5,"Chris");
			p5.setStartDiceNo(5);
			Player p6 = new Player(6,"Dilip");
			p6.setStartDiceNo(6);

			playerList.add(p1);
			playerList.add(p2);
			playerList.add(p3);
			playerList.add(p4);
			playerList.add(p5);
			playerList.add(p6);
			TurnManager manager = new TurnManager(playerList);
			assertEquals(manager.getNextPlayer().getPlayerName(), "Dilip");
			assertEquals(manager.getNextPlayer().getPlayerName(), "Evelyn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
