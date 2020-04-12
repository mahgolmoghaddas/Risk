package com.riskgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Observer;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

import com.riskgame.controller.AttackController;
import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.DiceType;
import com.riskgame.utility.ViewUtility;

/**
 * This class shows the view for attacking. It has drop downs to choose attacker and defender territory.
 * It also has the dice panel to role attacker and defender dice.
 * @author gautam
 *
 */
public class AttackPanelView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private Board board;

	JComboBox<String> defenderComboList = new JComboBox<String>();
	public static DicePanel attackDicePanel;
	public static DicePanel defendDicePanel;
	public static Territory attackerTerritory;
	public static Territory defenderTerritory;

	public AttackPanelView(Board board) {
		this.board = board;
		board.addObserver(this);
		createAttackPanel();
		revalidate();
	}

	@Override
	public void update(java.util.Observable o, Object arg) {
		revalidate();
	}

	public void createAttackPanel() {
		try {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
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
				if (territory != null && territory.getArmyCount() > 1) {
					attackerComboList.addItem(territory.getCountryName());
				}
			}
		}

		attackerComboList.addItemListener(itemListener -> {
			String attacker = (String) attackerComboList.getSelectedItem();
			System.out.println("ATTACKER*****" + attacker);
			World world = board.getWorld();
			if (world != null) {
				attackerTerritory = world.getTerritoryByName(attacker);
				if (attackerTerritory != null) {
					if (attackerTerritory.getArmyCount() >= 2) {
						defenderComboList = createDefenderComboBox(attackerTerritory.getNeighborsTerritory());
						defenderComboList.revalidate();
					} else {
						JOptionPane.showMessageDialog(BoardView.mainBoardFrame.getContentPane(),
								"You have less than 2 armies in this territory. Please select another", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});

		return attackerComboList;

	}

	public JComboBox<String> createDefenderComboBox(HashSet<String> neighboursTerritory) {
		if (defenderComboList != null && defenderComboList.getItemCount() > 0) {
			defenderComboList.removeAllItems();
		}
		World world = board.getWorld();
		defenderComboList.addItem("SELECT");
		if (neighboursTerritory != null && !neighboursTerritory.isEmpty()) {
			Iterator<String> neighboursIterator = neighboursTerritory.iterator();
			while (neighboursIterator.hasNext()) {
				String neighbour = neighboursIterator.next();
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
			defenderTerritory = world.getTerritoryByName(defender);
			System.out.println("Defender*****" + defender);
		});

		return defenderComboList;
	}

	public JPanel createPanelForRollingDice() {
		JPanel rollingDicePanel = new JPanel();
		rollingDicePanel.setLayout(new FlowLayout());

		attackDicePanel = new DicePanel(DiceType.Attack, board, false);
		defendDicePanel = new DicePanel(DiceType.Defend, board, false);

		JButton attackResultBtn = attackResultButton("See Result");

		rollingDicePanel.add(attackDicePanel);
		rollingDicePanel.add(defendDicePanel);
		rollingDicePanel.add(attackResultBtn);
		Dimension dimension = new Dimension();
		dimension.setSize(BoardView.mainBoardFrame.getWidth() - 350, 55);
		rollingDicePanel.setPreferredSize(dimension);

		return rollingDicePanel;
	}

	public JButton attackResultButton(String name) {
		JButton finishMove = new JButton(name);
		finishMove.addActionListener(new AttackController());
		finishMove.setBackground(Color.decode("#f5b942"));
		finishMove.setUI(new BasicButtonUI());
		finishMove.setVisible(true);
		return finishMove;
	}

}
