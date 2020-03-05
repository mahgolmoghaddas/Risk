package com.riskgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.riskgame.controller.CreateMapController;
import com.riskgame.controller.EditMapController;
import com.riskgame.controller.GameController;
import com.riskgame.controller.SaveMapController;
import com.riskgame.utility.ViewUtility;

public class MainWindowView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainWindowView mainWindowView;
	private JFrame mainFrame;

	private ViewUtility viewUtility = new ViewUtility();

	public MainWindowView() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
		getContentPane().setBackground(Color.LIGHT_GRAY);
	}

	public static MainWindowView getInstatnce() {

		if (mainWindowView == null) {
			mainWindowView = new MainWindowView();
		}
		return mainWindowView;
	}

	public void launchMainWindow() {
		try {
			mainFrame = viewUtility.createMainFrame("Risk Game",true);

			JMenuBar menuBar = new JMenuBar();
			menuBar.setBackground(Color.LIGHT_GRAY);

			menuBar.add(createGameMenu());
			menuBar.add(createMapMenu());

			mainFrame.setJMenuBar(menuBar);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JMenu createGameMenu() throws Exception {
		JMenu openMenu = viewUtility.createMenu("Open");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		JMenuItem gameMenuItem = new JMenuItem("New Game");
		gameMenuItem.setCursor(cursor);
		gameMenuItem.addActionListener(new GameController());

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setCursor(cursor);
//		gameMenuItem.addActionListener(new GameController());
		
		openMenu.add(gameMenuItem);
		openMenu.add(exitMenuItem);
		return openMenu;
	}

	public JMenu createMapMenu() throws Exception {
		JMenu mapMenu = viewUtility.createMenu("Map");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		JMenuItem createMapMenuItem = new JMenuItem("Create Map");
		createMapMenuItem.setCursor(cursor);
		createMapMenuItem.addActionListener(new CreateMapController());

		JMenuItem editMapMenuItem = new JMenuItem("Edit Map");
		editMapMenuItem.setCursor(cursor);
		editMapMenuItem.addActionListener(new EditMapController());

		JMenuItem saveMapMenuItem = new JMenuItem("Save Map");
		saveMapMenuItem.setCursor(cursor);
		saveMapMenuItem.addActionListener(new SaveMapController());

		mapMenu.add(createMapMenuItem);
		mapMenu.add(editMapMenuItem);
		mapMenu.add(saveMapMenuItem);

		return mapMenu;
	}
}
