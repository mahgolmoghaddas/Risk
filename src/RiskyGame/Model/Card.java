package RiskyGame.Model;

/**
 * This class is a card class that contains details about the card.
 * @author Zombies
 */
public class Card {

    private String territoryName;
    private String cardType;

    /**
     * Constructor with empty parameters.
     * @param territoryName
     * @param cardType
     */
    public Card(String territoryName, String cardType) {
        this.territoryName = territoryName;
        this.cardType = cardType;
    }

    /**
     * To set the territory name of the card.
     * @param territoryName
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * To set the card type.
     * @param cardType
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * To get the territory name of that card.
     * @return territoryName
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * To get the card type.
     * @return cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * This method overrides the ToString method.
     * @return card type
     */
    @Override
    public String toString() {
        return cardType;
    }
}
