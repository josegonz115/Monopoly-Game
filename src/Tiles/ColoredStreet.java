package Tiles;
import Player.Player;
/**
 * A colored street tile.
 */
public class ColoredStreet extends Street{
    private static int MAX_PROPERTY = 5;
    private int[] rentPrices;
    private String color;
    private int propertyPrice;
    private int properties;


    /**
     * Constructs a colored street.
     * @param name the name of street
     * @param price the price to buy street
     * @param mortgage the price of mortgage for street
     * @param rentPrices the rent prices of properties
     * @param color the color of the street
     * @param propertyPrice the price of each property
     */
    public ColoredStreet(String name, int price, int mortgage, int[] rentPrices, String color, int propertyPrice){
        super(name, price, mortgage);
        this.rentPrices = rentPrices;
        this.color = color;
        this.propertyPrice = propertyPrice;
    }

    // MUTATORS: -------------------------------------------------------------------------------------------
    //Property Mutators

    /**
     * Adds properties to the street.
     * @param amount amount of properties to add.
     */
    public boolean addProperty(int amount){
        if(properties + amount <= MAX_PROPERTY){
            properties += amount;
            getOwner().getMoneyDrawer().subtractMoney(getPropertyPrice());
            return true;
        }
        return false;
    }

    /**
     * Removes properties from the street.
     * @param amount amount of properties to remove.
     */
    public boolean removeProperty(int amount){
        if(properties - amount >= 0){
            properties -= amount;
            return true;
        }
        return false;
    }


    // ACCESSORS: -------------------------------------------------------------------------------------------
    /**
     * Gets the color of the street.
     * @return the color
     */
    public String getColor(){
        return color;
    }


    /**
     * Gets the rent price of street.
     * @return rent to be paid
     */
    public int getRentPrices() {
        Player streetOwner = getOwner();
        if(streetOwner == null){
            return 0;
        }
        int rent = rentPrices[properties];
        if(streetOwner.ownsColorGroup(color)){ // add the method to PLAYERRRRRRRRRRRRRRRRR
            rent *= 2;
        }
        return rent;
    }

    /**
     * Get number of properties on street.
     * @return number of properties
     */
    public int getProperties() {
        return properties;

    }

    /**
     * Get the price of a property.
     * @return property price.
     */
    public int getPropertyPrice() {
        return propertyPrice;
    }

    /**
     * Gets if properties are a hotel instead.
     * @return true if hotel exists
     */
    public boolean hasHotel(){ return properties == 5; }

    /**
     * Checks if there is a max amount of properties on the board.
     * @return true if there is max properties.
     */
    public boolean getMaxProperty(){return getProperties() == 5;}

    /**
     * String representation of the property.
     * @return
     */
    public String toString(){
        String coloredStreetString = getName();
        if(isStreetOwned()){
            coloredStreetString += "is owned by "+ getOwner() + "\n";
            if(hasHotel()){
                coloredStreetString += " -1 hotel\n";
            }
            else{
                coloredStreetString += " -" + getProperties() + " houses\n";
            }
            if(isStreetMortgaged()){
                coloredStreetString += "Street is being mortgaged right now\n";
            }
        }
        return coloredStreetString;
    }
}

