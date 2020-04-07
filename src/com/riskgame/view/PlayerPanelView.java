package com.riskgame.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.ViewUtility;

/**
 * This is the panel which displays the player details, the armies each player
 * holds and number of start dice number the player gets.
 * 
 * @author pushpa
 *
 */
public class PlayerPanelView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private Board board;

	private Map<Integer, JTextField> armiesFieldMap = new HashMap<>();
	private Map<Integer, JLabel> diceLabelMap = new HashMap<>();

	private ViewUtility viewUtility = new ViewUtility();

	public PlayerPanelView(Board board) {
		this.board = board;
		board.addObserver(this);
		createPlayerPanel(board);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Board) {
			updatePlayerPanel(board);
		}
	}

	/**
	 * This method creates the player panel dynamically as per the user selected
	 * number of players
	 * 
	 * @param board
	 * @return
	 */
	public void createPlayerPanel(Board board) {

		try {
			List<Player> playerList = board.getPlayerList();
			if (playerList != null && !playerList.isEmpty()) {
				for (int i = 0; i < playerList.size(); i++) {
					JLabel playerLabel = viewUtility.createPlayerLabel(playerList.get(i));
					JTextField textField = viewUtility.createPlayerArmiesTextField(playerList.get(i));
					JLabel playerDice = viewUtility.createPlayerDice(playerList.get(i));

					armiesFieldMap.put(playerList.get(i).getId(), textField);
					diceLabelMap.put(playerList.get(i).getId(), playerDice);
					add(playerLabel);
					add(textField);
					add(playerDice);
				}
			}
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatePlayerPanel(Board board) {

		try {
			GamePhase gamePhase = GameController.getInstance().getGamePhase();

			if (board != null && board.getPlayerList() != null && !board.getPlayerList().isEmpty()) {

				if (GamePhase.SETUP.equals(gamePhase)) {
					for (int i = 0; i < board.getPlayerList().size(); i++) {
						Player player = board.getPlayerList().get(i);
						updateSetUpPhase(player);
					}
				} else if (GamePhase.REINFORCE.equals(gamePhase)) {
					updateReinforcementPhase(board.getActivePlayer());
					revalidate();
				}
				updateArmiesHeld(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSetUpPhase(Player player) {
		armiesFieldMap.get(player.getId()).setText(player.getArmiesHeld() + "");
		if (player.getStartDiceNo() != null) {
			JLabel diceLabel = diceLabelMap.get(player.getId());
			diceLabel.setText("Dice value:" + player.getStartDiceNo());
			diceLabel.setForeground(player.getColor());
		}
		this.repaint();
	}

	private void updateReinforcementPhase(Player player) {
		if (diceLabelMap != null && !diceLabelMap.isEmpty()) {
			for (Entry<Integer, JLabel> entry : diceLabelMap.entrySet()) {
				JLabel label = entry.getValue();
				this.remove(label);
			}
		}
	}

	private void updateArmiesHeld(Board board) {

		if (board != null && board.getPlayerList() != null) {
			for (int i = 0; i < board.getPlayerList().size(); i++) {
				Player player = board.getPlayerList().get(i);
				JTextField armyHeldField = armiesFieldMap.get(player.getId());
				armyHeldField.setText(player.getArmiesHeld() + "");
			}
		}
	}
}
