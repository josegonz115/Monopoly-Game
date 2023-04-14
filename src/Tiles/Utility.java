package Tiles;
import Player.Player;
import Money.Money;
import Dice.Dice;
import java.util.ArrayList;

/**
 * A utility tile.
 */
public class Utility extends Street{
    int[] propertyPrices;

    /**
     Constructs the Utility.Street tile.
     @param name the name of the utility.
     @param price the price of the utility.
     @param mortgage the mortgage of the utility
    // @param propertyPrices the price of rent for each utility owned
    //@param dice the dice value that was rolled
     */
    public Utility(String name, int price, int mortgage){
        super(name, price, mortgage);
    }

    /**
     * Gets the price of the
     * @param dice the dice of the game
     */
    public ArrayList<Money> payRent(Player player, Dice dice){
        final String UTIL = "Utility";
        int amount = dice.getLastRoll();
        if(getOwner().ownsUtilGroup()){
            amount *= 10;
        }
        else{
            amount *= 4;
        }
        return player.getMoneyDrawer().subtractMoney(dice.getLastRoll());
    }

    /**
     * Checks if there is a max amount of properties on the board.
     * @return true if there is max properties.
     */
    public boolean getMaxProperty(){return isStreetOwned();}


    /**
     * Display the utility property.
     * @return the name
     */
    public String toString(){
        return getName();
    }
}
