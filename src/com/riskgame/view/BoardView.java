package com.riskgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import com.riskgame.controller.GameController;
import com.riskgame.controller.SaveGameController;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceType;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.ViewUtility;

/**
 * This is the board view for the risk game which displays the world map in the
 * gui with the selected number of players.
 * 
 * @author pushpa
 *
 */
public class BoardView implements Observer {

	private Board board;
	private ViewUtility viewUtility = new ViewUtility();
	public static JFrame mainBoardFrame;
	DicePanel diceRollPanel;
	JPanel finishSetupPanel;
	JButton reinforceButton;
	JPanel messagePanel;
	int currentPlayerId = 0;
	JPanel worldMapPanel;
	JButton attackButton;
	AttackPanelView attackPanel;
	FortifyPanelView fortifyPanel;
	JButton endAttackButton;
	JButton saveGameButton;
	JButton pickUpCardButton;

	Map<Integer, JTextField> armiesField = new HashMap<>();
	Map<Integer, JLabel> diceList = new HashMap<>();

	public BoardView(Board board) {
		this.board = board;
		board.addObserver(this);
		if (GameController.getInstance().isSavedGame()) {
			System.out.println("RUNNIG SAVED GAME");
			initializeBoardViewForSavedGame(board);
			messagePanel = createMessagePanel(updateActivePlayerLabel(board.getActivePlayer()));
			mainBoardFrame.add(messagePanel);
			executeByPhase(board);
		} else {
			System.out.println("RUNNING NEW GAME");
			showGameBoard(board);
		}
	}

	public void initializeBoardViewForSavedGame(Board board) {
		try {
			mainBoardFrame = viewUtility.createMainFrame("Play Game", false);
			mainBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			worldMapPanel = drawMap(board);
			PlayerPanelView playerPanel = new PlayerPanelView(board);
			playerPanel.setPreferredSize(getPreferredSizeForBoardPanel());
			mainBoardFrame.getContentPane().add(worldMapPanel, "Center");
			mainBoardFrame.getContentPane().add(playerPanel);
			mainBoardFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeByPhase(Board board) {
		System.out.println("GAME PHASE " + board.getGamePhase());
		if (GamePhase.SETUP.equals(GameController.getInstance().getGamePhase())) {
			showSetupBoard(board);
		} else if (GamePhase.REINFORCE.equals(GameController.getInstance().getGamePhase())) {
			showReinforceBoard(board);
		} else if (GamePhase.ATTACK.equals(GameController.getInstance().getGamePhase())) {
			showAttackBoard(board);
		} else if (GamePhase.FORTIFY.equals(GameController.getInstance().getGamePhase())) {
			showFortifyBoard(board);
		} else if (GamePhase.PICKCARD.equals(GameController.getInstance().getGamePhase())) {
			showCardPickup(board);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Board) {
			board = (Board) o;
			if (GamePhase.SETUP.equals(GameController.getInstance().getGamePhase())) {
				if (saveGameButton != null) {
					saveGameButton.setVisible(false);
				}
			} else {

				if (saveGameButton != null) {
					saveGameButton.setVisible(true);
				}
			}
			mainBoardFrame.revalidate();
			executeByPhase(board);

		}
	}

	/**
	 * This method displays the board view as per the Board data which includes
	 * player,no of territories acquired by the player and number of armies hold by
	 * player
	 */
	public void showGameBoard(Board board) {
		try {
			mainBoardFrame = viewUtility.createMainFrame("Play Game", false);
			mainBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			worldMapPanel = drawMap(board);
			PlayerPanelView playerPanel = new PlayerPanelView(board);
			playerPanel.setPreferredSize(getPreferredSizeForBoardPanel());
			JPanel turnDicePanel = createDiceRollPanel(board);
			turnDicePanel.setPreferredSize(getPreferredSizeForBoardPanel());
			mainBoardFrame.getContentPane().add(worldMapPanel, "Center");
			mainBoardFrame.getContentPane().add(turnDicePanel, "Center");
			mainBoardFrame.getContentPane().add(playerPanel);
			saveGameButton = createSaveGameButton();
			saveGameButton.setVisible(false);
			mainBoardFrame.add(saveGameButton, BorderLayout.WEST);
			mainBoardFrame.setVisible(true);
			JOptionPane.showMessageDialog(mainBoardFrame, "Roll dice to decide the player turn");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method displays the initial setup Board view as per the Board data which
	 * includes player,no of territories acquired by the player and number of armies
	 * hold by player
	 */
	public void showSetupBoard(Board board) {
		try {
			if (isStartDiceAllocatedToAll(board)) {
				Player currentPlayer = board.getActivePlayer();
				if (diceRollPanel != null) {
					mainBoardFrame.remove(diceRollPanel);
				}
				if (messagePanel != null) {
					mainBoardFrame.remove(messagePanel);
				}
				messagePanel = createMessagePanel(updateActivePlayerLabel(currentPlayer));
				mainBoardFrame.add(messagePanel);
			}

			if (!hideNextPhaseButton(board)) {
				System.out.println("Created Reinforce Button");
				reinforceButton = viewUtility.createGamePhaseButton("Reinforce");
				messagePanel.add(reinforceButton);
			}

			mainBoardFrame.revalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks whether start dice has been allocated all the players in
	 * the game
	 * 
	 * @param board
	 * @return true/false
	 */
	public boolean isStartDiceAllocatedToAll(Board board) {

		boolean isStartDiceAllocated = true;
		if (board != null && board.getPlayerList() != null && !board.getPlayerList().isEmpty()) {

			for (int i = 0; i < board.getPlayerList().size(); i++) {
				Player player = board.getPlayerList().get(i);
				if (player.getStartDiceNo() <= 0) {
					isStartDiceAllocated = false;
					break;
				}
			}
		}
		return isStartDiceAllocated;
	}

	/**
	 * This method shows the BoardView for the Reinforcement Phase
	 * 
	 * @param board
	 */
	public void showReinforceBoard(Board board) {
		try {
			if (reinforceButton != null) {
				messagePanel.remove(reinforceButton);
			}
//			if (fortifyPanel != null) {
//				mainBoardFrame.remove(fortifyPanel);
//			}
			if (pickUpCardButton != null) {
				mainBoardFrame.remove(pickUpCardButton);
			}
			if (messagePanel != null && messagePanel.getComponents().length > 0) {
				messagePanel.removeAll();
				mainBoardFrame.remove(messagePanel);
			}
			messagePanel = createMessagePanel(updateActivePlayerLabel(board.getActivePlayer()));
			if (!hideAttackButton(board.getActivePlayer())) {
				attackButton = viewUtility.createGamePhaseButton("Attack");
				messagePanel.add(attackButton);
			}
			mainBoardFrame.add(messagePanel);
			mainBoardFrame.repaint();
			mainBoardFrame.revalidate();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method shows the BoardView for the Attack Phase
	 * 
	 * @param board
	 */
	public void showAttackBoard(Board board) {

		try {

			if (attackButton != null) {
				mainBoardFrame.remove(attackButton);
			}
			if (endAttackButton != null) {
				mainBoardFrame.remove(endAttackButton);
			}
			if (messagePanel != null) {
				mainBoardFrame.remove(messagePanel);
			}

			attackPanel = new AttackPanelView(board);
			Dimension dim = new Dimension();
			dim.setSize(mainBoardFrame.getWidth() - 350, 90);
			attackPanel.setPreferredSize(dim);

			mainBoardFrame.add(attackPanel);
			mainBoardFrame.repaint();
			mainBoardFrame.revalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method shows the BoardView for the Re-Attack Phase
	 * 
	 * @param message
	 */
	public void showReAttackBoard(String message) {
		if (attackPanel != null) {
			mainBoardFrame.remove(attackPanel);
		}
		if (messagePanel != null) {
			mainBoardFrame.remove(messagePanel);
		}
		messagePanel = createMessagePanel(updateMessage(message));

		if (attackButton != null) {
			mainBoardFrame.remove(attackButton);
		}
		attackButton = viewUtility.createGamePhaseButton("Attack");
		endAttackButton = viewUtility.createEndAttackButton("End Attack");

		mainBoardFrame.add(messagePanel);
		mainBoardFrame.add(attackButton);
		mainBoardFrame.add(endAttackButton);
		mainBoardFrame.repaint();
		mainBoardFrame.revalidate();
	}

	/**
	 * This method shows the BoardView for the Fortify Phase
	 * 
	 * @param board
	 */
	public void showFortifyBoard(Board board) {
		if (attackPanel != null) {
			mainBoardFrame.remove(attackPanel);
		}
		if (messagePanel != null) {
			mainBoardFrame.remove(messagePanel);
		}
		if (attackButton != null) {
			mainBoardFrame.remove(attackButton);
		}
		if (endAttackButton != null) {
			mainBoardFrame.remove(endAttackButton);
		}
		if (fortifyPanel != null) {
			mainBoardFrame.remove(fortifyPanel);
		}

		fortifyPanel = new FortifyPanelView(board);

		mainBoardFrame.add(fortifyPanel);
		mainBoardFrame.repaint();
		mainBoardFrame.revalidate();
	}

	/**
	 * This method shows the BoardView for the Fortify Phase
	 * 
	 * @param board
	 */
	public void showCardPickup(Board board) {
		System.out.println("DISPLAYING CARD PICKUP");
		if (fortifyPanel != null) {
			mainBoardFrame.remove(fortifyPanel);
		}
		if (pickUpCardButton != null) {
			mainBoardFrame.remove(pickUpCardButton);
		}
		if (messagePanel != null && messagePanel.getComponents().length > 0) {
			messagePanel.removeAll();
			mainBoardFrame.remove(messagePanel);
		}
		pickUpCardButton = new JButton("Pick up card");
		pickUpCardButton.addActionListener(actionListener -> {
			int occupiedTerritoryAfterAttack = board.getActivePlayer().getCountriesOwned().size();
			System.out.println("Pre attack occupiedTerritories " + board.getActivePlayer().getOccupiedTerritories()
					+ " post attack OccupiedTerritories::" + occupiedTerritoryAfterAttack);
			if (board.getActivePlayer().getOccupiedTerritories() < occupiedTerritoryAfterAttack) {
				board.getActivePlayer().setOccupiedTerritories(occupiedTerritoryAfterAttack);
				GameController.getInstance().handleCardPickUpCase(board);
			} else {
				messagePanel = createMessagePanel(updateMessage("No new territories occupied.You cannot draw card"));
				mainBoardFrame.add(messagePanel);
				mainBoardFrame.repaint();
				mainBoardFrame.revalidate();
				board.getNextPlayer();
				GameController.getInstance().actionPerformed(null);
			}
		});
		mainBoardFrame.add(pickUpCardButton);
		mainBoardFrame.revalidate();
		mainBoardFrame.repaint();
	}

	/**
	 * This method decides whether to hide the next phase button or not
	 * 
	 * @param board
	 * @return
	 */
	public boolean hideNextPhaseButton(Board board) {
		boolean hideAttackButton = false;
		if (board != null && board.getPlayerList() != null) {
			for (Player player : board.getPlayerList()) {
				if (player.getArmiesHeld() > 0) {
					hideAttackButton = true;
					break;
				}
			}
		}
		return hideAttackButton;
	}

	public boolean hideAttackButton(Player player) {
		boolean hideAttackButton = false;
		if (player.getArmiesHeld() > 0) {
			hideAttackButton = true;
		}
		return hideAttackButton;
	}

	/**
	 * This method draws map with the provided World data with the respective
	 * continents and the countries details
	 * 
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public WorldMapPanel drawMap(Board board) throws Exception {
		String mapName = board.getWorld().getImage();
		String fileName = "resources/images/" + mapName;
		File file = new File(fileName);
		BufferedImage image = ImageIO.read(file);
		WorldMapPanel worldMapPanel = new WorldMapPanel(board, image);
		worldMapPanel.setLayout(new FlowLayout());
		worldMapPanel.setVisible(true);
		return worldMapPanel;
	}

	/**
	 * This method creates the panel to roll the dice
	 * 
	 * @return
	 */
	public JPanel createDiceRollPanel(Board board) {
		diceRollPanel = new DicePanel(DiceType.Defend, board, true);
		diceRollPanel.setPreferredSize(getPreferredSizeForBoardPanel());
		diceRollPanel.setBackground(Color.decode("#03c2fc"));
		return diceRollPanel;
	}

	/**
	 * This method creates a finish button to specify that a current user is done
	 * with a particular move.
	 * 
	 * @return
	 */
	public JPanel createFinishPhasePanel(String name) {
		finishSetupPanel = new JPanel();
		Dimension dim = getPreferredSizeForBoardPanel();
		dim.height = 30;
		finishSetupPanel.setPreferredSize(dim);
		JButton finishMove = viewUtility.createGamePhaseButton(name);
		finishSetupPanel.add(finishMove, "Center");
		finishSetupPanel.setVisible(true);
		return finishSetupPanel;
	}

	public void printPlayerDetails(Board board) {
		System.out.println("playerlist size:::" + board.getPlayerList().size());
		for (Player player : board.getPlayerList()) {
			System.out.println(player.getName() + " ::" + player.getArmiesHeld());
			System.out.println(player.getCountriesOwned().size());
			for (Territory territory : player.getCountriesOwned()) {
				System.out.println(territory.getArmyCount());
				System.out.println(territory.getCountryName());
				System.err.println(territory.getOwner().getName());
			}
		}
	}

	public Dimension getPreferredSizeForBoardPanel() {
		Dimension dimension = new Dimension();
		dimension.setSize(mainBoardFrame.getWidth(), 55);
		return dimension;
	}

	private JLabel updateActivePlayerLabel(Player player) {
		JLabel activePlayerLabel = new JLabel();
		if (GameController.gamePhase.equals(GamePhase.SETUP)) {
			activePlayerLabel.setText("Place 3 armies at your turn. Current Player is " + player.getName());
		} else {
			activePlayerLabel.setText("Current Player is " + player.getName());
		}

		activePlayerLabel.setForeground(Color.decode("#525b5c"));
		activePlayerLabel.setVisible(true);
		return activePlayerLabel;
	}

	private JLabel updateMessage(String message) {
		JLabel activePlayerLabel = new JLabel();
		activePlayerLabel.setText(message);
		activePlayerLabel.setForeground(Color.decode("#525b5c"));
		activePlayerLabel.setVisible(true);
		return activePlayerLabel;
	}

	private JPanel createMessagePanel(JLabel jlabel) {
		messagePanel = new JPanel();
		messagePanel.add(jlabel);
		Dimension dimension = new Dimension();
		dimension.setSize(mainBoardFrame.getWidth() - 350, 40);
		messagePanel.setPreferredSize(dimension);
		return messagePanel;
	}

	private JButton createSaveGameButton() {
		JButton saveButton = new JButton("Save Game");
		try {
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			saveButton.addActionListener(new SaveGameController());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveButton;
	}
}
