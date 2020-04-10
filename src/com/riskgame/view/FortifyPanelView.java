package com.riskgame.view;

import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;

public class FortifyPanelView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Board board;
	JComboBox<Integer> armyCountCombo = new JComboBox<Integer>();
	JComboBox<String> destComboList = new JComboBox<String>();
	public static Territory sourceTerritory;
	public static Territory destinationTerritory;
	Integer selectedArmyCount = 0;

	public FortifyPanelView(Board board) {
		this.board = board;
		board.addObserver(this);
		createFortifyPanel();
		revalidate();
	}

	private void createFortifyPanel() {
		JLabel sourceLabel = new JLabel("Source:");
		JComboBox<String> sourceComboList = createSourceComboBox();
		JLabel destinationLabel = new JLabel("Destination:");
		JLabel armyCntLabel = new JLabel("Select army count:");
		JButton moveButton = createMoveButton();

		setLayout(new FlowLayout());
		add(sourceLabel);
		add(sourceComboList);
		add(destinationLabel);
		add(destComboList);
		add(armyCntLabel);
		add(armyCountCombo);
		add(moveButton);
	}

	public JComboBox<String> createSourceComboBox() {
		JComboBox<String> sourceComboList = new JComboBox<String>();
		Player activePlayer = board.getActivePlayer();
		HashSet<Territory> countriesOwned = activePlayer.getCountriesOwned();

		sourceComboList.addItem("SELECT");
		if (countriesOwned != null && countriesOwned.size() > 0) {
			Iterator<Territory> attackerTerritoryIterator = countriesOwned.iterator();
			while (attackerTerritoryIterator.hasNext()) {
				Territory territory = attackerTerritoryIterator.next();
				if (territory.getArmyCount() > 1) {
					sourceComboList.addItem(territory.getCountryName());
				}
			}
		}

		sourceComboList.addItemListener(itemListener -> {
			String source = (String) sourceComboList.getSelectedItem();
			World world = board.getWorld();
			if (world != null) {
				sourceTerritory = world.getTerritoryByName(source);
				System.out.println("SOURCE*****" + sourceTerritory.getCountryName());
				if (sourceTerritory != null) {
					createArmyCountCombo(sourceTerritory.getArmyCount());
					destComboList =createDestinationComboBox(sourceTerritory.getNeighborsTerritory());
					destComboList.revalidate();
					armyCountCombo.revalidate();
				}
			}
		});

		return sourceComboList;

	}

	private void createArmyCountCombo(int armyCount) {
		if (armyCountCombo != null && armyCountCombo.getItemCount() > 0) {
			armyCountCombo.removeAllItems();
		}
		for (int i = 1; i < armyCount; i++) {
			armyCountCombo.addItem(i);
		}

		armyCountCombo.addItemListener(e -> {
			selectedArmyCount = (Integer) armyCountCombo.getSelectedItem();
		});

	}

	public JComboBox<String> createDestinationComboBox(HashSet<String> neighboursTerritory) {

		if (destComboList != null && destComboList.getItemCount() > 0) {
			destComboList.removeAllItems();
		}

		World world = board.getWorld();
		if (neighboursTerritory != null && !neighboursTerritory.isEmpty()) {
			Iterator<String> neighboursIterator = neighboursTerritory.iterator();
			while (neighboursIterator.hasNext()) {
				String neighbour = neighboursIterator.next();
				if (world != null) {
					Territory territory = world.getTerritoryByName(neighbour);
					if (territory != null && territory.getOwner().getId() == board.getActivePlayer().getId()) {
						destComboList.addItem(territory.getCountryName());
					}
				}
			}
		}

		destComboList.addItemListener(itemListener -> {
			String destination = (String) destComboList.getSelectedItem();
			destinationTerritory = world.getTerritoryByName(destination);
			System.out.println("Destination*****" + destinationTerritory.getCountryName());
		});

		return destComboList;
	}

	private JButton createMoveButton() {
		JButton moveButton = new JButton("Move");

		moveButton.addActionListener(e -> {

			if (sourceTerritory != null && destinationTerritory != null && selectedArmyCount > 0) {
				int sourceArmyCnt = sourceTerritory.getArmyCount();
				int destArmyCnt = destinationTerritory.getArmyCount();
				sourceTerritory.setArmyCount(sourceArmyCnt - selectedArmyCount);
				destinationTerritory.setArmyCount(destArmyCnt + selectedArmyCount);
				board.getNextPlayer();
			}

		});

		return moveButton;
	}

	@Override
	public void update(Observable o, Object arg) {
//		revalidate();
	}
}
