package com.riskgame.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.model.Player;
import com.riskgame.utility.GameUtility;
import com.riskgame.utility.PlayerType;

public class PlayerSelectionView extends JPanel {

	private GameUtility gameUtility = new GameUtility();
	private static final long serialVersionUID = 1L;
	private int playersCount = 0;
	private HashMap<Integer, String> playerNameMap = new HashMap<>();
	private HashMap<Integer, PlayerType> playerTypeMap = new HashMap<>();
	private ArrayList<Player> playerList = new ArrayList<>();

	public PlayerSelectionView(int playersCount,boolean isTournament) {
		this.playersCount = playersCount;
		if (playersCount == 0) {
			playersCount = 2;
		}
		setLayout(new FlowLayout());
		createPlayerNamePanel(isTournament);
		Dimension dim = new Dimension(300, 150);
		this.setPreferredSize(dim);
		revalidate();
	}

	public PlayerSelectionView() {

	}

	private void createPlayerNamePanel(boolean isTournament) {
		for (int i = 1; i <= playersCount; i++) {
			add(createPlayerNameLabel(i));
			add(createPlayerNameField(i));
			add(createPlayerTypeCombo(i,isTournament));
		}
	}

	private JTextField createPlayerNameField(int playerId) {
		JTextField playerTextField = new JTextField();
		playerTextField.setName("" + playerId);
		Dimension prefSize = new Dimension(80, 20);
		playerTextField.setPreferredSize(prefSize);
		playerTextField.addActionListener(e -> {
			String playerName = playerTextField.getText();
			if (playerName == null || playerName.isEmpty()) {
				playerName = "Player" + playerId;
			}
			playerNameMap.put(playerId, playerName);
		});

		playerTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				String playerName = textField.getText();
				if (playerName == null || playerName.isEmpty()) {
					playerName = "Player" + playerId;
				}
				playerNameMap.put(playerId, playerName);
			}
		});

		return playerTextField;
	}

	private JLabel createPlayerNameLabel(int count) {
		JLabel playerNameLabel = new JLabel("Player" + count);
		return playerNameLabel;
	}

	public JComboBox<String> createPlayerTypeCombo(int playerId, boolean isTournament) {
		JComboBox<String> playerTypeComboList = new JComboBox<>();
		playerTypeComboList.setName("" + playerId);
		playerTypeComboList.addItem("SELECT");
		if (!isTournament) {
			playerTypeComboList.addItem(PlayerType.HUMAN.toString());
		}
		playerTypeComboList.addItem(PlayerType.AGGRESIVE.toString());
		playerTypeComboList.addItem(PlayerType.CONSERVATIVE.toString());
		playerTypeComboList.addItem(PlayerType.CHEATER.toString());
		playerTypeComboList.addItem(PlayerType.RANDOM.toString());
		playerTypeComboList.addItemListener(itemListener -> {
			String type = playerTypeComboList.getSelectedItem().toString();
			if (!"SELECT".equalsIgnoreCase(type)) {
				PlayerType playerType = PlayerType.valueOf(type);
				playerTypeMap.put(playerId, playerType);
			}
		});
		return playerTypeComboList;
	}

	public ArrayList<Player> getPlayerList() {
		if (!this.playerNameMap.isEmpty() && !playerTypeMap.isEmpty()) {
			for (int i = 1; i <= playersCount; i++) {
				String playerName = playerNameMap.get(i);
				PlayerType playerType = playerTypeMap.get(i);
				if (playerName == null || playerName.isEmpty()) {
					playerName = "Player" + i;
				}
				Player player = new Player(i, playerName,playerType);
				player.setColor(gameUtility.getPlayerColorById(i));
				this.playerList.add(player);
			}
			System.out.println("****Created Player List for the game ********");
		} else {
			System.out.println("Something is null");
		}
		return this.playerList;
	}
}
