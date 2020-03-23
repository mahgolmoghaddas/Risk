package com.riskgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.riskgame.controller.CreateMapController;
import com.riskgame.controller.GameController;
import com.riskgame.controller.SaveMapController;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;

/**
 * This is the main window Frame for the game application. It has several menu options to proceed with the game.
 * @author pushpa
 *
 */
public class MainWindowView extends JFrame{

	private static final long serialVersionUID = 1L;
	private static MainWindowView mainWindowView;
	private ViewUtility viewUtility = new ViewUtility();

	private MainWindowView() {

		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
		getContentPane().setBackground(Color.LIGHT_GRAY);
	}

	public static MainWindowView getInstance() {

		if (mainWindowView == null) {
			mainWindowView = new MainWindowView();
		}
		return mainWindowView;
	}

	/**
	 * This mathod launches the main window for the game
	 */
	public void launchMainWindow() {
		try {
			createMainWindowFrame();
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBackground(Color.LIGHT_GRAY);

			menuBar.add(createGameMenu());
			menuBar.add(createMapMenu());
			menuBar.add(createEditorMenue());

			mainWindowView.setJMenuBar(menuBar);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindowView.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createMainWindowFrame() {
		mainWindowView = MainWindowView.getInstance();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		ImageIcon imgIcon = new ImageIcon("resources/images/RiskGame.jpg") ;
		mainWindowView.getContentPane().setLayout(new FlowLayout());
		
		dim.width = imgIcon.getIconWidth();
		dim.height = imgIcon.getIconHeight();
		mainWindowView.setSize(dim);
		
		final JLabel backGround = new JLabel(new ImageIcon(((new ImageIcon("resources/images/RiskGame.jpg").getImage()
				.getScaledInstance(mainWindowView.getSize().width, (int) ((int) mainWindowView.getSize().height+30),
						java.awt.Image.SCALE_SMOOTH)))));

		mainWindowView.add(backGround,"Center");
		mainWindowView.pack();
		mainWindowView.setLocationRelativeTo(null);

	}

	/**
	 * This method provides the menu to launch the New game 
	 * @return JMenu
	 * @throws Exception
	 */
	public JMenu createGameMenu() throws Exception {
		JMenu openMenu = viewUtility.createMenu("Game");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		JMenuItem gameMenuItem = new JMenuItem("New Game");
		gameMenuItem.setCursor(cursor);
		gameMenuItem.addActionListener(GameController.getInstance());

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setCursor(cursor);
//		gameMenuItem.addActionListener(new GameController());

		openMenu.add(gameMenuItem);
		openMenu.add(exitMenuItem);
		return openMenu;
	}

	/**
	 * This method provides the menu to create and save new map. Create menu asks for the several details like continents
	 * territories, bonus point and coordinates for the territories to be created. 
	 * Save menu saves the created map to a specified .map file
	 * @return JMenu
	 * @throws Exception
	 */
	public JMenu createMapMenu() throws Exception {
		JMenu mapMenu = viewUtility.createMenu("Map");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		JMenuItem createMapMenuItem = new JMenuItem("Create Map");
		createMapMenuItem.setCursor(cursor);
		createMapMenuItem.addActionListener(new CreateMapController());


		JMenuItem saveMapMenuItem = new JMenuItem("Save Map");
		saveMapMenuItem.setCursor(cursor);
		saveMapMenuItem.addActionListener(new SaveMapController());

		mapMenu.add(createMapMenuItem);

		mapMenu.add(saveMapMenuItem);

		return mapMenu;
	}


	/**
	 * method for creating the new map or editing the map
	 * @return
	 */
	public JMenu createEditorMenue(){

		JMenu editMenu=new JMenu("Editor Menu");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		JMenuItem createMapMenuItem;

		createMapMenuItem = new JMenuItem(new AbstractAction("Create New Map") {
			/**
			 * Action performed by clicking the "Create New Game" button
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				World world=new World();
				EditMapView editMapView=new EditMapView(world);
				//editMapView.setVisible(true);
			}
		});
		createMapMenuItem.setCursor(cursor);
		JMenuItem editMapMenuItem;
		editMapMenuItem =new JMenuItem(new AbstractAction("Edit Existing Map") {
			/**
			 * Action performed by clicking the "Edit Existing Map" button
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MapReader mapReader=new MapReader();
				try {
					World world=mapReader.fileChooser();
					EditMapView editMapView=new EditMapView(world);
				}
				catch (Exception exception){
					System.out.print("there is a problem with the file chosen");
				}

			}
		});
		editMapMenuItem.setCursor(cursor);
		editMenu.add(createMapMenuItem);
		editMenu.add(editMapMenuItem);
		return editMenu;
	}




}
