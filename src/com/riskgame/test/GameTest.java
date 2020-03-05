package com.riskgame.test;

import com.riskgame.model.Game;
import com.riskgame.model.Player;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Test;

public class GameTest {
	@Test
	public void addPlayerTest() {
		ArrayList<Player> playerList = new ArrayList<>();
		Player p1 = new Player(1, "Evelyn");
		p1.setStartDiceNo(1);
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
//		g.setPlayerList(playerList);
//		assertFalse(g.addPlayer("mahgol"));
	}

	@Test
	public void duplicateNameTest() {
		ArrayList<Player> playerList = new ArrayList<>();
		Player p1 = new Player(1, "Evelyn");
		p1.setStartDiceNo(1);
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
//		g.setPlayerList(playerList);
//		assertFalse(g.duplicatePlayer("Dilip"));
	}

}
