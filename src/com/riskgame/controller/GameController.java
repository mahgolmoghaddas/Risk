package com.riskgame.controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.model.Continent;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;
import com.riskgame.view.MainWindowView;

public class GameController implements ActionListener {

	ViewUtility viewUtility = new ViewUtility();
	JFrame newGameFrame;
	JPanel gamePanel;
	JComboBox comboBoxlist;
	MainWindowView mainWindowView;
	World currentWorldMap;

	public GameController() {

		mainWindowView = MainWindowView.getInstatnce();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		try {

			newGameFrame = viewUtility.createMainFrame("Start Game", false);

			gamePanel = createGamePanel();
			newGameFrame.add(gamePanel);

			newGameFrame.setVisible(true);
			newGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private JPanel createGamePanel() throws Exception {
		gamePanel = new JPanel();
		gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		gamePanel.setBorder(new EmptyBorder(20, 10, 10, 10));

		createSelectPlayerPanel();
		createLoadMapPanel();

		return gamePanel;
	}

	private void createSelectPlayerPanel() throws Exception {

		JPanel panelComboBox = createPlayerComboPanel();

		gamePanel.add(panelComboBox);
		gamePanel.add(createOKPlayerButton(comboBoxlist));
		gamePanel.add(createCancelPlayerButton());

	}

	public JButton createCancelPlayerButton() {
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGameFrame.dispose();
			}
		});
		return buttonCancel;
	}

	public JButton createOKPlayerButton(JComboBox comboBoxlist) {
		JButton buttonOK = new JButton("OK ");
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberofplayerselected = Integer.parseInt(comboBoxlist.getSelectedItem().toString());
				if (numberofplayerselected < 2) {
					JOptionPane.showMessageDialog(null, "Minimum Number of Players Must be : 2  ");
					newGameFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Number of Players Selected  " + numberofplayerselected);
				}
			}
		});
		return buttonOK;
	}

	public JPanel createPlayerComboPanel() {

		JPanel panelComboBox = new JPanel();
		panelComboBox.setLayout(new FlowLayout());
		JLabel selectplayerlabel = new JLabel("Select Number of  Players:");
		int maxnumberofplayers = 5;
		comboBoxlist = new JComboBox();
		for (int i = 0; i <= maxnumberofplayers; i++) {
			comboBoxlist.addItem(i);
		}

		panelComboBox.add(selectplayerlabel);
		panelComboBox.add(comboBoxlist);
		return panelComboBox;
	}

	public void createLoadMapPanel() throws Exception{
		JLabel loadMapLaber = new JLabel("Load map:");
		gamePanel.add(loadMapLaber);
		gamePanel.add(createLoadMapButton());
	}

	public JButton createLoadMapButton() {
		JButton loadMapButton = new JButton("Choose Map");
		loadMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				JFileChooser chooser = new JFileChooser();
				try {
					chooser.setMultiSelectionEnabled(false);
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

					FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
					chooser.setFileFilter(mapFileFilter);

					int selection = chooser.showOpenDialog(mainWindowView.getContentPane());

					if (selection != JFileChooser.CANCEL_OPTION) {
						File mapFile = chooser.getSelectedFile();

						MapReader mapReader = new MapReader();
						
						if(mapReader.isValidMap(mapFile)) {
							
							currentWorldMap = mapReader.createWorldMap();
							printWorldMap();
							
							JOptionPane.showMessageDialog(newGameFrame.getContentPane(), "Map Loaded successfully", "MESSAGE",
									JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(newGameFrame.getContentPane(), "Unsupported Map File", "MESSAGE",
									JOptionPane.ERROR_MESSAGE);
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return loadMapButton;

	}

	public void printWorldMap() {
		System.out.println("World MAP:: "+currentWorldMap);
		System.out.println("WorldMap ::" + currentWorldMap.getAuthor());
		
		for(int i=0;i<currentWorldMap.getContinents().size();i++) {
			Continent continent = currentWorldMap.getContinents().get(i);
			
			System.out.println(continent.getContinentName()+" ::BONUS"+continent.getBonusPoint());
			System.out.println("*********");
			
			for(int j =0;j<continent.getTerritoryList().size();j++) {
				
				Territory territory = continent.getTerritoryList().get(j);
				System.out.println(territory.getCountryName() +" "+territory.getTerritoryPosition().getX() + "  "+territory.getTerritoryPosition().getY());
				System.out.println(territory.getNeighborsTerritory().toString());
				
			}
			System.out.println(" #########################");
		}
		
		
	}
}
