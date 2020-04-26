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
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.ScoreConfiguration;
import com.riskgame.model.Territory;
import com.riskgame.model.TurnManager;
import com.riskgame.model.World;
import com.riskgame.strategy.PlayerStrategy;
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
	private Board board = new Board();
	private GameUtility gameUtility = new GameUtility();
	public static GamePhase gamePhase;
	private static GameController gameController;
	private TurnManager turnManager;
	private PlayerType playerType;
	private ArrayList<Player> playerList = new ArrayList<>();
	private GameLogs gameLogs = GameLogs.getInstance();
	private boolean isSavedGame = false;

	/**
	 * This constructor creates a GameController Object to set the turnPhase as
	 * <b>Start</b>
	 */
	private GameController() {
		this.gamePhase = GamePhase.PREGAME;
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
	 * This method sets the world data, playerList with the details of player in the
	 * game
	 * 
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
			gamePhase = gameUtility.getNextPhase(gamePhase);
			System.out.println("NEXT PHASE " + gamePhase);
			if (GamePhase.START.equals(gamePhase)) {
				System.out.println("************START PHASE************");
				NewGameView newGameView = new NewGameView();
				newGameView.launchNewGameFrame();
			} else if (GamePhase.SETUP.equals(gamePhase)) {

				System.out.println("************SETUP PHASE**************");
				initiateBoardAndPlayGame();

			} else if (GamePhase.REINFORCE.equals(gamePhase)) {
				System.out.println("************REINFORCE PHASE**************");
				board.setGamePhase(GamePhase.REINFORCE);
				Player activePlayer = board.getActivePlayer();
				if (PlayerType.HUMAN.equals(activePlayer.getPlayerType())) {
					activePlayer.setOccupiedTerritories(activePlayer.getCountriesOwned().size());
					System.out.println("Setting occupiedTerritories during reinforcement::"
							+ activePlayer.getCountriesOwned().size());
					gameUtility.calculateReinforcementForPlayers(activePlayer, board);
				} else {
					autoRunReinforceToFortify(activePlayer);
				}

			} else if (GamePhase.ATTACK.equals(gamePhase)) {

				System.out.println("************ATTACK PHASE**************");
				board.setGamePhase(GamePhase.ATTACK);

			} else if (GamePhase.FORTIFY.equals(gamePhase)) {

				System.out.println("************FORTIFY PHASE**************");
				board.setGamePhase(GamePhase.FORTIFY);

			} else if (GamePhase.PICKCARD.equals(gamePhase)) {
				System.out.println("************PICKCARD PHASE**************");
				board.setGamePhase(GamePhase.PICKCARD);
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
			List<Card> cardDeck = gameUtility.buildCardDeck(world);
			System.out.println("Card Deck " + cardDeck.toString());

			// Assign armies for each player
			assignArmiesToPlayer(playerList);

			// initialize the Board Data
			board.initializeGame(world, playerList, cardDeck);
			board.setGamePhase(GamePhase.SETUP);
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
		System.out.println("Total players in the game:::"+playerList.size());
		if (playerList != null && !playerList.isEmpty()) {
			int numberOfArmies = gameUtility.getNumberOfArmiesForEachPlayer(playerList.size());
			gameLogs.log("[Pre setup phase] Assigning " + numberOfArmies + " armies to each player ");
			for (Player player : playerList) {
				gameLogs.log(player.getName() + " got " + numberOfArmies + " armies");
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
		gameLogs.log("[Pre setup phase] Distributing each territories equally among players ");
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
					gameLogs.log("[Pre setup phase] Territory " + territory.getCountryName() + " allocated to "
							+ playerList.get(playersCount).getPlayerName());
					playersCount++;
				}
			}
		}
	}

	public GamePhase getGamePhase() {
		return this.gamePhase;
	}

	public BoardView getBoardView() {
		return this.boardView;
	}

	public void autoRunReinforceToFortify(Player activePlayer) {
		try {
			PlayerStrategy playerStrategy = activePlayer.getPlayerStrategy();

			playerStrategy.runReinforcePhase(activePlayer, board);
			playerStrategy.runAttackPhase(activePlayer, board);
			playerStrategy.runFortifyPhase(activePlayer, board);
			this.gamePhase = GamePhase.SETUP;
			board.getNextPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resumeGame(Board board) {
		this.board = board;
		isSavedGame = true;
		this.gamePhase = board.getGamePhase();
//		board.setBoard(board);
		if (this.board.getPlayerList() != null) {
			List<Player> playerList = this.board.getPlayerList();
			if (playerList != null && !playerList.isEmpty()) {
				for (int i = 0; i < playerList.size(); i++) {
					Player player = playerList.get(i);
					player.addObserver(this.board);
				}
			}
		}
		this.boardView = new BoardView(this.board);
	}

	public boolean isSavedGame() {
		return isSavedGame;
	}

	public Board getBoard() {
		return this.board;
	}

	public void handleCardPickUpCase(Board board) {
		try {
			System.out.println("Active player picking card");
			if (board != null && board.getCardDeck() != null && !board.getCardDeck().isEmpty()) {
				Card card = board.getCardDeck().get(0);
				board.getActivePlayer().getCardsHeld().add(card);
				board.getCardDeck().remove(0);
				System.out.println("Card Deck size after picking card " + board.getCardDeck().size());
			} else {
				System.out.println("Card Deck is empty");
			}
			board.getNextPlayer();
			GameController.getInstance().actionPerformed(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
