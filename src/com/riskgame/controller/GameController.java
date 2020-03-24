package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.TurnManager;
import com.riskgame.model.World;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.GameUtility;
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
	private int numberOfPlayers;
	private BoardView boardView;
	private Board board;
	private GameUtility gameUtility = new GameUtility();
	private GamePhase turnPhase;
	private GamePhase gamePhase;
	private static GameController gameController;

	private TurnManager turnManager;

	/**
	 * This constructor creates a GameController Object to set the turnPhase as <b>Start</b>
	 */
	private GameController() {
		this.turnPhase = GamePhase.START; 

		this.gamePhase = GamePhase.START;
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
	 * This method sets the world data, number of players and the phase of the turn
	 * 
	 * @param world
	 * @param numberOfPlayers
	 * @param turnPhase
	 */
	public void setGameParameters(World world, int numberOfPlayers) {
		this.world = world;
		this.numberOfPlayers = numberOfPlayers;
		this.turnPhase = turnPhase;

	}

	/**
	 * Performs various actions based on the game phase
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		try {
				if(GamePhase.START.equals(this.turnPhase)) {
					System.out.println("************START PHASE************");
					NewGameView newGameView = new NewGameView();
					newGameView.launchNewGameFrame();
				}else if (GamePhase.SETUP.equals(this.turnPhase)) {
					System.out.println("************SETUP PHASE**************");
					initiateBoardAndPlayGame();
				}else if(GamePhase.REINFORCE.equals(this.turnPhase)) {
					System.out.println("************REINFORCE PHASE**************");
				}else if(GamePhase.ATTACK.equals(this.turnPhase)) {
					System.out.println("************ATTACK PHASE**************");
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
			ArrayList<Player> playerList = gameUtility.createPlayers(numberOfPlayers);
			ArrayList<Card> cardDeck = gameUtility.buildCardDeck(world);

			// Assign armies for each player
			assignArmiesToPlayer(playerList);

			// assign 42 territories for each player evenly and place 1 army for each player
			distributeTerritories(playerList, world);

			board.initializeGame(world, playerList, cardDeck);

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
	public void distributeTerritories(ArrayList<Player> playerList, World world) throws Exception {

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
					territory.setOwner(playerList.get(playersCount));
					territory.setArmyCount(1);
					int oldArmiesCount = playerList.get(playersCount).getArmiesHeld();

					playerList.get(playersCount).setArmiesHeld(oldArmiesCount - 1);
					playersCount++;
				}
			}
		}
	}

}
