package Tiles;
import Player.Player;
//TESTING FINISHED
/**
 * Board.Board tile of monopoly game.
 */
public class Tile {
    String name;
    /**
     * Constructs a Tile with name
     * @param name the name of the tile
     */
    public Tile(String name){
        this.name = name;
    }

    //EVERYTHING BELOW IS OVERRIDEN
    // MUTATORS ------------------------------------------------------------
    public void setOwner(Player newPlayer){}
    public void setTitleDeed(Player newPlayer){}
    public void removeOwner(){}
    public void setMortgage(){}
    public boolean addProperty(int amount){return false;}
    public boolean removeProperty(int amount){ return false;}
    
    //ACCESSORS --------------------------------------------------------
    public int getPrice(){return -1;}
    public Player getOwner(){return null;}
    public boolean isStreetOwned(){return false;}
    public int getMortgage(){return -1;}
    public boolean isStreetMortgaged() {return false;}
    public int getInterestMortgage(){ return -1;}
    public int getRentPrices(){return -1;}
    public boolean getMaxProperty(){return false;}

    public int getPropertyPrice() {
        return -1;
    }
    public int getProperties() {return -1;}

    /**
     * Gets the name of the Tile.
     * @return the name
     */
    public String getName(){
        return name;
    }



    //TESTING TILES CLASSES
    public static void main(String[] args){
        Tile[] tiles = new Tile[8];
        tiles[0] = new Tile("Visting Jail");
        tiles[1] = new ColoredStreet("St. Charles Place", 140,70, new int[]{10,50,150,450,625,750}, "pink", 100);
        tiles[2] = new Utility("Electric Company", 150, 75);
        tiles[3] = new ColoredStreet("States Avenue", 140, 70, new int[]{10, 50,150,450,625,750},"pink", 100);
        tiles[4] = new ColoredStreet("Virginia Avenue", 160, 80, new int[]{12, 60, 180, 500, 700, 900},"pink", 100);
        tiles[5] = new Railroad("Pennsylvania Railroad", 200, 100, new int[]{25, 50, 100, 200});
        tiles[6] = new ColoredStreet("St. James Place", 180, 90, new int[]{14, 70, 200, 550, 750, 950},"orange",100);
        tiles[7] = new CommunityChest();

        //Test accessors
        System.out.println("Jail name: "+ tiles[0].getName());
        System.out.println("Community Chest Name: "+ tiles[7].getName());
        Tile street = tiles[1];
        System.out.println("Owner of Charles St: " + street.getOwner() + " Expected: None");
        System.out.println("Price of Street: " + street.getPrice() + " Expected: 140");
        System.out.println("Is Charles St Owned: " + street.isStreetOwned() + " Expected: false");
        System.out.println("Is Charles St Mortgaged: " + street.isStreetMortgaged() + " Expected: false");
        System.out.println("What is mortgage of St Charles: " + street.getMortgage() + " Expected: 70");
        System.out.println("What is the interest mortgage: " + street.getInterestMortgage() + " Expected: 77");
        System.out.println("What are the rent prices: " + street.getRentPrices() + "Expected: 0");

        //Test mutators
        Player player = new Player("JJ");
        street.setOwner(player);
        System.out.println("\nNew owner of St Charles: " + street.getOwner());
        street.removeOwner();
        System.out.println("New owner of St Charles: " + street.getOwner() + " Expected: None");
        street.setOwner(player);
        street.setMortgage();
        System.out.println("Is Charles St Mortgaged: " + street.isStreetMortgaged() + " Expected: true");


        //Test buying property methods
        street.addProperty(1);
        System.out.println("\nWhat are the rent prices: " + street.getRentPrices() + " Expected: 50");
        street.addProperty(1);
        System.out.println("What are the rent prices: " + street.getRentPrices() + " Expected: 150");
        street.removeProperty(1);
        System.out.println("What are the rent prices: " + street.getRentPrices() + " Expected: 50");
        if(street.addProperty(4)){
            System.out.println("Successful buy of 4 houses to make a hotel");
        }
        System.out.println("What are the rent prices: " + street.getRentPrices() + " Expected: 750");
        if(!street.removeProperty(6)){
            System.out.println("Unsuccessful removals of 6 properties");
        }

        //TESTING RAILROAD TILE
        Tile railroad = tiles[5];
        railroad.setOwner(player);
        System.out.println("\nWhat are the rent prices: " + railroad.getRentPrices() + " Expected: 25");
        Tile utility = tiles[2];
        utility.setOwner(player);
        System.out.println("\nWhat are the rent prices: " + utility.getRentPrices() + " Expected: 25");

        System.out.println(player.getTitleDeeds());
    }
}

