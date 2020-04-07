package com.riskgame.view;

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
	static JFrame mainBoardFrame;
	DicePanel diceRollPanel;
	JPanel finishSetupPanel;
	JButton reinforceButton;
	JPanel messagePanel;
	int currentPlayerId = 0;
	JPanel worldMapPanel;
	JButton attackButton;

	Map<Integer, JTextField> armiesField = new HashMap<>();
	Map<Integer, JLabel> diceList = new HashMap<>();

	public BoardView(Board board) {
		this.board = board;
		board.addObserver(this);
		showGameBoard(board);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Updated..BOARD DATA## PHASE::" + GameController.getInstance().getGamePhase());
		if (o instanceof Board) {
			board = (Board) o;
			if (GamePhase.SETUP.equals(GameController.getInstance().getGamePhase())) {
				showSetupBoard(board);
			} else if (GamePhase.REINFORCE.equals(GameController.getInstance().getGamePhase())) {
				showReinforceBoard(board);
				System.out.println("REINFORCE ARMY");
			} else if (GamePhase.ATTACK.equals(GameController.getInstance().getGamePhase())) {
				showAttackBoard(board);
				System.out.println("ATTACK ARMY");
			}
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

			mainBoardFrame.setVisible(true);
			JOptionPane.showMessageDialog(mainBoardFrame, "Roll dice to decide the player turn");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showSetupBoard(Board board) {
		try {
			if (isStartDiceAllocatedToAll(board)) {
				Player currentPlayer = board.getActivePlayer();
				
				mainBoardFrame.remove(diceRollPanel);
				if (messagePanel != null) {
					System.out.println("REMOVED");
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
		System.out.println("isStartDiceAllocated " + isStartDiceAllocated);
		return isStartDiceAllocated;
	}

	public void showReinforceBoard(Board board) {
		try {
			if (reinforceButton != null) {
				messagePanel.remove(reinforceButton);
			}
			if (messagePanel != null && messagePanel.getComponents().length > 0) {
				messagePanel.removeAll();
				mainBoardFrame.remove(messagePanel);
			}
			messagePanel = createMessagePanel(updateActivePlayerLabel(board.getActivePlayer()));
			if (!hideNextPhaseButton(board)) {
				System.out.println("Created Attack Button");
				attackButton = viewUtility.createGamePhaseButton("Attack");
				messagePanel.add(attackButton);
			}
			mainBoardFrame.add(messagePanel);
			mainBoardFrame.revalidate();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void showAttackBoard(Board board) {

		try {

			if (attackButton != null) {
				mainBoardFrame.remove(attackButton);
			}
			if (messagePanel != null) {
				mainBoardFrame.remove(messagePanel);
			}

			AttackPanelView attackPanel = new AttackPanelView(board);
			Dimension dim = new Dimension();
			dim.setSize(mainBoardFrame.getWidth() - 350, 90);
			attackPanel.setPreferredSize(dim);
			attackButton = viewUtility.createGamePhaseButton("End Attack");
			messagePanel = createMessagePanel(updateAttackMessage());

			mainBoardFrame.add(attackPanel);
			mainBoardFrame.add(messagePanel);
			mainBoardFrame.add(attackButton);

			mainBoardFrame.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		activePlayerLabel.setText("Place 3 armies at your turn. Current Player is " + player.getName());
		activePlayerLabel.setForeground(Color.decode("#525b5c"));
		activePlayerLabel.setVisible(true);
		return activePlayerLabel;
	}

	private JLabel updateAttackMessage() {
		JLabel activePlayerLabel = new JLabel();
		activePlayerLabel.setText("Message goes here");
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
}
