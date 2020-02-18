package com.riskgame.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.riskgame.model.Player;
import com.riskgame.model.TurnManager;

public class TurnOrganizerTest {

	@Test
	public void getNextPlayerTest() {

		try {
			List<Player> playerList = new ArrayList<>();
			Player p1 = new Player();
			p1.setStartDiceNo(1);
			p1.setName("Evelyn");
			Player p2 = new Player();
			p2.setStartDiceNo(2);
			p2.setName("Fred");
			Player p3 = new Player();
			p3.setStartDiceNo(3);
			p3.setName("Anna");
			Player p4 = new Player();
			p4.setStartDiceNo(4);
			p4.setName("Bikka");
			Player p5 = new Player();
			p5.setStartDiceNo(5);
			p5.setName("Chris");
			Player p6 = new Player();
			p6.setStartDiceNo(6);
			p6.setName("Dilip");

			playerList.add(p1);
			playerList.add(p2);
			playerList.add(p3);
			playerList.add(p4);
			playerList.add(p5);
			playerList.add(p6);
			TurnManager manager = new TurnManager(playerList);
			assertEquals(manager.getNextPlayer().getName(), "Dilip");
			assertEquals(manager.getNextPlayer().getName(), "Evelyn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
