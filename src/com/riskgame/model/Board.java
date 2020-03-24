package com.riskgame.model;

import java.util.*;
import com.riskgame.utility.*;

/**
 * This class maintains the data throught the game such as the world map
 * details, player details
 * 
 * @author pushpa
 *
 */
public class Board extends Observable implements Observer {
	private World world;
	private List<Player> playerList;
	private List<Card> cardDeck;
	private static Board board;
	private Player nextPlayer;
	
	private TurnManager turnManager;
	

	/**
	 * Single instance for the board is maintained throughout the game phases.
	 */
	private Board() {
	}

	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Return the available Cards in the board. If cards are distributed among
	 * players, the updated cards will be displayed
	 * 
	 * @return
	 */
	public List<Card> getCardDeck() {
		return cardDeck;
	}

	public void setCardDeck(List<Card> cardDeck) {
		this.cardDeck = cardDeck;
		boardDataChanged();
	}

	/**
	 * getter method for returning the world object
	 * 
	 * @return
	 */
	public World getWorld() {
		return world;
	}
	
	

	public Player getActivePlayer() {
		if(this.nextPlayer==null) {
			this.nextPlayer = getNextPlayer();
		}
		return this.nextPlayer;
	}

	public Player getNextPlayer() {
		try {
			if (turnManager == null) {
				turnManager = new TurnManager(board.getPlayerList());
			}
			this.nextPlayer = turnManager.getNextPlayer();
			boardDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.nextPlayer;
	}



	public void initializeGame(World world, ArrayList<Player> playerList, ArrayList<Card> cardDeck) {
		this.world = world;
		this.playerList = playerList;
		this.cardDeck = cardDeck;

		if (this.playerList != null && !this.playerList.isEmpty()) {
			for (int i = 0; i < playerList.size(); i++) {
				Player player = playerList.get(i);
				player.addObserver(this);
			}
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {

		System.out.println("PLayer data has been changed ");
		if (o instanceof Player) {

			Player updatedPlayer = (Player) o;
			if (this.playerList != null && !this.playerList.isEmpty()) {

				for (int i = 0; i < playerList.size(); i++) {

					Player oldPlayer = playerList.get(i);

					if (oldPlayer.getId() == updatedPlayer.getId()) {
						oldPlayer = updatedPlayer;
						boardDataChanged();// notify its observers
					}
				}
			}
		}

	}

	
	
	/**
	 * This method notify the Observers of Board whenever any data changes
	 */
	public void boardDataChanged() {
		System.out.println("Board Data has been changed");
		setChanged();
		notifyObservers();
	}

}
