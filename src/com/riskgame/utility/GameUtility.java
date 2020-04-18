package com.riskgame.utility;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.Player;
import com.riskgame.model.ScoreConfiguration;
import com.riskgame.model.Territory;
import com.riskgame.model.World;

/**
 * 
 * This class provides the reusable method used in the Risk Game.
 * 
 * @author pushpa
 *
 */
public class GameUtility {

	ScoreConfiguration scoreConfig = new ScoreConfiguration();
	Board board = Board.getInstance();

	/**
	 * This method returns Color as per the playerID
	 * 
	 * @param playerCount
	 * @return
	 */
	public Color getPlayerColorById(int playerId) {
		Color color = null;
		switch (playerId) {
		case 1:
			color = Color.decode("#03befc");
			break;
		case 2:
			color = Color.decode("#54b354");
			break;
		case 3:
			color = Color.decode("#a45eeb");
			break;
		case 4:
			color = Color.decode("#a81b8e");
			break;
		case 5:
			color = Color.decode("#fc9003");
			break;
		case 6:
			color = Color.RED;
			break;
		default:
			break;
		}
		return color;
	}

	/**
	 * This method builds the card deck by assigning a random card type for each
	 * territory.
	 * 
	 * @param world
	 * @return ArrayList of card for each territory
	 */
	public ArrayList<Card> buildCardDeck(World world) {
		System.out.println("***********8Building the Territory Card Deck**************");
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		try {
			if (world != null) {
				HashSet<Territory> territorySet = world.getTerritories();
				if (territorySet != null && !territorySet.isEmpty()) {

					Iterator<Territory> territory = territorySet.iterator();

					while (territory.hasNext()) {
						String territoryName = territory.next().getCountryName();

						CardType cardType = generateRandomCardType();
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
	 * This method generates the random Card type.
	 * 
	 * @return card Type i.e. INFANTRY OR CAVALRY OR ARTILLERY
	 */
	public CardType generateRandomCardType() {

		int min = 1;
		int max = 3;
		int range = max - min + 1;
		int randomNumber = (int) (Math.random() * range) + min;
		if (randomNumber == 1) {
			return CardType.INFANTRY;
		} else if (randomNumber == 2) {
			return CardType.CAVALRY;
		} else {
			return CardType.ARTILLERY;
		}
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
	 * This method returns the next phase of the game based on the current phase of
	 * the game
	 * 
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

	public int calculateBonusFromContinent(Player player) {
		int reinforcement = 0;
		World world = board.getWorld();
		if (world != null) {

			HashSet<Continent> continents = world.getContinents();

			Iterator<Continent> continentIterator = continents.iterator();

			while (continentIterator.hasNext()) {
				Continent continent = continentIterator.next();
				HashSet<Territory> territorySet = continent.getTerritoryList();

				if (player.getCountriesOwned().containsAll(territorySet)) {
					reinforcement += continent.getBonusPoint();
					player.getContinentsOwned().add(continent);
				}
			}
		}
		System.out.println("Reinforcement from Continent " + reinforcement);
		return reinforcement;
	}

	public int calculateBonusFromOccupiedTerritories(Player player) throws Exception {
		return scoreConfig.getOccupiedTerritoryBonus(player.getCountriesOwned().size());

	}

	public int calculateBonusFromCards() {
		int reinforcement = 0;
		return reinforcement;
	}

	public boolean playersHaveArmies() {
		boolean playersHaveArmies = false;
		if (board != null && board.getPlayerList() != null) {
			for (Player player : board.getPlayerList()) {
				if (player.getArmiesHeld() > 0) {
					playersHaveArmies = true;
					break;
				}
			}
		}
		return playersHaveArmies;
	}

	public HashSet<Territory> sortTerritoryByArmies(HashSet<Territory> territorySet) {
		List<Territory> playersTerritoryList = new ArrayList<Territory>(territorySet); // set -> list

		Collections.sort(playersTerritoryList, new TerritoriesArmiesComparator());
		HashSet sortedTerritorySet = new LinkedHashSet<Territory>(playersTerritoryList);
		return sortedTerritorySet;
	}

}
