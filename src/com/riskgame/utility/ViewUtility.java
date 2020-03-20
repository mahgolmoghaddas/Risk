package com.riskgame.utility;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.riskgame.enums.GameScreen;
import com.riskgame.model.Continent;
import com.riskgame.model.Territory;
import com.riskgame.model.World;

public class ViewUtility {

	

	public JFrame createMainFrame(String name, boolean showBackGround) {
		JFrame frame = new JFrame(name);
		try {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			double width = GameScreen.WIDTH.getValue();
			double height = GameScreen.HEIGHT.getValue();
			dim.width = (int) (dim.width * width);
			dim.height = (int) (dim.height * height);

			System.out.println(dim.getHeight() + " " + dim.getWidth());
			frame.setSize(dim);

			frame.getContentPane().setLayout(new FlowLayout());
			if (showBackGround) {
				final JLabel backGround = new JLabel(new ImageIcon(((new ImageIcon("resources/images/RiskGame.jpg")
						.getImage().getScaledInstance(frame.getSize().width, (int) ((int) frame.getSize().height - 30),
								java.awt.Image.SCALE_SMOOTH)))));

				frame.add(backGround);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frame;
	}
	
	
	/**
	 * create and return the frame for mapeditor
	 * @param name
	 * @return JFrame
	 * @throws Exception
	 */
	public JFrame editFrame(String name) throws Exception {
		JFrame frame = new JFrame(name);
		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			double width = GameScreen.WIDTH.getValue();
			double height = GameScreen.HEIGHT.getValue();
			dim.width = (int) (dim.width * width);
			dim.height = (int) (dim.height * height);

			System.out.println(dim.getHeight() + " " + dim.getWidth());
			frame.setSize(dim);
	        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); // top to bottom
	        frame.setLayout(boxLayout);
			
			return frame;
	}
	
	

	public JMenu createMenu(String name) throws Exception {
		JMenu menu = new JMenu(name);
		menu.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 13));

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		menu.setCursor(cursor);
		return menu;
	}

	public JTable createWorldMapTable(World world) throws Exception {

		final String[] columnNames = { "Continent", "Bonus", "Territory" };
		JTable worldMapTable = new JTable();

		try {
			DefaultTableModel dataModel = new DefaultTableModel(0, 0);
			dataModel.setColumnIdentifiers(columnNames);
			if (world != null && world.getContinents() != null && !world.getContinents().isEmpty()) {

				Iterator<Continent> continentIterator = world.getContinents().iterator();

				while (continentIterator.hasNext()) {

					Continent continent = continentIterator.next();

					double bonus = continent.getBonusPoint();

					Iterator<Territory> territoryIterator = continent.getTerritoryList().iterator();
					String territories = "";
					
					while(territoryIterator.hasNext()) {
						territories = territories+","+territoryIterator.next().getCountryName();
					}
					
					territories = territories.replace(",", "");

					dataModel.addRow(new Object[] { continent.getContinentName(), bonus + "", territories });
				}
				worldMapTable.setModel(dataModel);
				worldMapTable.setLocation(250, 300);
				worldMapTable.setVisible(true);
			} else {
				if(world==null) {
					System.out.print("world is null");
				}
				else if(world.getContinents() == null) {
					System.out.print("Continent is null");
				}
				else if(world.getContinents().isEmpty()) {
					System.out.print("Continent is empty");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return worldMapTable;
	}

}
