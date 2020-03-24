package com.riskgame.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.riskgame.model.Board;
import com.riskgame.model.Territory;


/**
 * This class creates a WorldMap Panel and draws map with the provided World object.
 * It also allows option to place armies in the territories
 * @author gauta
 *
 */
public class WorldMapPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Board board;
	private BufferedImage mapImage;
	Dimension size = new Dimension();

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


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
