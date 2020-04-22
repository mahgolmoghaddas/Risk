package com.riskgame.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.strategy.PlayerStrategy;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.GameUtility;
import com.riskgame.utility.PlayerType;

/**
 * This class creates a WorldMap Panel and draws map with the provided World
 * object. It also allows option to place armies in the territories
 * 
 * @author gauta
 *
 */
public class WorldMapPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Board board;
	private BufferedImage mapImage;
	Dimension size = new Dimension();
	int tempReinforcementCount = 0;

	HashMap<String, Territory> territoryMap = new HashMap<String, Territory>();
	private GameUtility gameUtility = new GameUtility();

	public WorldMapPanel(Board board, BufferedImage mapImage) {
		this.board = board;
		this.mapImage = mapImage;
		setComponentSize();
		board.addObserver(this);
	}

	@Override
	public void paint(Graphics graphics) {
		try {
			Graphics2D g2D = (Graphics2D) graphics;
			g2D.drawImage(this.mapImage, 0, 0, this);
			setComponentSize();
			drawTerritoriesInMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawTerritoriesInMap() {

		if (this.board != null && this.board.getWorld() != null) {
			HashSet<Territory> territorySet = this.board.getWorld().getTerritories();
			if (territorySet != null && !territorySet.isEmpty()) {
				Iterator<Territory> territoryIterator = territorySet.iterator();
				while (territoryIterator.hasNext()) {
					Territory territory = territoryIterator.next();
					territoryMap.put(territory.getCountryName(), territory);
					JTextField text = new JTextField();
					int x = (int) territory.getTerritoryPosition().getX();
					int y = (int) territory.getTerritoryPosition().getY();
					text.setEditable(false);
					text.setCursor(new Cursor(Cursor.HAND_CURSOR));
					text.setVisible(true);
					text.setName(territory.getCountryName());
					text.setText(territory.getArmyCount() + "");
					text.setBackground(territory.getOwner().getColor());
					text.setBounds(x, y, 18, 15);
					text.addMouseListener(new MouseListener() {
						@Override
						public void mouseReleased(java.awt.event.MouseEvent e) {
							if (tempReinforcementCount == 2 || tempReinforcementCount == 0) {
								if (GameController.getInstance().getGamePhase().equals(GamePhase.SETUP)) {
									Player activePlayer = Board.getInstance().getActivePlayer();
									if (!PlayerType.HUMAN.equals(activePlayer.getPlayerType())) {
										System.out.println("********AUTO ARMIES PLACEMENT***********");
										PlayerStrategy strategy = activePlayer.getPlayerStrategy();
										strategy.runSetupPhase(activePlayer);
									}
								}
							}
						}

						@Override
						public void mousePressed(java.awt.event.MouseEvent e) {
						}

						@Override
						public void mouseExited(java.awt.event.MouseEvent e) {
						}

						@Override
						public void mouseEntered(java.awt.event.MouseEvent e) {
						}

						@Override
						public void mouseClicked(java.awt.event.MouseEvent e) {
							JTextField targetTerritoryField = (JTextField) e.getComponent();
							if (GameController.getInstance().getGamePhase().equals(GamePhase.SETUP)) {
								Player activePlayer = Board.getInstance().getActivePlayer();
								if (PlayerType.HUMAN.equals(activePlayer.getPlayerType())) {
									handleSetUpPhase(targetTerritoryField);
								}
							} else if (GameController.getInstance().getGamePhase().equals(GamePhase.REINFORCE)
									&& PlayerType.HUMAN.equals(board.getActivePlayer().getPlayerType())) {
								handleReinforcementPhase(targetTerritoryField);
							}
						}
					});
					this.setLayout(null);
					this.add(text);
				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return size;
	}

	private void setComponentSize() {
		if (mapImage != null) {
			size.width = mapImage.getWidth();
			size.height = mapImage.getHeight();
			this.setSize(size);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param targetTerritoryField
	 */
	private void handleReinforcementPhase(JTextField targetTerritoryField) {
		Player activePlayer = board.getActivePlayer();

		Territory targetTerritory = territoryMap.get(targetTerritoryField.getName());

		if (targetTerritory.getOwner().getId() == activePlayer.getId() && activePlayer.getArmiesHeld() > 0) {
			activePlayer.getCountriesOwned().add(targetTerritory);
			int oldTerritoryArmyCount = targetTerritory.getArmyCount();
			targetTerritory.setArmyCount(oldTerritoryArmyCount + 1);
			int oldArmiesCount = activePlayer.getArmiesHeld();
			activePlayer.setArmiesHeld(oldArmiesCount - 1);
			targetTerritoryField.setText(targetTerritory.getArmyCount() + "");
		}
	}

	private void handleSetUpPhase(JTextField targetTerritoryField) {
		Player activePlayer = board.getActivePlayer();

		Territory targetTerritory = territoryMap.get(targetTerritoryField.getName());

		if (targetTerritory.getOwner().getId() == activePlayer.getId() && activePlayer.getArmiesHeld() > 0) {

			activePlayer.getCountriesOwned().add(targetTerritory);
			int oldTerritoryArmyCount = targetTerritory.getArmyCount();
			targetTerritory.setArmyCount(oldTerritoryArmyCount + 1);
			int oldArmiesCount = activePlayer.getArmiesHeld();
			activePlayer.setArmiesHeld(oldArmiesCount - 1);

			targetTerritoryField.setText(targetTerritory.getArmyCount() + "");
			tempReinforcementCount = tempReinforcementCount + 1;

			// pick up next player
			if ((activePlayer.getArmiesHeld() <= 0 || tempReinforcementCount >= 3) && gameUtility.playersHaveArmies()) {
				tempReinforcementCount = 0;
				activePlayer = board.getNextPlayer();
			}

		}

		System.out.println(activePlayer.getName() + " places 1 army in " + targetTerritory.getCountryName());
	}
}
