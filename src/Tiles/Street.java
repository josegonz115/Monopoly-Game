package Tiles;
import Player.Player;

/**
 * A street tile.
 */
public class Street extends Tile{
    private final static double MORTGAGE_INTEREST = 1.1;

    private int price;
    private int mortgage;
    private Player owner;
    private boolean streetOwned;
    private boolean streetMortgaged;


    /**
        Constructs the Tiles.Street tile.
        @param name the name of the street.
        @param price the price of the street.
        @param mortgage the mortgage for the street
     */
    public Street(String name, int price, int mortgage){
        super(name);
        this.price = price;
        this.mortgage = mortgage;
        owner = null;
        streetOwned = false;
        streetMortgaged = false;
    }

    // MUTATORS: ----------------------------------------------------------------------
//    /**
//        Sets the title card up with information about property.
//     */
//    public void setTitleDeed(Player newPlayer){
//        newPlayer.buyStreet(this);
//    }

    /**
        Sets the owner of the street.
        @param newPlayer the new owner of the street
     */
    public void setOwner(Player newPlayer){
        owner = newPlayer;
        streetOwned = true;
    }

    /**
        Removes the owner of the street.
     */
    public void removeOwner(){
        owner = null;
        streetOwned = false;
        streetMortgaged = false;
    }

    /**
     * Adds properties to the street.
     * @param amount amount of properties to add.
     */
    public boolean addProperty(int amount){
        //nothing: to be overrided
        return false;
    }

    /**
     Remove a property on the street.
     @param amount the amount removed
     */
    public boolean removeProperty(int amount){return false;}

    /**
     * Sets a mortgage on or off on the street.
     */
    public void setMortgage(){ streetMortgaged = !streetMortgaged; }

    // ACCESSORS -------------------------------------------------------------------------------
    /**
     Gets the price of the street.
     @return the price
     */
    public int getPrice(){
        return price;
    }

    /**
     Gets the owner of the street.
     @return the owner of the street.
     */
    public Player getOwner(){ return owner; }

    /**
     * Gets if street is owned.
     * @return true if street is owned.
     */
    public boolean isStreetOwned() {return streetOwned;}

    /**
     * Get the price of the mortgage.
     * @return mortgage price.
     */
    public int getMortgage(){ return mortgage; }

    /**
     * Gets true if street is mortgaged.
     * @return true if street is mortgaged
     */
    public boolean isStreetMortgaged() {return streetMortgaged;}

    /**
     * Gets the mortgage plus interest of the street.
     * @return mortgage + interest
     */
    public int getInterestMortgage(){
        return (int)(MORTGAGE_INTEREST * mortgage);
    }

    /**
     * Gets the rent price for the properties on the street
     */
    public int getRentPrices(){
        return 0;
    }

    public boolean getMaxProperty(){return false;}
    public int getPropertyPrice() {return -1;}
    public int getProperties() {return 1;}

//    /**
//     * Gets the properties on the street
//     */
//    public String toString(){ return "";}//check to see if it is correct
}
