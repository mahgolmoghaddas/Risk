package com.riskgame.utility;

import java.util.ArrayList;
import java.util.List;

import com.riskgame.model.Player;
import com.riskgame.model.TurnManager;

public class Test {

	public static void main(String[] args) {

		try {
			List<Player> list = new ArrayList<>();
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

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
			TurnManager manger =new TurnManager(list);
			System.out.println(manger.getNextPlayer().getName());
			System.out.println(manger.getNextPlayer().getName());
			System.out.println(manger.getNextPlayer().getName());
			System.out.println(manger.getNextPlayer().getName());
			System.out.println(manger.getNextPlayer().getName());
			manger.setCurrentPhase(TurnPhase.START);
			System.out.println(manger.getNextPlayer().getName());
			System.out.println(manger.getNextPlayer().getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
