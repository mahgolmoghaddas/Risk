package com.riskgame.view;

import com.riskgame.model.Game;

import java.awt.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import com.riskgame.utility.MapReader;
import com.riskgame.model.Player;
import com.riskgame.view.GameFrame;
public class BoardView extends JFrame {
    public static JPanel cardHolder;
    //public static JPanel cardsContainerPanel;
    public static CardLayout cardLayout;
    private JMenu GameMenu;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu playMenu;
    private JLabel removeplayerLabel;
    private JTextField removePlayerName;
    private JButton removePlayerButton;
    private JMenuBar menuBar;
    private JFrame gameWindow;
    private JPanel gameLaunchPanel;
    private JPanel StartupViewPanel;
    private JButton addPlayerButton;
    private JButton mapSelectorButton;
    private JTextField mapPath;
    private JLabel playerNameLabel;
    private JLabel mapSelectorLabel;
    private JTextField playerName;
    private JButton populateCountriesButton;
    private JList<String> currentPlayerList;
    private DefaultListModel<String> model;
    private JButton showMapButton;
    private JComboBox<String> playerCount;
    private JLabel playerCountLabel;
    private MapReader reader;
    private JComboBox<String> playerList;
    private JLabel playerListLabel;
    private JMenu helpMenu;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem placearmiesoncountry;
    private JMenuItem addContinent;
    private JMenuItem addCountry;
    private JMenuItem removeContinent;
    private JMenuItem endreinforcementphase;
    private JMenuItem removeCountry;
    private JMenuItem showplayercountries;
    private JMenuItem addPlayer;
    private JMenuItem ExchangeCardsforplayer;
    private JMenuItem createMap;
    private JMenuItem ShowCurrentPlayer;
    private JMenuItem numberofplayers;
    private JMenuItem startgame;
    private JMenuItem help;
    private JMenuItem start, pause;
    private String[] numberOfPlayers={"2","3", "4","5"};
    public BoardView(JFrame frame,JPanel panel){
        this.gameWindow=frame;
        BoardView.cardHolder=panel;
        gameLaunchPanel = new JPanel();
        gameLaunchPanel.setLayout(null);
        gameLaunchPanel.setVisible(true);

        placeCards();


    }

    public void placeCards() {

        cardHolder=new JPanel(new CardLayout());
        cardHolder.add(gameLaunchPanel, "Card with Game Launching View");

        gameWindow.getContentPane().add(cardHolder, BorderLayout.CENTER);
        cardLayout=(CardLayout) cardHolder.getLayout();
        cardLayout.show(cardHolder, "Card with Game Launching View");

        gameWindow.pack();
        gameWindow.setSize(600, 700);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        initaliseUI();
    }

    public void initaliseUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);
        fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
        editMenu = new JMenu("Edit");
        editMenu.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
        GameMenu = new JMenu("Game");
        GameMenu.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
        helpMenu = new JMenu("Help");
        helpMenu.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));
        playMenu = new JMenu("Play");
        playMenu.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 13));

        open = new JMenuItem("Open");
        save = new JMenuItem("Save to file");
        addContinent = new JMenuItem("Add Continent");
        addCountry = new JMenuItem("Add  Country");
        removeContinent = new JMenuItem("Remove Continent");
        removeCountry = new JMenuItem("Remove Country");
        showplayercountries = new JMenuItem("Show Player Countries");
        numberofplayers = new JMenuItem(" Start Game");
        endreinforcementphase = new JMenuItem("End reinforcement phase");
        createMap = new JMenuItem("Create Map");
        ShowCurrentPlayer = new JMenuItem("Show Current Player");
        placearmiesoncountry = new JMenuItem("Place Armies On Country");
        placearmiesoncountry = new JMenuItem("Place Armies On Country");
        ExchangeCardsforplayer = new JMenuItem("Exchange Cards");
        /*
         * Create the menu items for the simulation menu.
         */
        help = new JMenuItem("Help");
        start = new JMenuItem("Start");
        pause = new JMenuItem("Pause");

        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(save);
        fileMenu.add(createMap);

        editMenu.add(addContinent);
        editMenu.add(removeContinent);
        editMenu.addSeparator();
        editMenu.add(addCountry);
        editMenu.add(removeCountry);
        GameMenu.add(numberofplayers);
        GameMenu.add(ShowCurrentPlayer);
        GameMenu.add(showplayercountries);
        GameMenu.add(endreinforcementphase);
        GameMenu.add(ExchangeCardsforplayer);
        playMenu.add(placearmiesoncountry);
        helpMenu.add(help);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(GameMenu);
        menuBar.add(playMenu);
        menuBar.add(helpMenu);
        StartupViewPanel = new JPanel();
        StartupViewPanel.setVisible(true);
        StartupViewPanel.setLayout(null);


        playerCount = new JComboBox<String>(numberOfPlayers);
        playerCountLabel = new JLabel("Select player count");
        StartupViewPanel.add(playerCountLabel);
        playerCountLabel.setVisible(true);
        playerCountLabel.setBounds(36, 90, 121,20);
        StartupViewPanel.add(playerCount);
        playerCount.setBounds(216, 90, 121, 20);




        playerNameLabel = new JLabel("Enter Player name to add");
        StartupViewPanel.add(playerNameLabel);
        playerNameLabel.setVisible(true);
        playerNameLabel.setBounds(36, 120, 200, 21);

        playerName = new JTextField();
        StartupViewPanel.add(playerName);
        playerName.setVisible(true);
        playerName.setBounds(216, 120, 121, 20);

        addPlayerButton=new JButton("Add Player");
        addPlayerButton.setBounds(350, 120, 121, 20);
        addPlayerButton.setVisible(true);
        StartupViewPanel.add(addPlayerButton);
        //addPlayerButton.color(Black);

        removeplayerLabel = new JLabel("Enter Player name to remove");
        StartupViewPanel.add(removeplayerLabel);
        removeplayerLabel.setVisible(true);
        removeplayerLabel.setBounds(36, 150, 200, 21);

        removePlayerName = new JTextField();
        StartupViewPanel.add(removePlayerName);
        removePlayerName.setVisible(true);
        removePlayerName.setBounds(216, 150, 121, 20);

        removePlayerButton=new JButton("Remove Player");
        removePlayerButton.setBounds(350, 150, 121, 20);
        removePlayerButton.setVisible(true);
        StartupViewPanel.add(removePlayerButton);


        populateCountriesButton = new JButton("Populate Countries and Assign Armies");
        StartupViewPanel.add(populateCountriesButton);
        populateCountriesButton.setBounds(36, 180, 250, 30);
        populateCountriesButton.setVisible(true);



        showMapButton = new JButton("Show Current Game Map");
        StartupViewPanel.add(showMapButton);
        showMapButton.setBounds(300, 180, 200, 30);
        //showMapButton.setHorizontalAlignment(SwingConstants.LEFT);
        showMapButton.setVisible(true);
        gameWindow.setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardHolder.add(StartupViewPanel,"");
        gameWindow.getContentPane().add(cardHolder, BorderLayout.CENTER);
        cardLayout=(CardLayout) cardHolder.getLayout();
        cardLayout.show(cardHolder, "");
    }




    /**
     * Getter to the element shopMapButton
     * @return shopMapButton
     */
    public JButton getShowMapButton() {
        return showMapButton;
    }

    /**
     * Getter method for the Playername text field
     * @return playerName
     */
    public JTextField getPlayerName() {
        return playerName;
    }

    /**
     * Getter method for the populate countries Button
     * @return populateCountriesButton
     */
    public JButton getPopulateCountriesButton() {
        return populateCountriesButton;
    }

    /**
     * Getter method for removeplayer Button
     * @return removePlayerButton
     */
    public JButton getRemovePlayerButton() {
        return removePlayerButton;
    }

    /**
     * Getter method for the getRemovePlayerName text field
     * @return removePlayerName
     */
    public JTextField getRemovePlayerName() {
        return removePlayerName;
    }

    /**
     * Getter method for the Model
     * @return model
     */
    public DefaultListModel<String> getModel() {
        return model;
    }




}
