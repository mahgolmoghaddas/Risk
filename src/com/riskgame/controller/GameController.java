package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.CardType;
import com.riskgame.utility.GameUtility;
import com.riskgame.view.BoardView;
import com.riskgame.view.NewGameView;

/**
 * This class provides the
 * 
 * @author gauta
 *
 */
public class GameController implements ActionListener {

	private boolean isGamePlay;
	private World world;
	private int numberOfPlayers;
	private BoardView boardView;
	private Board game;
	private NewGameView newGameView;
	private GameUtility gameUtility = new GameUtility();

	public GameController(boolean isGamePlay) {
		this.isGamePlay = isGamePlay;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		try {
			if (isGamePlay) {
				initiateBoardAndPlayGame();
			} else {
				newGameView = new NewGameView(this);
				newGameView.launchNewGameFrame();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setGameParameters(World world, int numberOfPlayers, boolean isGamePlay) {
		this.isGamePlay = isGamePlay;
		this.world = world;
		this.numberOfPlayers = numberOfPlayers;
	}

	public void initiateBoardAndPlayGame() {
		try {
			game = Board.getInstance();

			ArrayList<Player> playerList = gameUtility.createPlayers(numberOfPlayers);
			ArrayList<Card> cardDeck = gameUtility.buildCardDeck(world);

			// Assign armies for each player
			assignArmiesToPlayer(playerList);

			// assign 42 territories for each player evenly and place 1 army for each player
			distributeTerritories(playerList, world);
			
			game.initializeGame(world, playerList, cardDeck);

			boardView = new BoardView(game);
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
	 * This method distributes all territories evenly to the players in the game. This method also place 1 army of each player
	 * to the respective territory assigned to the player
	 */
	public void distributeTerritories(ArrayList<Player> playerList, World world) throws Exception {

		int playersCount = 0;
		if (world != null) {
			HashSet<Territory> territorySet = world.getTerritories();

			if (territorySet != null && !territorySet.isEmpty()) {

				Iterator<Territory> territoryIterator = territorySet.iterator();

				while (territoryIterator.hasNext()) {
					Territory territory = territoryIterator.next();
					
					if(playersCount == playerList.size() ) {
						playersCount = 0;
					}
					playerList.get(playersCount).getCountriesOwned().add(territory);
					territory.setOwner(playerList.get(playersCount));
					territory.setArmyCount(1);
					int oldArmiesCount = playerList.get(playersCount).getArmiesHeld();
					
					playerList.get(playersCount).setArmiesHeld(oldArmiesCount-1);
					playersCount++;
				}
			}
		}
	}
	
}
