package RiskyGame.Model;
//package sample.model;
import java.util.*;
import java.util.Map;

/**
* This class maintains the player details and implements methods related to the player.
* @author Himani
*/
public class Player extends Observable{

  private String playerId;
  private String playerName;
  private int playerArmies;
  private Boolean isOut;
  private String playerCharacter;
  private Color playerColor;
  private HashMap<String, Units> territoriesHeld;
  private HashMap<String, Continent> continentHeld;
  private List<Card> cardsHeld;
  private int cardsHolded;
  private int cardTurn;

  /**
   * Constructor with parameters.
   *
   * @param playerName
   * @param playerArmies
   * @param isKnocked
   * @param playerCharacter
   * @param playerColor
   * @param cardsHolded
   */
  public Player(String playerName, int playerArmies, Boolean isOut, String playerCharacter, Color playerColor, int cardsHolded) {

      this.playerName = playerName;
      this.playerArmies = playerArmies;
      this.isOut = isOut;
      this.playerCharacter = playerCharacter;
      this.playerColor = playerColor;
      this.cardsHolded = cardsHolded;
  }

  /**
   * constructor with parameters.
   * @param playerId
   * @param playerName
   * @param playerArmies
   * @param isKnocked
   * @param cardsHolded
   */
  public Player(String playerId, String playerName, int playerArmies, Boolean isOut, int cardsHolded) {

      this.playerId = playerId;
      this.playerName = playerName;
      this.playerArmies = playerArmies;
      this.isOut = isOut;
      this.cardsHolded = cardsHolded;
  }

  /**
   * To set player name.
   * @param playerName
   */
  public void setPlayerName(String playerName) {
      this.playerName = playerName;
  }

  /**
   * To set Number of armies.
   * @param playerArmies
   */
  public void setPlayerArmies(int playerArmies) {
      this.playerArmies = playerArmies;
  }

  /**
   * Set if player knocked.
   * @param knocked
   */
  public void setKnocked(Boolean Out) {
      isOut = Out;
  }

  /**
   * Set Player's character.
   * @param playerCharacter
   */
  public void setPlayerCharacter(String playerCharacter) {
      this.playerCharacter = playerCharacter;
  }

  /**
   * Set Player's color.
   * @param playerColor
   */
  public void setPlayerColor(Color playerColor) {
      this.playerColor = playerColor;
  }

  /**
   * Set number of cards that are held.
   * @param cardsHeld
   */
  public void setCardsHolded(int cardsHolded) {
      this.cardsHolded = cardsHolded;
  }

  /**
   * Set number of territories held by the player.
   * @param territoriesHeld
   */
  public void setTerritoriesHeld(HashMap<String, Units> territoriesHeld) {
      this.territoriesHeld = territoriesHeld;
  }

  /**
   * Set number of continents held by the player.
   * @param continentHeld
   */
  public void setContinentHeld(HashMap<String, Continent> continentHeld) {
      this.continentHeld = continentHeld;
  }

  /**
   * To set the player Id;
   * @param playerId
   */
  public void setPlayerId(String playerId) {
      this.playerId = playerId;
  }

  /**
   * To set the cards list.
   * @param cardsHeld
   */
  public void setCardsHeld(List<Card> cardsHeld) {
      this.cardsHeld = cardsHeld;
  }

  /**
   * To set the card turns.
   * @param cardTurn
   */
  public void setCardTurn(int cardTurn) {
      this.cardTurn = cardTurn;
  }

  /**
   * To get the card turns.
   * @return card turn
   */
  public int getCardTurn() {
      return cardTurn;
  }

  /**
   * To get the cards list.
   * @return cards list
   */
  public List<Card> getCardsHeld() {
      return cardsHeld;
  }

  /**
   * returns the Player name.
   * @return playerName
   */
  public String getPlayerName() {
      return playerName;
  }

  /**
   * returns the number of armies player held.
   * @return playerArmies
   */
  public int getPlayerArmies() {
      return playerArmies;
  }

  /**
   * returns if the player is knocked out or not.
   * @return isOut
   */
  public Boolean getKnocked() {
      return isOut;
  }

  /**
   * returns the players character.
   * @return playerCharacter
   */
  public String getPlayerCharacter() {
      return playerCharacter;
  }

  /**
   * returns players color.
   * @return playerColor
   */
  public Color getPlayerColor() {
      return playerColor;
  }

  /**
   * returns the number of cards players held.
   * @return cardsHeld
   */
  public int getCardsHolded() {
      return cardsHolded;
  }

  /**
   * returns the territories player held.
   * @return territoriesHeld
   */
  public HashMap<String, Units> getTerritoriesHeld() {
      return territoriesHeld;
  }

  /**
   * returns the continents player held.
   * @return continentHeld
   */
  public HashMap<String, Continent> getContinentHeld() {
      return continentHeld;
  }

  /**
   * To return the player Id.
   * @return playerId
   */
  public String getPlayerId() {
      return playerId;
  }

  /**
   * Overrides the method toString() method.
   * @return playerName
   */
  @Override
  public String toString() {
      return playerName;
  }

  /**
   * To increment player army count by 1.
   */
  public void incrementArmycountby1() {
      playerArmies += 1;
  }

  /**
   * To decrement player army count by 1.
   */
  public void decrementArmycountby1() {
      playerArmies -= 1;
  }

  /**
   * To increase army count by some value.
   * @param value
   */
  public void increaseArmyCountByValue(int value) {

      playerArmies += value;
      setChanged();
      notifyObservers(playerArmies);
  }

  /**
   * To decrease army count by some value.
   * @param value
   */
  public void decreaseArmyCountByValue(int value) {

      playerArmies -= value;
      setChanged();
      notifyObservers(playerArmies);
  }

  /**
   * To add card to the list.
   * @param card
   */
  public void addCard(Card card) {

      cardsHeld.add(card);
  }

  /**
   * To remove the card from the list.
   * @param card
   */
  public void removeCard(Card card) {

      cardsHeld.remove(card);
  }

  /**
   * To add the territory to the list and notify observers.
   * @param territory
   */
  public void addTerritory(Units territory) {

      territoriesHeld.put(territory.getTerritorieName(),territory);
      setChanged();
      notifyObservers(territoriesHeld);
  }

  /**
   * To remove the territory from the player lis and notify observers.
   * @param territory
   * @return boolean
   */
  public boolean removeTerritory(Units territory) {

      if(territoriesHeld.containsKey(territory.getTerritorieName())) {
          territoriesHeld.remove(territory.getTerritorieName());
          setChanged();
          notifyObservers(territoriesHeld);
          return true;
      }
      return false;
  }

  /**
   * To add the continent to the list and notify observers.
   * @param continent
   */
  public void addContinent(Continent continent) {

      continentHeld.put(continent.getContinentName(),continent);
      System.out.println("[Player Continent Size is = ]" + continentHeld.size());
      setChanged();
      notifyObservers(this.continentHeld);
  }

  /**
   * To remove the continent from the player list and notify observers.
   * @param continentName
   * @return boolean
   */
  public boolean removeContinent(String continentName) {

      if(this.continentHeld.containsKey(continentName)) {
          this.continentHeld.remove(continentName);
          this.setChanged();
          this.notifyObservers(this.continentHeld);
          return true;
      }
      return false;
  }

  /**
   * To calculate the reinforcement armies to the players.
   * @return int
   */
  public void calculateReinforcementArmies() {

      int reinforcementArmies = 0;
      if(this.territoriesHeld.size()/3 < 3) {
          reinforcementArmies = 3;
      } else {
          reinforcementArmies = this.territoriesHeld.size()/3;
      }
      if(!this.continentHeld.isEmpty()) {
          System.out.println("[ReinforcementPhase][Checking for continent list of the player]");
          for(String key : this.continentHeld.keySet()) {
              Continent continent = this.continentHeld.get(key);
              reinforcementArmies = reinforcementArmies + continent.getContinentScore();
          }
      }

      System.out.println("[Reinforcement amries are calculated]" +" [" +reinforcementArmies + "]");
      increaseArmyCountByValue(reinforcementArmies);
  }

  /**
   * This method check if the player can do a valid fortification. If yes then player can fortify.
   * @param sourceCountry
   * @param destinationCountry
   * @return boolean
   */
  public boolean canFortify(String sourceCountry,String destinationCountry) {

      Units sourceTerritory = territoriesHeld.get(sourceCountry);
      if(sourceTerritory.getAdjacentTerritories().contains(destinationCountry)) {
          return true;
      } else {
          return false;
      }
  }

  /**
   * This method performs a fortification move.
   * @param noOfArmiesToMove
   * @param sourceCountry
   * @param destinationCountry
   * @return boolean
   */
  public boolean doFortification(int noOfArmiesToMove,String sourceCountry,String destinationCountry) {

      Units sourceTerritory = territoriesHeld.get(sourceCountry);
      sourceTerritory.setArmiesHeld(sourceTerritory.getArmiesHeld()-noOfArmiesToMove);

      Units destinationTerritory = territoriesHeld.get(destinationCountry);
      destinationTerritory.setArmiesHeld(destinationTerritory.getArmiesHeld()+noOfArmiesToMove);

      return true;
  }

  /**
   * Returns true if the attacked country is adjacent to attacker country.
   * @param attackerCountry
   * @param attackedCountry
   * @return boolean
   */
  public boolean canAttack(String attackerCountry,String attackedCountry) {

      Units sourceCountry = this.territoriesHeld.get(attackerCountry);
      if(sourceCountry.getAdjacentTerritories().contains(attackedCountry)) {
          return true;
      }
      return false;
  }

  /**
   * This method performs attack by rolling the dice and declares the winner.
   * @param attackerDice
   * @param attackedDice
   * @param attackerCountry
   * @param attackedCountry
   * @param defender
   * @return attack result
   */
  public String doAttack(int attackerDice,int attackedDice,String attackerCountry,String attackedCountry,Player defender) {

      // declare Arraylist for dice.
      ArrayList<Integer> attackerDiceList = new ArrayList<>();
      ArrayList<Integer> attackedDiceList = new ArrayList<>();

      // call the dice method to arraylist.
      attackerDiceList = rollDice(attackerDice);
      printTheDiceToTheConsole(attackerDiceList);

      attackedDiceList = rollDice(attackedDice);
      printTheDiceToTheConsole(attackedDiceList);

      // to display the dice.
     // displayTheDice(attackerDiceList,attackedDiceList);

      // Call the function to decide the winner.
      String result = determineWinner(attackerDiceList,attackedDiceList,defender,attackedCountry,attackerCountry);

      return result;
  }

  /**
   * This method returns list of dice values after rolling the dice.
   * @param noOfDice
   * @return diceList
   */
  public ArrayList<Integer> rollDice(int noOfDice) {

      ArrayList<Integer> diceList = new ArrayList<>();
      int max = 6,min = 1;
      int range = max - min + 1;
      for(int i = 0; i<noOfDice; i++) {
          diceList.add((int) (Math.random() * range) + min);
      }
      Collections.sort(diceList,Collections.reverseOrder());
      return diceList;
  }

  /**
   * This function determines the winner of the battle.
   * @param attackerDiceList
   * @param attackedDiceList
   * @param defender
   * @return Battle result
   */
  public String determineWinner(ArrayList<Integer> attackerDiceList,ArrayList<Integer> attackedDiceList
          ,Player defender,String attackedCountry, String attackerCountry) {

      int attackerDecreamentArmy=0,attackedDecreamentArmy=0,index = 0;

      if(attackedDiceList.size() < attackerDiceList.size()) {
          index = attackedDiceList.size();
      } else if(attackerDiceList.size() < attackedDiceList.size()) {
          index = attackerDiceList.size();
      } else if(attackerDiceList.size() == attackedDiceList.size()) {
          index = attackedDiceList.size();
      }

      for(int i = 0; i<index; i++) {
          if(attackerDiceList.get(i) > attackedDiceList.get(i)) {
              attackedDecreamentArmy++;
          } else {
              attackerDecreamentArmy++;
          }
      }

      territoriesHeld.get(attackerCountry).decreaseArmyCountByvalue(attackerDecreamentArmy);
      defender.getTerritoriesHeld().get(attackedCountry).decreaseArmyCountByvalue(attackedDecreamentArmy);

      if(attackerDecreamentArmy < attackedDecreamentArmy) {
          return "WINNER";
      } else if(attackerDecreamentArmy > attackedDecreamentArmy) {
          return "LOSER";
      } else if(attackedDecreamentArmy == attackerDecreamentArmy) {
          return "TIE";
      }

      return "";
  }

  /**
   * This method prints the dice to the list.
   * @param diceList
   */
  public void printTheDiceToTheConsole(ArrayList<Integer> diceList) {

      System.out.print("The Player dice list is = ");
      for(int i = 0; i<diceList.size(); i++) {
          System.out.print(diceList.get(i) + " ");
      }
      System.out.println("");
  }

   /**
   * This method checks if the territory has lost all armies during the attack.
   * @param Country
   * @return boolean
   */
  public boolean checkIfATerritoryHasLostAllArmies(String Country) {

      if(territoriesHeld.get(Country).getArmiesHeld() == 0) {
          return true;
      }
      return false;
  }

  /**
   * Checks if the player has lost all the countries.
   * @return boolean
   */
  public boolean checkIfPlayerHasLostAllCountries() {

      if(territoriesHeld.size() == 0) {
          return true;
      }
      return false;
  }

  /**
   * This function transfers the cards from one player to another.
   * @param attacker
   */
  public void transferCardsFromOnePlayerToAnother(Player attacker) {

      for(int i = 0; i<cardsHeld.size(); i++) {
          attacker.getCardsHeld().add(cardsHeld.get(i));
      }
      cardsHeld.clear();
      cardsHolded = 0;

      attacker.setCardsHolded(attacker.getCardsHeld().size());
  }

}
