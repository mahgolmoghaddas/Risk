package com.riskgame.utility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Card;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;

/**
 * 
 * This class provides the reusable method used in the Risk Game.
 * @author pushpa
 *
 */
public class GameUtility {

	RiskUtility utility = new RiskUtility();

	/**
	 * This method creates the player object specifying the name and color of the
	 * player as per the number of the player selected for the game.
	 * 
	 * @param playerCount
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Player> createPlayers(int playerCount) throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (int i = 1; i <= playerCount; i++) {

			Player player = new Player(i, "Player" + i);
			switch (i) {
			case 1:
				player.setColor(Color.decode("#03befc"));
				break;
			case 2:
				player.setColor(Color.decode("#54b354"));
				break;
			case 3:
				player.setColor(Color.decode("#a45eeb"));
				break;
			case 4:
				player.setColor(Color.decode("#a81b8e"));
				break;
			case 5:
				player.setColor(Color.decode("#fc9003"));
				break;
			case 6:
				player.setColor(Color.RED);
				break;
			default:
				break;
			}
			
			playerList.add(player);
		}
		return playerList;
	}

	/**
	 * This method builds the card deck by assigning a random card type for each
	 * territory.
	 * 
	 * @param world
	 * @return ArrayList of card for each territory
	 */
	public ArrayList<Card> buildCardDeck(World world) {
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		try {
			if (world != null) {
				HashSet<Territory> territorySet = world.getTerritories();
				if (territorySet != null && !territorySet.isEmpty()) {

					Iterator<Territory> territory = territorySet.iterator();

					while (territory.hasNext()) {
						String territoryName = territory.next().getCountryName();

						CardType cardType = utility.generateRandomCardType();
						Card card = new Card(territoryName, cardType);

						cardDeck.add(card);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cardDeck;
	}

	/**
	 * This method returns the armies to be assigned to each player, as per the
	 * number of players in the game
	 */
	public int getNumberOfArmiesForEachPlayer(int numberOfPlayers) {
		int numberOfArmies = 0;
		switch (numberOfPlayers) {

		case 2:
			numberOfArmies = 40;
			break;
		case 3:
			numberOfArmies = 35;
			break;
		case 4:
			numberOfArmies = 30;
			break;
		case 5:
			numberOfArmies = 25;
			break;
		case 6:
			numberOfArmies = 20;
			break;
		default:
			break;
		}
		return numberOfArmies;
	}
	/**
	 * This method returns the next phase of the game based on the current phase of the game
	 * @return GamePhase
	 */
	public GamePhase getNextPhase(GamePhase currentPhase) {
		switch (currentPhase) {
		case START:
			return GamePhase.SETUP;
		case SETUP:
			return GamePhase.REINFORCE;
		case REINFORCE:
			return GamePhase.ATTACK;
		case ATTACK:
			return GamePhase.FORTIFY;
		case FORTIFY:
			return GamePhase.PICKCARD;
		case PICKCARD:
			return GamePhase.REINFORCE;
		default:
			return GamePhase.START;
		}
	}
}
