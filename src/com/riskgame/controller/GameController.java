package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.Player;
import com.riskgame.model.ScoreConfiguration;
import com.riskgame.model.Territory;
import com.riskgame.model.TurnManager;
import com.riskgame.model.World;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.GameUtility;
import com.riskgame.utility.PlayerType;
import com.riskgame.view.BoardView;
import com.riskgame.view.NewGameView;

/**
 * This class provides the single instance of GameController throught the game.
 * 
 * @author pushpa
 *
 */
public class GameController implements ActionListener {

	private World world;
	private BoardView boardView;
	private Board board;
	private GameUtility gameUtility = new GameUtility();
	public static GamePhase gamePhase;
	private static GameController gameController;
	private TurnManager turnManager;
	private PlayerType playerType;
	private ArrayList<Player> playerList = new ArrayList<>();
	/**
	 * This constructor creates a GameController Object to set the turnPhase as
	 * <b>Start</b>
	 */
	private GameController() {
		this.gamePhase = GamePhase.PREGAME;
		board = Board.getInstance();
	}

	/**
	 * Returns the single instance of the GameController
	 * 
	 * @return
	 */
	public static GameController getInstance() {
		if (gameController == null) {
			gameController = new GameController();
		}
		return gameController;
	}

	/**
	 * This method sets the world data, playerList with the details of player in the game
	 * @param world
	 * @param playerList
	 */
	public void setGameParameters(World world, ArrayList<Player> playerList) {
		this.world = world;
		this.playerList = playerList;
	}

	/**
	 * Performs various actions based on the game phase
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		try {
			this.gamePhase = gameUtility.getNextPhase(this.gamePhase);
			if (GamePhase.START.equals(this.gamePhase)) {
				System.out.println("************START PHASE************");
				NewGameView newGameView = new NewGameView();
				newGameView.launchNewGameFrame();
			} else if (GamePhase.SETUP.equals(this.gamePhase)) {
				System.out.println("************SETUP PHASE**************");
				initiateBoardAndPlayGame();
			} else if (GamePhase.REINFORCE.equals(this.gamePhase)) {
				System.out.println("************REINFORCE PHASE**************");
				board.setGamePhase(GamePhase.REINFORCE);
				calculateReinforcementForPlayers();

			} else if (GamePhase.ATTACK.equals(this.gamePhase)) {
				board.setGamePhase(GamePhase.ATTACK);
				System.out.println("************ATTACK PHASE**************");
			} else if (GamePhase.FORTIFY.equals(this.gamePhase)) {
				board.setGamePhase(GamePhase.FORTIFY);
				System.out.println("************FORTIFY PHASE**************");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initiates the board with the specified player details and build card as per
	 * the specified territories
	 */
	public void initiateBoardAndPlayGame() {
		try {
			ArrayList<Card> cardDeck = gameUtility.buildCardDeck(world);
			System.out.println("Card Deck "+cardDeck.toString());

			// Assign armies for each player
			assignArmiesToPlayer(playerList);

			// initialize the Board Data
			board.initializeGame(world, playerList, cardDeck);

			// Distribute 42 armies equally to the players
			distributeTerritories(playerList, world);

			boardView = new BoardView(board);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method fetches the armies to be allocated to each player based on the
	 * number of players in the game. And the respective number of armies is
	 * allocated for the players in the game.
	 */
	public void assignArmiesToPlayer(ArrayList<Player> playerList) throws Exception {
		if (playerList != null && !playerList.isEmpty()) {
			int numberOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(playerList.size());

			for (Player player : playerList) {
				player.setArmiesToplayer(numberOfArmies);
			}
		}

	}

	/**
	 * This method distributes all territories evenly to the players in the game.
	 * This method also place 1 army of each player to the respective territory
	 * assigned to the player
	 */
	public void distributeTerritories(List<Player> playerList, World world) throws Exception {

		int playersCount = 0;
		if (world != null) {
			HashSet<Territory> territorySet = world.getTerritories();

			if (territorySet != null && !territorySet.isEmpty()) {

				Iterator<Territory> territoryIterator = territorySet.iterator();

				while (territoryIterator.hasNext()) {
					Territory territory = territoryIterator.next();

					if (playersCount == playerList.size()) {
						playersCount = 0;
					}
					playerList.get(playersCount).getCountriesOwned().add(territory);
					int territoriesOccupied = playerList.get(playersCount).getPlayerScore()
							.getNoOfOccupiedTerritories();
					playerList.get(playersCount).getPlayerScore().setNoOfOccupiedTerritories(territoriesOccupied + 1);
					territory.setOwner(playerList.get(playersCount));
					territory.setArmyCount(1);
					int oldArmiesCount = playerList.get(playersCount).getArmiesHeld();

					playerList.get(playersCount).setArmiesHeld(oldArmiesCount - 1);
					playersCount++;
				}
			}
		}
	}

	public GamePhase getGamePhase() {
		return this.gamePhase;
	}

	public void calculateReinforcementForPlayers() {
		try {
			int reinforcement = 0;
			Board board = Board.getInstance();
			if (board != null && board.getPlayerList() != null) {

				for (int i = 0; i < board.getPlayerList().size(); i++) {
					Player player = board.getPlayerList().get(i);
					reinforcement = gameUtility.calculateBonusFromOccupiedTerritories(player);
					reinforcement += gameUtility.calculateBonusFromContinent(player);
					player.setArmiesHeld(reinforcement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public BoardView getBoardView() {
		return this.boardView;
	}
}
