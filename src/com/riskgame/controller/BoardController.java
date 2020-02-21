package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.riskgame.model.Game;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.view.BoardView;

/**
 * This class initializes the StartUpPhase View and invoke corresponding gameplay functions as per user-view interaction.
 */

public class BoardController implements View, PlayerCommandListener, LoadMapCommand.LoadMapListener, GameTypeListener, Observer {

    /**
     * name of the new game
     */
    private final String NEW_GAME = "/view/new-game.fxml";
    /**
     * variable of sceneView view
     */
    private final Scene sceneView;
    /**
     * variable for choose map button
     */
    private Button chooseMap;
    /**
     * variable for Start Game button
     */
    private Button startGame;
    /**
     * list of the players in the game
     */
    private List<PlayerDTO> playersList = new ArrayList<>();
    /**
     * variable for map preview image
     */
    private ImageView mapPreviewImage;
    /**
     * variable of players list view
     */
    private viewList playerViewList;
    /**
     * variable for remove player button
     */
    private Button removePlayerButton;
    /**
     * variable for text field view player name
     */
    private TextField playerName;
    /**
     * ariable for text area for the command line
     */
    private TextArea commad;
    /**
     * variable to chose color
     */
    private ColorPicker playerColor;
    /**
     * variable for add player button
     */
    private Button addPlayerButton;
    /**
     * variable for processCommand button
     */
    private Button processCommandOption;
    /**
     * variable to select player
     */
    private String selectedPlayer;
    /**
     * variable for the map data
     */
    private MapData mapData;

    /**
     * Constructor of the class that initializes objects
     * @throws IOException
     */
    public newGameController() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(NEW_GAME));
        sceneView = new Scene(root, 980, 740);
        bindView();
        //to setup mapData
        mapData = new MapData();
        //set corresponding action to the map chosen
        chooseMap.setOnAction(event -> {
            try {
                MapListController mapListController = new MapListController(name -> {
                    System.out.println(name);
                    FileReader fileReader = new FileReader("C:\\Users\\Admin\\APP\\RiskGameProject\\src\\main\\resources\\maps\\0" + name + ".map");
                    String mapData = fileReader.readData().replaceAll(MapDelimiters.CARRIAGE_DELIMITER, "");
                    MapValidator mapValidator = new MapValidator(mapData);
                    mapValidator.validate();
                    MapParser mapParser = new MapParser(mapData);
                    this.mapData = new Map.Builder(mapParser.getGameFile(), mapParser.getCountries(), mapParser.getContinentDTOS(), mapParser.getBorderDTOS()).build();
                    this.mapData.addObserver(this);
                    updateImage(name);
                });
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                try {
                    stage.setscene(mapListController.getView());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //set the corresponding action for the game after starting
        startGame.setOnAction(event -> {
            try {
            	//sets strategy for each player in the game
                playersList.forEach(v -> mapData.addPlayer(v.getPlayerName(), v.getStrategy()));
                GamePlayController gamePlayController = new GamePlayController(mapData);
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                try {
                    stage.setscene(gamePlayController.getView());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        //set selection model based on the property used by the player
        playerViewList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedPlayer = String.valueOf(newValue);
        });

        //to remove player
        removePlayerButton.setOnAction(event -> {
            removePlayer(selectedPlayer);
        });

        //to add player
        addPlayerButton.setOnAction(event -> {
            addPlayer(playerName.getText(), GameStrategy.HUMAN);
        });

        //to set commands give by the player and execute accordingly
        processCommandOption.setOnAction(event -> {
            String command = commad.getText();
            List<Token> tokens = Lexer.lex(command);
            CommandSytanxTree commandSytanxTree= new CommandSytanxTree(mapData, tokens);
            commandSytanxTree.setPlayerCommandListener(this);
            commandSytanxTree.setLoadMapListener(this);
            commandSytanxTree.setGameTypeListener(this);
            commandSytanxTree.processCommand();
        });

    }
    
    /**
     * method to update the image
     * @param name String name of image
     */
    private void updateImage(String name) {
        inStream inStream = (getClass().getResourceAsStream("/maps/" + name + "_pic.jpg"));
        if(inStream == null) {
            inStream = (getClass().getResourceAsStream("/maps/" + name + "_pic.png"));
        }
        Image mapView = new Image(inStream);
        mapPreviewImage.setImage(mapView);
    }
    
    /**
     * method to setup players list
     */
    private void setupPlayerList() {
        List<String> list = playersList.stream().map(v -> v.getPlayerName()).collect(Collectors.toList());;
        ObservableList<String> items = FXCollections.observableArrayList(
                list);
        playerViewList.setItems(items);
    }

    /**
     * method to add player to the list
     */
    public void addPlayer(String playerName, String strategy) {
        if (mapData.isTournamentMode()) {
            addPlayerToList(playerName, strategy);
        } else {
            if (playersList.size() < mapData.getCountries().size()) {
                addPlayerToList(playerName, strategy);
            } else {
                commad.setText("You can't add more players that number of countries available");
            }
        }
    }

    /**
     * getter method of the view
     * 
     * @param String playerName and String strategy
     */
    private void addPlayerToList(String playerName, String strategy) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerName(playerName);
        playerDTO.setStrategy(strategy);
        playersList.add(playerDTO);
        setupPlayerList();
    }

    /**
     * method to remove player to the list
     */
    public void removePlayer(String playerName) {
        List<PlayerDTO> newList = playersList.stream().filter(s -> !(s.getPlayerName().equalsIgnoreCase(playerName))).collect(Collectors.toList());
        this.playersList = newList;
        setupPlayerList();
    }
    /**
     * binder method of the view to load and view 
     */
    private void bindView() {
        chooseMap = (Button) sceneView.lookup("#chooseMap");
        startGame = (Button) sceneView.lookup("#startGame");
        mapPreviewImage = (ImageView) sceneView.lookup("#mapPreviewImage");
        playerViewList = (viewList) sceneView.lookup("#playerViewList");
        removePlayerButton = (Button) sceneView.lookup("#removePlayerButton");
        playerName = (TextField) sceneView.lookup("#playerName");
        commad = (TextArea) sceneView.lookup("#commad");
        playerColor = (ColorPicker) sceneView.lookup("#playerColor");
        addPlayerButton = (Button) sceneView.lookup("#addPlayer");
        processCommandOption = (Button) sceneView.lookup("#processCommandOption");
    }
    
    /**
     * getter method of the view
     * @return sceneView
     * @throws IOException
     */
    @Override
    public Scene getView() throws IOException {
        return sceneView;
    }
    
    /**
     * obseravle class method
     * @param o Observable
     * @param arg Object
     */
    @Override
    public void update(Observable o, Object arg) {
    	
    }
    
    /**
     * method to load map
     * @param mapData mapdata
     */
    @Override
    public void loadMap(MapData mapData) {
        this.mapData = mapData;
        updateImage(mapData.getFileName());
    }

    /**
     * method to load map
     * @param list String listOfMapFiles
     */
    @Override
    public void setListOfMapFiles(List<String> listOfMapFiles) {
        MapData data = MapDataUtil.loadMapFromFile(listOfMapFiles.get(mapData.getGameCounter()));
        mapData.setGameCounter(listOfMapFiles);
        mapData.setCountries(data.getCountries());
        mapData.setContinents(data.getContinents());
        mapData.setFileName(data.getFileName());
        updateImage(listOfMapFiles.get(0));
    }

    /**
     * method to set the list of player strategies
     */
    @Override
    public void setListOfPlayerStrategies(List<String> listOfMapFiles) {
        mapData.setListOfPlayersStrategies(listOfMapFiles);
    }

    /**
     * method to set the number of games
     */
    @Override
    public void setNumberOfGames(int num) {
        mapData.setNumberOfGames(num);
    }

    /**
     * method to set the maximum number of games
     */
    @Override
    public void setMaxNumberOfGames(int num) {
        mapData.setMaxNumberOfTurns(num);
    }

    /**
     * method to set the tournament
     */
    @Override
    public void setIsTournament() {
        mapData.setTournamentMode(true);
    }
}








