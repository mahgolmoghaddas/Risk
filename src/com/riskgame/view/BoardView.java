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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.utility.DiceType;
import com.riskgame.utility.ViewUtility;

public class BoardView implements Observer {

	private Board board;
	private ViewUtility viewUtility = new ViewUtility();
	static JFrame mainBoardFrame;

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

			WorldMapPanel worldMapPanel = drawMap(board);
			mainBoardFrame.getContentPane().add(worldMapPanel, "Center");

			mainBoardFrame.getContentPane().add(createDiceRollPanel(), "Center");
			mainBoardFrame.getContentPane().add(createPlayerPanel(board));
			mainBoardFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public JPanel createPlayerPanel(Board board) {

		JPanel playerPanel = new JPanel();
		playerPanel.setPreferredSize(getPreferredSizeForBoardPanel());

		try {
			List<Player> playerList = board.getPlayerList();
			if (playerList != null && !playerList.isEmpty()) {
				for (int i = 0; i < playerList.size(); i++) {
					JLabel playerLabel = viewUtility.createPlayerLabel(playerList.get(i).getName());
					JTextField textField = viewUtility.createPlayerArmiesTextField(playerList.get(i));
					playerPanel.add(playerLabel);
					playerPanel.add(textField);
				}
			}
			playerPanel.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return playerPanel;
	}

	public JPanel createDiceRollPanel() {
		DicePanel diceRollPanel = new DicePanel(DiceType.Defend);
		diceRollPanel.setPreferredSize(getPreferredSizeForBoardPanel());
		diceRollPanel.setBackground(Color.decode("#03c2fc"));
		return diceRollPanel;
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

class WorldMapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Board board;
	private BufferedImage mapImage;
	Dimension size = new Dimension();

	public WorldMapPanel(Board board, BufferedImage mapImage) {
		this.board = board;
		this.mapImage = mapImage;
		setComponentSize();
	}

	@Override
	public void paint(Graphics graphics) {
		try {
			Graphics2D g2D = (Graphics2D) graphics;
			g2D.drawImage(this.mapImage, 0, 0, this);
			setComponentSize();
//			drawTerritoriesInMap();
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

					JTextField text = new JTextField();
					int x = (int) territory.getTerritoryPosition().getX();
					int y = (int) territory.getTerritoryPosition().getY();
					text.setEditable(false);
					text.setCursor(new Cursor(Cursor.HAND_CURSOR));
					text.setVisible(true);
					text.setName(territory.getCountryName());
					text.setText(territory.getArmyCount() + "");
					text.setBackground(territory.getOwner().getColor());
					text.setBounds(x, y, 15, 15);
					text.addMouseListener(new MouseListener() {
						@Override
						public void mouseReleased(java.awt.event.MouseEvent e) {
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
							JOptionPane.showMessageDialog(BoardView.mainBoardFrame.getContentPane(), "Mouse clicked.",
									"MESSAGE", JOptionPane.INFORMATION_MESSAGE);
							JTextField eventCOmp = (JTextField) e.getComponent();
							System.out.println("Target " + eventCOmp.getName());
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

	private void mouseClicked(JTextField textField) {

	}

}
