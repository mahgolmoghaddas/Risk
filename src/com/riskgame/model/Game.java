package com.riskgame.model;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.scene.Scene;
import com.riskgame.utility.CardType;
import com.riskgame.utility.RiskUtility;
import java.util.ArrayList;
import java.util.Observer;
import java.util.stream.Collectors;
package com.riskgame.model.game_play;
import com.riskgame.model.command.ShowMapCommand;
import com.riskgame.model.command_line.CommandSytanxTree;
import com.riskgame.model.command_line.Lexer;
import com.riskgame.model.command_line.Token;
import com.riskgame.model.enums.Phase;
import com.riskgame.model.interfaces.CommandSytanxProcessor;
import com.riskgame.model.interfaces.View;
import com.riskgame.model.model.Country;
import com.riskgame.model.model.MapData;
import com.riskgame.model.model.Player;
import com.riskgame.model.strategy.HumanStrategy;
import com.riskgame.model.utils.MapDataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Game extends Observable {
	
    /**
     * name of the gameplay
     */
    private final String NEW_GAME = "/view/game-play.fxml";
    /**
     * variable of scene view
     */
    private final scene scene;
    /**
     * variable of image view
     */
    private ImageView mapView;
    /**
     * variable for country list view
     */
    private viewList countryviewList;
    /**
     * variable for neighbour list view
     */
    private viewList neighbourviewList;
    /**
     * GUI variable of Anchor pane
     */
    private AnchorPane anchorPane;
    /**
     * variable of list view
     */
    private viewList viewList;
    /**
     * variable of Mpa data
     */
    private MapData mapData;
    /**
     * variable for processCommand button
     */
    private Button processCommandOption;
    /**
     * variable for text line
     */
    private Text turnTextLine;
    /**
     * variable for test area
     */
    private TextArea commad;
    /**
     * variable for test area
     */
    private TextArea gameLog;

    /**
     * variable for players list view
     */
    private viewList playerViewList;
    /**
     * variable for reinforcement army
     */
    private Text reinforceArmy;
    /**
     * variable for initial  army
     */
    private Text initialArmy;
    /**
     * total army
     */
    private Text totalArmy;
    /**
     * phase variable
     */
    private Text phaseText;
    /**
     * card view variable
     */
    private Text cardView;
    /**
     * variable to display currentSelectedCountry
     */
    private String currentSelectedCountry;

    private List<Node> node;

    /**
     * Constructor of the class
     *
     */
    public GameController(MapData mapData) throws IOException {
    	//to load FXML and get resources for the game
        Parent root = FXMLLoader.load(getClass().getResource(NEW_GAME));
        scene = new scene(root, 1300, 760);
        setupView();
        this.mapData = mapData;
        
        //to add observer to the mapData
        mapData.addObserver(this);
        mapData.setGameStarted(true);
        
        //to start the initial setup of the game
        setupGameView();
        
        //to set the players data to the game
        setPlayerList(mapData);
        
        //to set the countries data to the game 
        setupCountriesList();
        
        
        processCommandOption.setOnAction(event -> {
            String command = commad.getText();
            List<Token> tokens = Lexer.lex(command);
            CommandSytanxTree commandSytanxTree = new CommandSytanxTree(mapData, tokens);
            commandSytanxTree.setShowMapListener(this);
            commandSytanxTree.setCommandSytanxProcessor(this);
            commandSytanxTree.processCommand();
            System.out.println();
        });
        
        //To fetch countries and set up the view list accordingly
        countryviewList.getSelectionModel().selectedItemProperty().
        	addListener((observable, oldValue, newValue) -> {
            setupNeighbours((Country) newValue);
        });
        autoPopulate(mapData);
    }
    
    /**
     * setup method of the view to initialize all necessary things for the game to start
     * and set up scene elements of the game
     */
    private void setupView() {
    	//initialize map view for the game
        mapView = (ImageView) scene.lookup("#mapView");
        anchorPane = (AnchorPane) scene.lookup("#anchorPane");
        //initialize the class to process the command
        processCommandOption = (Button) scene.lookup("#processCommandOption");
        turnTextLine = (Text) scene.lookup("#turnTextLine");
        commad = (TextArea) scene.lookup("#comandLine");
        //set up the lookup list for playerview
        playerViewList = (viewList) scene.lookup("#playerViewList");
        //initialize reinforce army
        reinforceArmy = (Text) scene.lookup("#reinforceArmy");
        initialArmy = (Text) scene.lookup("#initialArmy");
        totalArmy = (Text) scene.lookup("#totalArmy");
        phaseText = (Text) scene.lookup("#phaseText");
        cardView = (Text) scene.lookup("#cardView");
        gameLog = (TextArea) scene.lookup("#gameLog");
        //setup country view list
        countryviewList = (viewList) scene.lookup("#countryviewList");
        neighbourviewList = (viewList) scene.lookup("#neighbourviewList");
    }

    /**
     * method to auto populate the strategy used by the player
     * 
     * @param MapData mapData
     */
    private void autoPopulate(MapData mapData) {
        mapData.toList().forEach(v -> {
            if (!(v.getPlayerStrategy() instanceof HumanStrategy)) {
                mapData.populateCountries();
                mapData.placeAll();
            }
        });
    }

    /**
     * method to update country location
     */
    private void updateCountriesLocation() {
    	//to make sure nodes doesnt have previous data
        if (node != null) {
            anchorPane.getChildren().removeAll(node);
        }
        node = mapData.getCountries().values().stream().map(country -> {
            Circle circle = new Circle();
            StackPane stack = new StackPane();
            //If isRisk is boolean we set the coordinates accordingly
            if (!mapData.isRisk()) {
                stack.setLayoutX(Double.parseDouble(country.getXCoordinate()) - 40);
                stack.setLayoutY(Double.parseDouble(country.getYCoordinate()) - 30);
            } else {
                stack.setLayoutX(Double.parseDouble(country.getXCoordinate()) + 10);
                stack.setLayoutY(Double.parseDouble(country.getYCoordinate()) + 10);
            }
            // setting the player related data
            if (country.getPlayer() != null) {
                Player.PlayerColor playerColor = country.getPlayer().getPlayerColor();
                circle.setFill(javafx.scene.paint.Color.rgb(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue()));
                circle.setRadius(10.0f);
                Text text = new Text(String.valueOf(country.getNoOfArmies()));
                text.setBoundsType(TextBoundsType.VISUAL);
                stack.getChildren().addAll(circle, text);
            }
            return stack;
        }).collect(Collectors.toList());


        anchorPane.getChildren().addAll(node);
    }

    
    /**
     * method for setup game view
     */
    private void setupGameView() {
        updateImage(mapData.getFileName());
    }

    /**
     * method to setup countries list
     */
    private void setupCountriesList() {
    	//fetch the countries and add them to the game data list
        List<Country> countries = mapData.getCountries().entrySet().
        		stream().map(stringCountryEntry -> stringCountryEntry.
        		getValue()).collect(Collectors.toList());
        ObservableList<Country> items = FXCollections.observableArrayList(
                countries);
        countryviewList.setItems(items);
        setCountryTableView(countryviewList);
    }

    /**
     * method to setup neighbours to country
     *
     * @param country country
     */
    private void setupNeighbours(Country country) {
        ObservableList<Country> items = FXCollections.observableArrayList(
                country.getAdjacentCountries());
        neighbourviewList.setItems(items);
        setCountryTableView(neighbourviewList);
    }

    /**
     * method to setup view for country tables
     *
     * @param viewlist viewList
     */
    private void setCountryTableView(viewList viewList) {
        viewList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Circle circle = new Circle();
                    if (item.getPlayer() != null) {
                        Player.PlayerColor playerColor = item.getPlayer().getPlayerColor();
                        circle.setFill(javafx.scene.paint.Color.rgb(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue()));
                        circle.setRadius(10.0f);
                        setText(item.getName() + "; Armies: " + item.getNoOfArmies());
                        setGraphic(circle);
                    }
                }
            }
        });
    }

    /**
     * method to update image
     *
     * @param name input string
     */
    private void updateImage(String name) {
        InputStream inStream = (getClass().getResourceAsStream("/maps/" + name + "_pic.jpg"));
        //Loading resources required for the game launch
        if (inStream == null) {
            inStream = (getClass().getResourceAsStream("/maps/" + name + "_pic.png"));
        }
        //Loading image for game background
        Image mapView = new Image(inStream);
        this.mapView.setImage(mapView);
    }

    /**
     * method to set players list
     */
    private void setPlayerList(MapData mapData) {
        ObservableList<Player> items = FXCollections.observableArrayList(
                mapData.toList());
        playerViewList.setItems(items);
        //setting up the cells and player data for the game
        playerViewList.setCellFactory(param -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Circle circle = new Circle();
                    Player.PlayerColor playerColor = item.getPlayerColor();
                    circle.setFill(javafx.scene.paint.Color.rgb(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue()));
                    circle.setRadius(10.0f);
                    double mapSize = mapData.getCountries().size();
                    double countries = item.getCountries().size();
                    double result = countries / mapSize;
                    setText(item.getPlayerName() + "\t\t\t" + String.valueOf(result * 100) + "%");
                    setGraphic(circle);
                }
            }
        });

    }

    /**
     * getter method of the view
     *
     * @return scene
     * @throws IOException
     */
    @Override
    public scene getView() throws IOException {
        return ;
    }

    /**
     * method to update the view
     *
     * @param o   oberseravble
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
        MapData mapTest = (MapData) arg;
        this.mapData = mapTest;
        System.out.println(mapData);
        Player player = mapTest.getPlayers().last();
        turnTextLine.setText("" + player.getPlayerName() + " Turn!!!");
        initialArmy.setText("Initial Army: " + player.getPlaceArmiesNo());
        reinforceArmy.setText("Reinforcement Army: " + player.getNumOfArmies());
        Long totalArmyCount = player.getCountries().stream().mapToLong(Country::getNoOfArmies).sum();
        totalArmy.setText("Total Army: " + totalArmyCount);
        gameLog.setText(mapTest.getAttackLog());

        setupGameView();
        setPhase(player);
        setPlayerList(mapTest);
        setCardView(player);
        setupCountriesList();

        //Fetching the strategy used by player and implementing the same for this turn
        if (!(player.getPlayerStrategy() instanceof HumanStrategy)) {
            if(player.getPhase() == Phase.ATTACK) {
                player.getPlayerStrategy().attack(mapData);
            } else if(player.getPhase() == Phase.ATTACK_MOVE) {
                player.getPlayerStrategy().attackMove(mapData);
            } else if (player.getPhase() == Phase.EXCHANGE_CARD) {
                player.getPlayerStrategy().exchangeCard(mapData);
            } else if (player.getPhase() == Phase.REINFORCEMENT) {
                player.getPlayerStrategy().reinforce(mapData);
            } else if (player.getPhase() == Phase.FORTIFICATION) {
                player.getPlayerStrategy().fortify(mapData);
            }
        }
        
        //handling the edge case
        if(mapData.isNewPhase()) {
            mapData.setNewPhase(false);
            autoPopulate(mapData);
        }
        updateCountriesLocation();
    }

    /**
     * Setter method for card view
     *
     * @param player game player
     */
    private void setCardView(Player player) {
        if (player.getPhase() == Phase.REINFORCEMENT) {
            cardView.setVisible(true);
            StringBuilder cardViewBuilder = new StringBuilder();
            List<String> cards = player.getCards().getPlayerCards();
            for (int i = 0; i < cards.size(); i++) {
                cardViewBuilder.append("Index: " + i + "\tCard Type: " + cards.get(i) + "\n");
            }
            cardView.setText(cardViewBuilder.toString());
        } else {
            cardView.setVisible(false);
        }
    }

    /**
     * Setter method for phase view
     *
     * @param player game player
     */
    private void setPhase(Player player) {
        if (player.getPlaceArmiesNo() > 0) {
            phaseText.setText("Startup Phase");
        }

        if (player.getPhase() == Phase.REINFORCEMENT) {
            phaseText.setText("Reinforcement Phase");
        }

        if (player.getPhase() == Phase.ATTACK) {
            phaseText.setText("Attack Phase");
        }

        if (player.getPhase() == Phase.FORTIFICATION) {
            phaseText.setText("Fortification Phase");
        }
    }

    /**
     * method implementing show map in GUI
     *
     * @param mapData
     */
    @Override
    public void showMap(String mapData) {
        commad.setText(mapData);
    }

    /**
     * method if any error
     *
     * @param message error message
     */
    @Override
    public void onError(String message) {
        commad.setText(message);
    }
}

}
