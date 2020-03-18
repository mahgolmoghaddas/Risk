package com.riskgame.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.ViewUtility;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class BoardView implements Observer {

	private Board board;
	private ViewUtility viewUtility = new ViewUtility();
	private JFrame mainBoardFrame;
	private WorldMapPanel worldMapPanel;

	public BoardView(Board board) {
		this.board = board;
		board.addObserver(this);
		showGameBoard(board);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Updated..");
		if (o instanceof Board) {

		}
		board = (Board) o;
		showGameBoard(board);
	}

	public void showGameBoard(Board board) {
		try {
			mainBoardFrame = viewUtility.createMainFrame("Play Game", false);
			mainBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			drawMap(board.getWorld().getImage());
			Container container = mainBoardFrame.getContentPane();
			container.add(worldMapPanel, "Center");
			
			mainBoardFrame.setVisible(true);
			printPlayerDetails(board);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawMap(String mapName) throws Exception{
		worldMapPanel = new WorldMapPanel();
		String fileName = "resources/images/"+mapName;
		File file = new File(fileName);
		BufferedImage image =  ImageIO.read(file);
		worldMapPanel.setImage(image);
	}
	
	public void printPlayerDetails(Board board) {
		System.out.println("playerlist size:::"+ board.getPlayerList().size());
		for(Player player:board.getPlayerList()) {
			System.out.println(player.getName() + " ::"+player.getArmiesHeld());
			System.out.println(player.getCountriesOwned().size());
			for(Territory territory : player.getCountriesOwned()) {
				
				System.out.println(territory.getArmyCount());
				System.out.println(territory.getCountryName());
				System.err.println(territory.getOwner().getName());
			}
		}
	}

	
}

class WorldMapPanel extends JPanel {
	BufferedImage image;
	Dimension size = new Dimension();

	public WorldMapPanel() {
	}

	public WorldMapPanel(BufferedImage image) {
		this.image = image;
		setComponentSize();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	public Dimension getPreferredSize() {
		return size;
	}

	public void setImage(BufferedImage bi) {
		image = bi;
		setComponentSize();
		repaint();
	}

	private void setComponentSize() {
		if (image != null) {
			size.width = image.getWidth();
			size.height = image.getHeight();
			revalidate();
		}
	}
}
