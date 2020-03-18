package com.riskgame.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;

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
				player.setColor(PlayersColor.BLUE);
				break;
			case 2:
				player.setColor(PlayersColor.GREEN);
				break;
			case 3:
				player.setColor(PlayersColor.ORANGE);
				break;
			case 4:
				player.setColor(PlayersColor.PINK);
				break;
			case 5:
				player.setColor(PlayersColor.YELLOW);
				break;
			case 6:
				player.setColor(PlayersColor.RED);
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
}
