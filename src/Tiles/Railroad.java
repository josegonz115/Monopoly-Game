package Tiles;
import Player.Player;

import java.util.ArrayList;

/**
 * A railroad tile.
 */
public class Railroad extends Street{
    private int[] propertyPrices;

    /**
     * Constructs a railroad street.
     * @param name the name
     * @param price the price
     * @param mortgage the mortgage
     * @param propertyPrices the rent price for amount of railroads owned
     */
    public Railroad(String name, int price, int mortgage, int[] propertyPrices){
        super(name, price, mortgage);
        this.propertyPrices = propertyPrices;
    }

    /**
     * Gets the rent price for the railroad properties.
     * @return amount of rent
     */
    public int getRentPrices(){
        final String TILE = "railroad";
        Player streetOwner = getOwner();
        if(streetOwner == null){
            return 0;
        }
        int ownerAmount = streetOwner.getRailAmount(); // ADD THIS METHOD TO PLAYERRRR
        return propertyPrices[ownerAmount - 1];
    }

    /**
     * Checks if there is a max amount of properties on the board.
     * @return true if there is max properties.
     */
    public boolean getMaxProperty(){return isStreetOwned();}

    /**
     * Display the railroad property.
     * @return the name
     */
    public String toString(){
        return getName();
    }
}
