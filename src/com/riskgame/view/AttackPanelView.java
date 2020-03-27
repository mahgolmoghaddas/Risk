package com.riskgame.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.DiceType;

public class AttackPanelView extends JPanel {

	private static final long serialVersionUID = 1L;

	private Board board;

	JComboBox<String> defenderComboList  = new JComboBox<String>();
	JPanel rollingDicePanel;

	public AttackPanelView(Board board) {
		this.board = board;
		createAttackPanel();
	}

	public void createAttackPanel() {
		JLabel attackerLabel = new JLabel("Select attacker:");
		JComboBox<String> attackerComboList = createAttackerComboBox();
		JLabel defenderLabel = new JLabel("Select defender:");
		JPanel rollingDicePanel = createPanelForRollingDice();

		setLayout(new FlowLayout());
		add(attackerLabel);
		add(attackerComboList);
		add(defenderLabel);
		add(defenderComboList);
		add(rollingDicePanel);
		setVisible(true);
	}

	public JComboBox<String> createAttackerComboBox() {
		JComboBox<String> attackerComboList = new JComboBox<String>();
		Player activePlayer = board.getActivePlayer();
		HashSet<Territory> countriesOwned = activePlayer.getCountriesOwned();

		attackerComboList.addItem("SELECT");
		if (countriesOwned != null && countriesOwned.size() > 0) {
			Iterator<Territory> attackerTerritoryIterator = countriesOwned.iterator();
			while (attackerTerritoryIterator.hasNext()) {
				Territory territory = attackerTerritoryIterator.next();
				attackerComboList.addItem(territory.getCountryName());
			}
		}

		attackerComboList.addItemListener(itemListener -> {
			String attacker = (String) attackerComboList.getSelectedItem();
			System.out.println("ATTACKER*****" + attacker);
			World world = board.getWorld();
			if (world != null) {
				Territory territory = world.getTerritoryByName(attacker);
				if (territory != null) {
					defenderComboList = createDefenderComboBox(territory.getNeighborsTerritory());
					defenderComboList.revalidate();
				}
			}
		});

		return attackerComboList;

	}

	public JComboBox<String> createDefenderComboBox(HashSet<String> neighboursTerritory) {
		if (defenderComboList != null && defenderComboList.getItemCount()>0) {
			defenderComboList.removeAllItems();
		}
		defenderComboList.addItem("SELECT");
		if (neighboursTerritory != null && !neighboursTerritory.isEmpty()) {
			Iterator<String> neighboursIterator = neighboursTerritory.iterator();
			while (neighboursIterator.hasNext()) {
				String neighbour = neighboursIterator.next();
				World world = board.getWorld();
				if (world != null) {
					Territory territory = world.getTerritoryByName(neighbour);
					if (territory != null && territory.getOwner().getId() != board.getActivePlayer().getId()) {
						defenderComboList.addItem(territory.getCountryName());
					}
				}
			}
		}

		defenderComboList.addItemListener(itemListener -> {
			String defender = (String) defenderComboList.getSelectedItem();
			System.out.println("Defender*****" + defender);
		});

		return defenderComboList;
	}

	public JPanel createPanelForRollingDice() {
		rollingDicePanel = new JPanel();
		rollingDicePanel.setLayout(new FlowLayout());
		JPanel attackDicePanel = new DicePanel(DiceType.Attack, board, false);
		JPanel defendDicePanel = new DicePanel(DiceType.Defend, board, false);
		rollingDicePanel.add(attackDicePanel);
		rollingDicePanel.add(defendDicePanel);
		Dimension dimension = new Dimension();
		dimension.setSize(BoardView.mainBoardFrame.getWidth() - 350, 40);

		rollingDicePanel.setPreferredSize(dimension);
		return rollingDicePanel;

	}

}
