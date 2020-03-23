package com.riskgame.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.utility.ViewUtility;

public class PlayerPanelView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private Board board;

	private Map<Integer, JTextField> armiesField = new HashMap<>();
	private Map<Integer, JLabel> diceMap = new HashMap<>();

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
	 * This method creates the player panel as per the user selected number of
	 * players
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

					armiesField.put(playerList.get(i).getId(), textField);
					diceMap.put(playerList.get(i).getId(), playerDice);
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
			for (int i = 0; i < board.getPlayerList().size(); i++) {
				Player player = board.getPlayerList().get(i);
				armiesField.get(player.getId()).setText(player.getArmiesHeld() + "");
				if (player.getStartDiceNo() != null) {
					JLabel diceLabel =diceMap.get(player.getId());
					diceLabel.setText("Dice value:" + player.getStartDiceNo());
					diceLabel.setForeground(player.getColor());
				}
			}

			this.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
