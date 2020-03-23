package com.riskgame.view;

import java.awt.FlowLayout;
import java.io.File;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.controller.GameController;
import com.riskgame.model.Continent;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;

public class NewGameView extends JFrame{

	private JFrame newGameFrame;
	private JPanel gamePanel;
	private JButton cancelButton;
	private JButton startGameButton;

	private ViewUtility viewUtility = new ViewUtility();
	private MapReader mapReader = new MapReader();

	private World currentWorldMap;
	private int playersCount;

	private GameController gameController;

	
	
	public NewGameView(GameController gameController) {
		this.gameController = gameController;
	}

	public void launchNewGameFrame() {
		try {
			newGameFrame = viewUtility.createMainFrame("Start Game", false);

			newGameFrame.setSize(MainWindowView.getInstance().getSize());
			newGameFrame.setLocationRelativeTo(null);

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
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		gamePanel.setBorder(new EmptyBorder(20, 10, 10, 10));

		gamePanel.add(createPlayerComboPanel());
		gamePanel.add(createLoadMapPanel());
		gamePanel.add(createPlayGamePanel());
		return gamePanel;
	}

	public JPanel createPlayerComboPanel() {

		JPanel panelComboBox = new JPanel();
		panelComboBox.setLayout(new FlowLayout());
		JLabel selectplayerlabel = new JLabel("Select Number of  Players:");
		int maxnumberofplayers = 5;
		JComboBox comboBoxlist = new JComboBox();
		for (int i = 0; i <= maxnumberofplayers; i++) {
			comboBoxlist.addItem(i);
		}

		comboBoxlist.addItemListener(itemListener -> {
			playersCount = Integer.parseInt(comboBoxlist.getSelectedItem().toString());
			if (playersCount < 2) {
				JOptionPane.showMessageDialog(newGameFrame.getContentPane(), "Minimum Number of Players Must be : 2",
						"MESSAGE", JOptionPane.ERROR_MESSAGE);
			}
		});

		panelComboBox.add(selectplayerlabel);
		panelComboBox.add(comboBoxlist);
		return panelComboBox;
	}

	public JPanel createLoadMapPanel() throws Exception {
		JPanel loadMapPanel = new JPanel();
		JLabel loadMapLabel = new JLabel("Load map:");
		loadMapPanel.add(loadMapLabel);
		loadMapPanel.add(createLoadMapButton());
		return loadMapPanel;
	}

	public JButton createLoadMapButton() {
		JButton loadMapButton = new JButton("Choose Map");
		loadMapButton.addActionListener(actionEvent -> {

			JFileChooser chooser = new JFileChooser();
			try {
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
				chooser.setFileFilter(mapFileFilter);

				int selection = chooser.showOpenDialog(newGameFrame.getContentPane());

				if (selection != JFileChooser.CANCEL_OPTION) {
					File mapFile = chooser.getSelectedFile();
					if (mapReader.isValidMap(mapFile)) {
						JOptionPane.showMessageDialog(newGameFrame.getContentPane(),
								"Map Loaded successfully.Click Start Game.", "MESSAGE",
								JOptionPane.INFORMATION_MESSAGE);

						currentWorldMap = mapReader.createWorldMap();
//						printWorldMap();
						gameController.setGameParameters(currentWorldMap, playersCount, true);
						startGameButton.setVisible(true);
						cancelButton.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(newGameFrame.getContentPane(), "Unsupported Map File", "MESSAGE",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return loadMapButton;
	}

	public JPanel createPlayGamePanel() {
		JPanel playGamePanel = new JPanel();
		playGamePanel.setLayout(new FlowLayout());
		playGamePanel.add(createStartGameButton());
		playGamePanel.add(createCancelButton());
		return playGamePanel;
	}

	public JButton createCancelButton() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionEvent -> {
			newGameFrame.dispose();
		});
		cancelButton.setVisible(false);
		return cancelButton;
	}

	public JButton createStartGameButton() {
		startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(gameController);
		startGameButton.setVisible(false);
		return startGameButton;
	}

	public void printWorldMap() {
		System.out.println("World MAP:: " + currentWorldMap);
		System.out.println("WorldMap ::" + currentWorldMap.getAuthor());
		Iterator<Continent> continentIterator = currentWorldMap.getContinents().iterator();

		while (continentIterator.hasNext()) {
			Continent continent = continentIterator.next();

			System.out.println(continent.getContinentName() + " ::BONUS" + continent.getBonusPoint());
			System.out.println("*********");

			Iterator<Territory> territoryIterator = continent.getTerritoryList().iterator();
			while (territoryIterator.hasNext()) {
				Territory territory = territoryIterator.next();
				System.out.println(territory.getCountryName() + " " + territory.getTerritoryPosition().getX() + "  "
						+ territory.getTerritoryPosition().getY());
				System.out.println(territory.getNeighborsTerritory().toString());

			}
			System.out.println(" #########################");
		}
	}

}
