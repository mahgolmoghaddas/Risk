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
	JPanel diceRollPanel;
	JPanel finishSetupPanel;
	JPanel messagePanel;
	int currentPlayerId = 0;

	Map<Integer, JTextField> armiesField = new HashMap<>();
	Map<Integer, JLabel> diceList = new HashMap<>();

	public BoardView(Board board) {
		this.board = board;
		board.addObserver(this);
		showGameBoard(board);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Updated..BOARD DATA");
		if (o instanceof Board) {
			board = (Board) o;
			if (GamePhase.REINFORCE.equals(GameController.getInstance().getGamePhase())) {
				showReinforceBoard(board);
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

			WorldMapPanel worldMapPanel = drawMap(board);
			PlayerPanelView playerPanel = new PlayerPanelView(board);
			playerPanel.setPreferredSize(getPreferredSizeForBoardPanel());
			JPanel turnDicePanel = createDiceRollPanel(board);
			finishSetupPanel = createFinishPhasePanel("Place armies");

			mainBoardFrame.getContentPane().add(worldMapPanel, "Center");
			mainBoardFrame.getContentPane().add(turnDicePanel, "Center");
			mainBoardFrame.getContentPane().add(playerPanel);
			mainBoardFrame.getContentPane().add(finishSetupPanel, "Center");

			mainBoardFrame.setVisible(true);
			JOptionPane.showMessageDialog(mainBoardFrame, "Roll dice to decide the player turn");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showReinforceBoard(Board board) {
		try {
			mainBoardFrame.remove(diceRollPanel);
			mainBoardFrame.remove(finishSetupPanel);
			if (messagePanel != null && messagePanel.getComponents().length > 0) {
				messagePanel.removeAll();
				mainBoardFrame.remove(messagePanel);
			}
			messagePanel = createMessagePanel(updateActivePlayerLabel(board.getActivePlayer()));
			if (!hideAttackButton(board)) {
				System.out.println("Created Attack Button");
				messagePanel.add(viewUtility.createGamePhaseButton("Attack"));
			}
			mainBoardFrame.add(messagePanel);
			mainBoardFrame.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAttackBoard(Board board) {

		try {
			AttackPanelView attackPanel = new AttackPanelView(board);
			Dimension dim = new Dimension();
			dim.setSize(mainBoardFrame.getWidth()-350, 80);
			attackPanel.setPreferredSize(dim);
			mainBoardFrame.add(attackPanel);
			mainBoardFrame.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hideAttackButton(Board board) {
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
	 * This mehod creates the panel to roll the dice
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
		dimension.setSize(mainBoardFrame.getWidth(), 50);
		return dimension;
	}

	private JLabel updateActivePlayerLabel(Player player) {
		JLabel activePlayerLabel = new JLabel();
		activePlayerLabel.setText("Current Player is " + player.getName());
		activePlayerLabel.setForeground(Color.decode("#4842f5"));
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
