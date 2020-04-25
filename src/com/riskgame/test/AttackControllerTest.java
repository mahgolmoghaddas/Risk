package com.riskgame.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.riskgame.controller.AttackController;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;

import junit.framework.Assert;

public class AttackControllerTest {
	AttackController attackController;
	World world;
	private String worldName;
	static MapReader map=new MapReader();
	@Before
	public void beforeCLass() {
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
		
	}
	
	@Test
	public void decideAttackWinnerTest() {
		attackController=new AttackController();
		String actual="";
		NavigableSet<Integer> attackerScoreSet = new TreeSet<>();
		HashSet<Territory> territories=new HashSet<Territory>();
		
		attackerScoreSet.add(2);
		attackerScoreSet.add(4);
		attackerScoreSet.add(6);
		NavigableSet<Integer> defenderScoreSet = new TreeSet<>();
		attackerScoreSet.add(1);
		attackerScoreSet.add(2);
		attackerScoreSet.add(5);
		territories=world.getTerritories();
		ArrayList<Territory> ter=new ArrayList<Territory>(territories);
		Territory attackerTerritory=ter.get(0);
		Player attackerPlayer=new Player(10, "Mahgol");
		attackerTerritory.setOwner(attackerPlayer);
		attackerPlayer=attackerTerritory.getOwner();
		
		
		Territory defenderTerritory=ter.get(10);
		Player defenderPlayer=new Player(11, "Farid");
		defenderTerritory.setOwner(defenderPlayer);
		defenderPlayer=defenderTerritory.getOwner();
		//defenderPlayer=defenderTerritory.getOwner();
		String attackerName=attackerPlayer.getPlayerName();	
		String defenderName=defenderPlayer.getPlayerName();
		String expected="Defender " + " won the battle. Attacker " + " lost 2 armies";
		try {
			 actual=attackController.decideAttackWinner(ter.get(0), attackerScoreSet, ter.get(1), defenderScoreSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);
	}
}
