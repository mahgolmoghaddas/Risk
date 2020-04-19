package com.riskgame.view;

import java.awt.FlowLayout;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.controller.TournamentController;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;

/**
 * This class creates the view to select no of Maps, no of games, no of players
 * with different strategies to be played in the tournament
 * 
 * @author pushpa
 *
 */
public class TournamentView {

	JFrame tournamentFrame;
	JPanel tournamentParentPanel;
	JPanel mapSelectorPanel;
	PlayerSelectionView playerPanel;
	ViewUtility viewUtility = new ViewUtility();
	private MapReader mapReader = new MapReader();
	int noOfMap = 0;
	int noOfPlayer = 0;
	int noOfGame = 0;
	int totalTurns = 0;

	public TournamentView() {

		tournamentFrame = viewUtility.createMainFrame("Tournament Mode", false);
		tournamentFrame.setVisible(true);
		tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tournamentFrame.setLocationRelativeTo(null);
		addComponents();
	}

	private void addComponents() {
		createtournamentParentPanel();
		tournamentFrame.add(tournamentParentPanel);
		tournamentFrame.revalidate();
	}

	private void createtournamentParentPanel() {
		tournamentParentPanel = new JPanel();
		tournamentParentPanel.setLayout(new BoxLayout(tournamentParentPanel, BoxLayout.Y_AXIS));
		tournamentParentPanel.add(createMapPanel());
		tournamentParentPanel.add(createPlayerSelectionPanel());
		tournamentParentPanel.add(createGameAndTurnsPanel());
		tournamentParentPanel.add(createStartAndCancelPanel());
	}

	/**
	 * This method creates Map Panel for selecting the number of maps to be used in
	 * the tournament. According to the number of selected maps, it displays the map
	 * selector
	 * 
	 * @return JPanel panel with combobox and map selector
	 */
	private JPanel createMapPanel() {
		JPanel mapParentPanel = new JPanel();
		JLabel noOfMapsLabel = new JLabel("Select number of Map:");
		JComboBox<Integer> noOfMapsCombo = new JComboBox<>();
		for (int i = 1; i <= 5; i++) {
			noOfMapsCombo.addItem(i);
		}
		noOfMapsCombo.addItemListener(listener -> {
			noOfMap = Integer.parseInt(noOfMapsCombo.getSelectedItem().toString());
			// Creating map loader
			if (mapSelectorPanel != null) {
				mapParentPanel.remove(mapSelectorPanel);
			}
			mapSelectorPanel = new JPanel();
			for (int j = 1; j <= noOfMap; j++) {
				mapSelectorPanel.add(createLoadMapButton(j));
			}
			mapParentPanel.add(mapSelectorPanel);
			mapParentPanel.revalidate();
		});

		mapParentPanel.add(noOfMapsLabel);
		mapParentPanel.add(noOfMapsCombo);
		return mapParentPanel;
	}

	/**
	 * Creates a button to load an existing world map file in the window. It
	 * validates the map file and creates a world object and show the button to
	 * start the game if the map file is valid one.
	 * 
	 * @return JButton button with file loader
	 * 
	 */
	public JButton createLoadMapButton(int i) {
		JButton loadMapButton = new JButton("Choose Map");
		loadMapButton.setName("MAP" + i);
		loadMapButton.addActionListener(actionEvent -> {
			JFileChooser chooser = new JFileChooser();
			try {
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
				chooser.setFileFilter(mapFileFilter);

				int selection = chooser.showOpenDialog(tournamentFrame.getContentPane());

				if (selection != JFileChooser.CANCEL_OPTION) {
					File mapFile = chooser.getSelectedFile();
					if (mapReader.isValidMap(mapFile)) {
						// TODO'
						System.out.println("VALID MAP " + i);
					} else {
						JOptionPane.showMessageDialog(tournamentFrame.getContentPane(), "Unsupported Map File",
								"MESSAGE", JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return loadMapButton;
	}

	/**
	 * This method creates the view to select the number of players in each game of
	 * tournament along with the player strategies
	 * 
	 * @return JPanel player details selection panel
	 */
	private JPanel createPlayerSelectionPanel() {
		JPanel playerParentPanel = new JPanel();
		playerParentPanel.setLayout(new FlowLayout());

		JLabel noOfPlayersLabel = new JLabel("Select number of Players:");
		JComboBox<Integer> noOfPlayersCombo = new JComboBox<>();
		for (int i = 2; i <= 4; i++) {
			noOfPlayersCombo.addItem(i);
		}
		noOfPlayersCombo.addItemListener(listener -> {
			noOfPlayer = Integer.parseInt(noOfPlayersCombo.getSelectedItem().toString());
			if (playerPanel != null) {
				playerParentPanel.remove(playerPanel);
			}
			playerPanel = new PlayerSelectionView(noOfPlayer, true);
			playerParentPanel.add(playerPanel);
			playerParentPanel.revalidate();

		});
		playerParentPanel.add(noOfPlayersLabel);
		playerParentPanel.add(noOfPlayersCombo);
		return playerParentPanel;
	}

	/**
	 * This panel creates the view to choose the number of game to be played on each
	 * map and the number of maximum turns allowed in a particular game in the
	 * tournament
	 * 
	 * @return JPanel panel with game and turn combox box
	 */
	private JPanel createGameAndTurnsPanel() {
		JPanel jpanel = new JPanel();

		JLabel noOfGameLabel = new JLabel("Select number of Game:");
		JComboBox<Integer> noOfGameCombo = new JComboBox<>();
		for (int i = 1; i <= 5; i++) {
			noOfGameCombo.addItem(i);
		}
		noOfGameCombo.addItemListener(listener -> {
			noOfGame = Integer.parseInt(noOfGameCombo.getSelectedItem().toString());

		});

		JLabel turnsLabel = new JLabel("Select maximum turns:");
		JComboBox<Integer> noOfTurnsCombo = new JComboBox<>();
		for (int i = 10; i <= 50; i++) {
			noOfTurnsCombo.addItem(i);
		}
		noOfTurnsCombo.addItemListener(listener -> {
			totalTurns = Integer.parseInt(noOfTurnsCombo.getSelectedItem().toString());

		});
		jpanel.add(noOfGameLabel);
		jpanel.add(noOfGameCombo);
		jpanel.add(turnsLabel);
		jpanel.add(noOfTurnsCombo);
		return jpanel;
	}

	/**
	 * This method creates a view displaying buttons to start and cancel the
	 * tournament mode
	 * 
	 * @return JPanel with start and cancel button
	 */
	private JPanel createStartAndCancelPanel() {

		JPanel jpanel = new JPanel();

		JButton startButton = new JButton("Start Tournament");
		startButton.addActionListener(new TournamentController(true));

		JButton cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(actionEvent -> {
			tournamentFrame.dispose();
		});

		jpanel.add(startButton);
		jpanel.add(cancelButton);
		return jpanel;

	}

}
