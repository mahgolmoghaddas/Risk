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
    public static JPanel cardsContainerPanel;
    public static CardLayout cardLayout;
    private Game gamePlay;
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
    private JPanel placeArmiesViewPanel;
    private String[] numberOfPlayers={"2","3", "4","5"};
    public BoardView(JFrame frame,JPanel panel){
        this.gameWindow=frame;
        BoardView.cardsContainerPanel=panel;
        gamePlay=new Game();
        gameLaunchPanel = new JPanel();
        gameLaunchPanel.setLayout(null);
        gameLaunchPanel.setVisible(true);
        placeCards();


    }

    public void placeCards() {

        cardsContainerPanel=new JPanel(new CardLayout());
        cardsContainerPanel.add(gameLaunchPanel, "Card with Game Launching View");

        gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
        cardLayout=(CardLayout) cardsContainerPanel.getLayout();
        cardLayout.show(cardsContainerPanel, "Card with Game Launching View");

        gameWindow.pack();
        gameWindow.setSize(700, 700);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        initaliseUI();
    }

    public void initaliseUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        mapSelectUI();
        editPlayerUI();
        placeArmiesUI();


        cardsContainerPanel.add(StartupViewPanel,"");
        cardsContainerPanel.add(placeArmiesViewPanel, "");

        gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
        cardLayout=(CardLayout) cardsContainerPanel.getLayout();
        cardLayout.show(cardsContainerPanel, "");
    }


    /**
     * Initalise UI.
     */
    public void mapSelectUI() {
        StartupViewPanel = new JPanel();
        StartupViewPanel.setVisible(true);
        StartupViewPanel.setLayout(null);


        mapSelectorLabel = new JLabel("Please Select The Map File");
        mapSelectorButton=(new JButton("Browse"));
        StartupViewPanel.add(getMapSelectorButton());
        StartupViewPanel.add(mapSelectorLabel);
        mapSelectorLabel.setVisible(true);
        mapSelectorLabel.setBounds(36, 60, 121, 20);
        mapSelectorButton.setBounds(216, 60, 121, 20);
        mapSelectorButton.setVisible(true);



        model = new DefaultListModel<String>();
        currentPlayerList=new JList<String>(model);
        playerListLabel=new JLabel("Player List");
        StartupViewPanel.add(currentPlayerList);
        StartupViewPanel.add(playerListLabel);
        playerListLabel.setBounds(530, 30, 121, 21);
        currentPlayerList.setBounds(530, 50, 121, 200);
        currentPlayerList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currentPlayerList.setVisible(true);




    }


    /**
     * Edits the player UI.
     */
    public void editPlayerUI() {

        playerCount = new JComboBox<String>(numberOfPlayers);
        playerCountLabel = new JLabel("Select player count");
        StartupViewPanel.add(playerCountLabel);
        playerCountLabel.setVisible(true);
        playerCountLabel.setBounds(36, 90, 121,20);
        StartupViewPanel.add(playerCount);
        playerCount.setBounds(216, 90, 121, 20);




        playerNameLabel = new JLabel("Enter Your Name Please");
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

        populateCountriesButton = new JButton("Populate Countries and Assign Armies");
        StartupViewPanel.add(populateCountriesButton);
        populateCountriesButton.setBounds(36, 180, 250, 30);
        populateCountriesButton.setVisible(true);



        showMapButton = new JButton("Show Current Game Map");
        StartupViewPanel.add(showMapButton);
        showMapButton.setBounds(300, 180, 200, 30);
        //showMapButton.setHorizontalAlignment(SwingConstants.LEFT);
        showMapButton.setVisible(true);

    }



    /**
     * Place armies UI.
     */
    public void placeArmiesUI() {
        placeArmiesViewPanel=new JPanel();
        ArrayList<String> playerNames=new ArrayList<String>();
        for(Player player:gamePlay.getPlayers())
            playerNames.add(player.GetName());
        playerList=new JComboBox<String>(playerNames.toArray(new String[playerNames.size()]));
        placeArmiesViewPanel.add(playerList);


        playerListLabel=new JLabel("Select the player ");
        placeArmiesViewPanel.setVisible(true);


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
     * Getter method for the addplayerbutton
     * @return addPlayerButton
     */
    public JButton getAddPlayerButton() {
        return addPlayerButton;
    }

    /**
     * Getter method for the mapPath text field
     * @return mapPath
     */
    public JTextField getMapPath() {
        return mapPath;
    }

    /**
     * Setter method for mappath TextField
     * @param mapPath mapPath
     */
    public void setMapPath(JTextField mapPath) {
        this.mapPath = mapPath;
    }

    /**
     * Getter method for the Map selection button
     * @return mapSelectorButton
     */
    public JButton getMapSelectorButton() {
        return mapSelectorButton;
    }

    /**
     * Getter method for the playercount drop down
     * @return playerCount
     */
    public JComboBox<String> getPlayerCount() {
        return playerCount;
    }

    /**
     * Getter method for the cardLayout
     * @return cardLayout
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Getter method for the Model
     * @return model
     */
    public DefaultListModel<String> getModel() {
        return model;
    }

    /**
     * Getter method for the currentplayerlist
     * @return currentPlayerList
     */
    public JList<String> getCurrentPlayerList() {
        return currentPlayerList;
    }

}
