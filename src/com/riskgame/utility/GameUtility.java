package com.riskgame.utility;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.Continent;
import com.riskgame.model.GameLogs;
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
	GameLogs gameLogs = GameLogs.getInstance();

	/**
	 * This method returns Color as per the playerID
	 * 
	 * @param playerId
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
	public List<Card> buildCardDeck(World world) {
		System.out.println("************Building the Territory Card Deck**************");
		gameLogs.log("***Building the Territory Card Deck****");
		List<Card> cardDeck = new LinkedList<Card>();
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
			System.out.println("***Shuffled the card Deck for the Game and set in the board*****");
			Collections.shuffle(cardDeck);
			gameLogs.log("*** Territory Card Deck with total " + cardDeck.size() + " territory cards Built****");
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

	public int calculateBonusFromContinent(Player player, Board board) {
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

	public void calculateBonusFromCards(Map<CardType, List<String>> tradedCards, Board board) {

		int reinforcement = 0;
		try {
			System.out.println("Calculating and assining Bonus From Traded cards to player");
			if (tradedCards != null && !tradedCards.isEmpty()) {

				if (tradedCards.size() == 1) {
					CardType cardType = tradedCards.keySet().iterator().next();
					reinforcement = ScoreConfiguration.getCardBonus(cardType.getCardTypeValue());
				} else if (tradedCards.size() == 3) {
					reinforcement = ScoreConfiguration.getCardBonus("All");
				} else {
					System.out.println("Neither set of 3 cards nor all 3 cards are same");
				}

				System.out.println("Reinforcement Bonus from traded cards " + reinforcement);
				World world = board.getWorld();
				Player activePlayer = board.getActivePlayer();
				int armiesHeld = activePlayer.getArmiesHeld();
				activePlayer.setArmiesHeld(armiesHeld + reinforcement);

				bonusForTradedCardsTerritory(tradedCards, world, activePlayer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bonusForTradedCardsTerritory(Map<CardType, List<String>> tradedCards, World world,
			Player activePlayer) {
		Iterator<Entry<CardType, List<String>>> cardIterator = tradedCards.entrySet().iterator();
		boolean allocatedTerrBonus = false;
		while (cardIterator.hasNext()) {

			Entry<CardType, List<String>> entry = cardIterator.next();

			List<String> territoryList = entry.getValue();
			for (int i = 0; i <= territoryList.size(); i++) {

				Territory territory = world.getTerritoryByName(territoryList.get(i));
				if (activePlayer.getCountriesOwned().contains(territory)) {
					System.out.println(territory.getCountryName() + " is owned by " + activePlayer.getPlayerName()
							+ ". Autoplacing 2 armies in this territory");
					int armyCnt = territory.getArmyCount();
					territory.setArmyCount(armyCnt + 2);
					allocatedTerrBonus = true;
					break;
				}
			}
			if (allocatedTerrBonus) {
				break;
			}
		}
	}

	public boolean playersHaveArmies(Board board) {
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

	public void calculateReinforcementForPlayers(Player player, Board board) {
		try {
			int reinforcement = 0;
			reinforcement = calculateBonusFromOccupiedTerritories(player);
			gameLogs.log("Reinforcement received by Territories" + reinforcement);
			reinforcement += calculateBonusFromContinent(player, board);
			player.setArmiesHeld(reinforcement);
			System.out.println("Reinforcement received by player " + player.getName() + "::" + reinforcement);
			gameLogs.log("Reinforcement received by player " + player.getName() + "::" + reinforcement);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public HashSet<Territory> sortTerritoryByArmiesASC(HashSet<Territory> territorySet) {
		List<Territory> playersTerritoryList = new ArrayList<Territory>(territorySet); // set -> list

		Collections.sort(playersTerritoryList, new TerritoriesArmiesComparator());
		HashSet sortedTerritorySet = new LinkedHashSet<Territory>(playersTerritoryList);
		return sortedTerritorySet;
	}

	public HashSet<Territory> sortTerritoryByArmiesDESC(HashSet<Territory> territorySet) {
		List<Territory> playersTerritoryList = new ArrayList<Territory>(territorySet); // set -> list

		Collections.sort(playersTerritoryList, new TerritoriesArmiesComparator());
		Collections.reverse(playersTerritoryList);
		HashSet sortedTerritorySet = new LinkedHashSet<Territory>(playersTerritoryList);
		return sortedTerritorySet;
	}

	public HashSet<Territory> getDefenderTerritories(Territory attackerTerritory, Board board) {
		HashSet<Territory> defenderTerritories = new HashSet<>();
		try {
			HashSet<String> neighboursTerr = attackerTerritory.getNeighborsTerritory();

			Iterator<String> neighTerrIterator = neighboursTerr.iterator();

			while (neighTerrIterator.hasNext()) {
				World world = board.getWorld();
				Territory territory = world.getTerritoryByName(neighTerrIterator.next());
				if (territory != null && territory.getOwner().getId() != attackerTerritory.getOwner().getId()) {
					defenderTerritories.add(territory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defenderTerritories;
	}

	public HashSet<Territory> getDestinationTerritories(Territory sourceTerritory, Board board) {
		HashSet<Territory> destinationTerritories = new HashSet<>();
		try {
			HashSet<String> neighboursTerr = sourceTerritory.getNeighborsTerritory();

			Iterator<String> neighTerrIterator = neighboursTerr.iterator();

			while (neighTerrIterator.hasNext()) {
				World world = board.getWorld();
				Territory territory = world.getTerritoryByName(neighTerrIterator.next());
				if (territory != null && territory.getOwner().getId() == sourceTerritory.getOwner().getId()) {
					destinationTerritories.add(territory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinationTerritories;
	}

	/**
	 * This method checks if the active player has owned all 42 countries
	 * 
	 * @param board
	 * @return
	 */
	public boolean hasPlayerWon(Board board) {
		if (board.getActivePlayer().getCountriesOwned().size() == 42) {
			return true;
		} else {
			return false;
		}
	}

	public void tradeCardAutomatically(Board board) {
		try {
			Player player = board.getActivePlayer();
			if (player.getCardsHeld() != null && !player.getCardsHeld().isEmpty()
					&& player.getCardsHeld().size() >= 3) {
				boolean isCardTraded = false;
				int reinforcement = 0;
				List<Card> infantryCards = new ArrayList<>();
				List<Card> cavalryCards = new ArrayList<>();
				List<Card> artilleryCards = new ArrayList<>();
				List<Card> tradedCards = new ArrayList<>();

				for (Card card : player.getCardsHeld()) {
					if (card.getCardType().equals(CardType.INFANTRY)) {
						infantryCards.add(card);
					}
					if (card.getCardType().equals(CardType.CAVALRY)) {
						cavalryCards.add(card);
					}
					if (card.getCardType().equals(CardType.ARTILLERY)) {
						artilleryCards.add(card);
					}
				}
				if (infantryCards.size() > 0 && cavalryCards.size() > 0 && artilleryCards.size() > 0) {
					reinforcement = ScoreConfiguration.getCardBonus("All");
					tradedCards.add(infantryCards.get(0));
					tradedCards.add(artilleryCards.get(0));
					tradedCards.add(cavalryCards.get(0));
					isCardTraded = true;
				} else {
					if (artilleryCards.size() >= 3) {
						reinforcement = ScoreConfiguration.getCardBonus(CardType.ARTILLERY.getCardTypeValue());
						tradedCards.add(artilleryCards.get(0));
						tradedCards.add(artilleryCards.get(1));
						tradedCards.add(artilleryCards.get(2));
						isCardTraded = true;
					} else if (cavalryCards.size() >= 3) {
						reinforcement = ScoreConfiguration.getCardBonus(CardType.CAVALRY.getCardTypeValue());
						tradedCards.add(cavalryCards.get(0));
						tradedCards.add(cavalryCards.get(1));
						tradedCards.add(cavalryCards.get(2));
						isCardTraded = true;
					} else if (infantryCards.size() >= 3) {
						reinforcement = ScoreConfiguration.getCardBonus(CardType.INFANTRY.getCardTypeValue());
						tradedCards.add(infantryCards.get(0));
						tradedCards.add(infantryCards.get(1));
						tradedCards.add(infantryCards.get(2));
						isCardTraded = true;
					} else {
						System.out.println("Neither set of 3 cards nor all 3 cards are same");
						gameLogs.log("Neither set of 3 cards nor all 3 cards are same");
					}
				}
				gameLogs.log("BONUS FROM TRADED CARDS " + reinforcement);
				System.out.println("BONUS FROM TRADED CARDS " + reinforcement);
				System.out.println("Players card before trading " + player.getCardsHeld().size());
				if (isCardTraded) {
					player.getCardsHeld().removeAll(tradedCards);
					System.out.println("Players card after trading " + player.getCardsHeld().size());
				}
			} else {
				System.out.println(board.getActivePlayer().getPlayerName() + " doesnt have enough cards for trading");
				gameLogs.log(board.getActivePlayer().getPlayerName() + " doesnt have enough cards for trading");
			}
		} catch (Exception e) {
		}
	}

	public void handleCardPickUpCase(Board board) {
		try {
			System.out.println("Active player picking card" + board.getActivePlayer().getPlayerName());
			if (board != null && board.getCardDeck() != null && !board.getCardDeck().isEmpty()) {
				Card card = board.getCardDeck().get(0);
				board.getActivePlayer().getCardsHeld().add(card);
				board.getCardDeck().remove(0);
				System.out.println(
						"Active player picked card::" + card.getTerritoryName() + " CardType::" + card.getCardType());
				System.out.println("Card Deck size after picking card " + board.getCardDeck().size());
			} else {
				System.out.println("Card Deck is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
