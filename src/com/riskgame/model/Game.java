package com.riskgame.model;

import java.util.*;
import com.riskgame.utility.*;
import com.riskgame.model.*;


public class Game {
	private  int playerCount;
	private int playerMaxNum=6;
	private World world;
	private List<Player> playerList;
	RiskUtility utility = new RiskUtility();

	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}

	public void setPlayerQueue(Queue<Player> playerQueue) {
		this.playerQueue = playerQueue;
	}

	private Queue<Player> playerQueue;
	private List<Card> cardDeck;
	private Player player;

	public Game() {
		this.cardDeck = new LinkedList<Card>();
		this.playerList=new ArrayList<Player>() ;
		this.playerCount=0;

	}

	/**
	 * getter method for the current player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * setter method for the current player
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * returns the list of players
	 * @return
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * set the list of players
	 * @param playerList
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}


	/**
	 * returns the number of current players
	 * @return
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * set trhe number of current players
	 * @param playerCount
	 */
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * return the cards
	 * @return
	 */
	public List<Card> getCardDeck() {
		return cardDeck;
	}

	/**
	 * set the number of the armies
	 * @param count
	 */
	public void setarmyQuant(int count) {
		for(Player player:getPlayerList()) {
			player.SetArmies(count);
		}}

	/**
	 * check the possibility of adding players
	 * @param playerName
	 * @return
	 */
	public boolean addPlayer(String playerName) {
		if((playerList.size()==playerCount && playerCount!=0) ||playerList.size()==playerMaxNum )
		{
			return false;
		}
		else{
			playerList.add(new Player(playerList.size()+1, playerName));
			return true;
		}

	}

	/**
	 * checks if we have duplaication in the player name
	 * @param playerName
	 * @return boolean
	 */
	public boolean duplicatePlayer(String playerName)
	{
		for(Player player:playerList)
			if(player.getPlayer_name().equalsIgnoreCase(playerName))
				return true;

		return false;

	}

	/**
	 * This method creates a new card and add it to the card Deck.
	 * 
	 * @param territoryName
	 * @throws Exception
	 */
	public void addNewCardToDeck(String territoryName) throws Exception {
		CardType cardType = utility.generateRandomCardType();
		Card card = new Card(territoryName, cardType);
		cardDeck.add(card);
	}


	/**
	 * getter method for returning the world object
	 * @return
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * setter method for setting the map object
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * asign the armies to the players based on their quantity
	 */
	public void assignArmiesToPlayers() {
		List<String> countries=new ArrayList<>();
		countries=getWorld().countryNamesList();

		switch(playerList.size()) {

			case 2:setarmyQuant(40);
				break;
			case 3:setarmyQuant(35);
				break;

			case 4:setarmyQuant(30);
				break;

			case 5:setarmyQuant(25);
				break;

			case 6:setarmyQuant(20);
				break;



	}}
		/**
		 * Assigning the reinforcement armies
		 */
		public void assignReinforcementArmies() {

			for(Player player:getPlayerList()) {
				int armyToReinforce=((player.getCountriesOwned().size())/3);
				for(Continent continent:getWorld().getOccupiedContinents(player.getPlayer_name())) {
					armyToReinforce=armyToReinforce+continent.getControlValue();
				}
				armyToReinforce=(armyToReinforce<3)?(3):armyToReinforce;
				player.SetArmiesToplayer(armyToReinforce+player.GetArmies());
			}
		}

		public void assignArmy(Country country, int count){
			for(Continent continent:getWorld().getContinents()) {
				if(country==null)
					continue;
				country.setQuantityOfArmies(country.getQuantityOfArmies()+count);

			}
		}
	/**
	 * the class handeling the to place the army and trigger the reinforcement
	 */
	public void placeAllArmies() {
		Queue<Player> tempQueue=getPlayerQueue();
		setPlayer(tempQueue.remove());
		ArrayList<Country> countries=getPlayer().getCountriesOwned();
		System.out.println("Placing army for "+getPlayer().getPlayer_name());
		int countryindex=0;
		while(getPlayer().GetArmies()!=0)
		{
			int armycount=1;
			assignArmy(countries.get(countryindex), armycount);
			countryindex=(countryindex==countries.size()-1)?0:++countryindex;
		}

	}


}
