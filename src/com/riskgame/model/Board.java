package com.riskgame.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.*;
import com.riskgame.utility.*;

/**
 * This class maintains the data throught the game such as the world map
 * details, player details
 * 
 * @author pushpa
 *
 */
public class Board extends Observable implements Observer, Externalizable {
	World world;
	List<Player> playerList;
	List<Card> cardDeck;
	Player nextPlayer;
	Player activePlayer;
	GamePhase gamePhase;
	TurnManager turnManager;

	
	public  Board() {
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
	}

	/**
	 * getter method for returning the world object
	 * 
	 * @return
	 */
	public World getWorld() {
		return world;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
		boardDataChanged();
	}

	public Player getActivePlayer() {
		if (this.activePlayer == null) {
			this.activePlayer = getNextPlayer();
		}
		return this.activePlayer;
	}

	public Player getNextPlayer() {
		try {
			if (turnManager == null) {
				turnManager = new TurnManager(playerList);
			}
			this.nextPlayer = turnManager.getNextPlayer();
			if (this.activePlayer != null) {
				this.activePlayer = this.nextPlayer;
				boardDataChanged();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.nextPlayer;
	}

	public void initializeGame(World world, ArrayList<Player> playerList, List<Card> cardDeck) {
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
		setChanged();
		notifyObservers();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(world);
		out.writeObject(playerList);
		out.writeObject(cardDeck);
		out.writeObject(activePlayer);
		out.writeObject(gamePhase);
		out.writeObject(turnManager);

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		world = (World) in.readObject();
		playerList = (List<Player>) in.readObject();
		cardDeck = (List<Card>) in.readObject();
		activePlayer = (Player) in.readObject();
		gamePhase = (GamePhase) in.readObject();
		turnManager = (TurnManager) in.readObject();
	}
}
