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

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceType;
import com.riskgame.utility.ViewUtility;

public class BoardView implements Observer {

	private Board board;
	private ViewUtility viewUtility = new ViewUtility();
	static JFrame mainBoardFrame;
	WorldMapPanel worldMapPanel;
	JPanel diceRollPanel; 
	
	Map<Integer,JTextField> armiesField = new HashMap<>();
	Map<Integer,JLabel> diceList = new HashMap<>();

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
		}
//		Update board data
		
	}
	
	/**
	 * This method displays the board view as per the Board data which includes player,no of territories acquired by the player
	 * and number of armies hold by player
	 * */
	public void showGameBoard(Board board) {
		try {
			mainBoardFrame = viewUtility.createMainFrame("Play Game", false);
			mainBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			worldMapPanel = drawMap(board);
			mainBoardFrame.getContentPane().add(worldMapPanel, "Center");
			PlayerPanelView playerPanel = new PlayerPanelView(board);
			playerPanel.setPreferredSize(getPreferredSizeForBoardPanel());

			mainBoardFrame.getContentPane().add(createDiceRollPanel(board), "Center");
			mainBoardFrame.getContentPane().add(playerPanel);
			mainBoardFrame.getContentPane().add(createFinishMovePanel(),"Center");
			mainBoardFrame.setVisible(true);
			JOptionPane.showMessageDialog(mainBoardFrame, "Roll dice to decide the player turn");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method draws map with the provided World data with the respective 
	 * continents and the countries details
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
	 * @return
	 */
	public JPanel createDiceRollPanel(Board board) {
		diceRollPanel = new DicePanel(DiceType.Defend,board,true);
		diceRollPanel.setPreferredSize(getPreferredSizeForBoardPanel());
		diceRollPanel.setBackground(Color.decode("#03c2fc"));
		return diceRollPanel;
	}

	
	/**
	 * This method creates a finish button to specify 
	 * that a current user is done with a particular move.
	 * 
	 * @return
	 */
	public JPanel createFinishMovePanel() {
		JPanel finishMovePanel = new JPanel();
		finishMovePanel.setPreferredSize(getPreferredSizeForBoardPanel());
		JButton finishMove = new JButton("Finish Move");
		finishMove.setBackground(Color.decode("#f5b942"));
		finishMove.setUI(new BasicButtonUI());
		finishMovePanel.add(finishMove, "Center");
		finishMove.setVisible(true);
		finishMovePanel.setVisible(true);
		return finishMovePanel;
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
		dimension.setSize(mainBoardFrame.getWidth() - 350, 60);
		return dimension;
	}

}
