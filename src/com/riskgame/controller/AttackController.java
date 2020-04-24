package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.GamePhase;
import com.riskgame.view.AttackPanelView;

/**
 * This class handles the action to decide who wons the battle that is Attacker
 * or Defender. Then it calls BoardView to display the reattack board.
 * 
 * @author gautam
 *
 */
public class AttackController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			String attackDiceScore = "";
			String defenderDiceScore = "";
			Territory attackerTerritory = AttackPanelView.attackerTerritory;
			Territory defenderTerritory = AttackPanelView.defenderTerritory;

			if (AttackPanelView.attackDicePanel != null) {
				attackDiceScore = AttackPanelView.attackDicePanel.getDiceLabelText();
			}

			if (AttackPanelView.attackDicePanel != null) {
				defenderDiceScore = AttackPanelView.defendDicePanel.getDiceLabelText();
			}

			System.out.println("Attacker " + attackerTerritory + " Defender " + defenderTerritory + " AScore "
					+ attackDiceScore + " DScore " + defenderDiceScore);

			NavigableSet<Integer> attackerScoreSet = getAttackerDiceScore(attackDiceScore);
			NavigableSet<Integer> defenderScoreSet = getDefenderDiceScore(defenderDiceScore);

			String attackResult = decideAttackWinner(attackerTerritory, attackerScoreSet, defenderTerritory,
					defenderScoreSet);
			GameController.gamePhase = GamePhase.REINFORCE;
			GameController.getInstance().getBoardView().showReAttackBoard(attackResult);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String decideAttackWinner(Territory attackerTerritory, NavigableSet<Integer> attackerScoreSet,
			Territory defenderTerritory, NavigableSet<Integer> defenderScoreSet) throws Exception {
		String result = "";
		int attackerWinCnt = 0;
		String attackerName = "";
		String defenderName = "";
		if (attackerScoreSet != null && !attackerScoreSet.isEmpty() && defenderScoreSet != null
				&& !defenderScoreSet.isEmpty() && attackerTerritory != null && defenderTerritory != null) {
			Iterator<Integer> attackerScoreIterator = attackerScoreSet.iterator();
			Iterator<Integer> defenderScoreIterator = defenderScoreSet.iterator();
			attackerName = attackerTerritory.getOwner().getName();
			defenderName = defenderTerritory.getOwner().getName();
			while (attackerScoreIterator.hasNext()) {
				int attackerScore = attackerScoreIterator.next();
				while (defenderScoreIterator.hasNext()) {
					int defenderScore = defenderScoreIterator.next();
					if (attackerScore > defenderScore) {
						// Attacker Win
						++attackerWinCnt;
						attackerWin(attackerTerritory, defenderTerritory);
					} else {
						// Attacker LOSE
						defenderWin(attackerTerritory);
					}
				}
			}
		} else {
			result = "Mandatory data is missing or null";
		}
		if (attackerWinCnt == 2) {
			result = "Attacker " + attackerName + " won the battle. Defender " + defenderName + " lost 2 armies";
		} else if (attackerWinCnt == 1) {
			result = "Attacker " + attackerName + " lost 1 army. Defender " + defenderName + " lost 1 army";
		} else {
			result = "Defender " + defenderName + " won the battle. Attacker " + attackerName + " lost 2 armies";
		}
		System.out.println("ATTACK RESULT::: " + result);
		return result;
	}

	private void defenderWin(Territory attackerTerritory) {

		int armyCnt = attackerTerritory.getArmyCount();
		attackerTerritory.setArmyCount(armyCnt - 1);
		System.out.println(attackerTerritory.getOwner().getName() + " lost the 1 army");
	}

	private void attackerWin(Territory attackerTerritory, Territory defenderTerritory) {
		if (defenderTerritory.getArmyCount() == 1) {
			Player attacker = attackerTerritory.getOwner();
			Player defender = defenderTerritory.getOwner();

			attacker.getCountriesOwned().add(defenderTerritory);
			defender.getCountriesOwned().remove(defenderTerritory);

			defenderTerritory.setOwner(attacker);

			int armyCnt = attackerTerritory.getArmyCount();
			attackerTerritory.setArmyCount(armyCnt - 1);

			System.out.println(defender.getName() + " lost the territory " + defenderTerritory.getCountryName());
			System.out.println(
					"Attacker " + attacker.getName() + " owned new territory " + defenderTerritory.getCountryName());

		} else if (defenderTerritory.getArmyCount() > 1) {
			// Decrease the army in territory
			int armyCnt = defenderTerritory.getArmyCount();
			defenderTerritory.setArmyCount(armyCnt - 1);

			System.out.println(defenderTerritory.getOwner().getName() + " lost the 1 army");
		}
	}

	public NavigableSet<Integer> getAttackerDiceScore(String attackDiceScore) throws Exception {
		NavigableSet<Integer> attackerScoreSet = new TreeSet<>();

		if (attackDiceScore != null && attackDiceScore.contains(" ")) {
			String[] attackDiceArr = attackDiceScore.split(" ");
			for (int i = 0; i < attackDiceArr.length; i++) {
				attackerScoreSet.add(Integer.valueOf(attackDiceArr[i]));
			}
		}
		return attackerScoreSet.descendingSet();
	}

	public NavigableSet<Integer> getDefenderDiceScore(String defenderDiceScore) throws Exception {
		NavigableSet<Integer> defenderScoreSet = new TreeSet<>();
		if (defenderDiceScore != null && defenderDiceScore.contains(" ")) {
			String[] defendDiceArr = defenderDiceScore.split(" ");
			for (int i = 0; i < defendDiceArr.length; i++) {
				defenderScoreSet.add(Integer.valueOf(defendDiceArr[i]));
			}
		}
		return defenderScoreSet.descendingSet();
	}

}
