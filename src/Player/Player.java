package Player;
import Money.*;
import Tiles.*;
import Board.Board;
import java.util.ArrayList;
//TESTING FINISHED
/**
 * A player in the monopoly game.
 */
public class Player {
    private String name;
    private MoneyDrawer cash;
    private int tileIndex;

    private ArrayList<Tile> titleDeeds;



    private boolean jailed;
    private int jailTurns;
    private boolean bankrupt;

    /**
     * Constructs a player with a name and starting cash.
     * @param name name of the token
     */
    public Player(String name){
        this.name = name;
        cash = new MoneyDrawer();
        tileIndex = 0;
        titleDeeds = new ArrayList<Tile>();
        jailed = false;
        jailTurns = 0;
        bankrupt = false;
    }
    // MUTATORS--------------------------------------------------------------------------------------------
    /**
     * Moves the player spaces amount on the board.
     * @param index amount of spaces moved.
     * @return true if passed go.
     */
    public boolean move(int index){ //try boolean move instead of void move
        tileIndex += index;
        if(tileIndex > Board.MAX_TILES){
            tileIndex -= Board.MAX_TILES;
            return true;
        }
        return false;
    }

    /**
     * Sets the jail status of the player.
     */
    public void setJailed() {
        jailed = !jailed;
    }

    /**
     * Buys a street and adds title deed to player.
     * @param bought the street bought.
     */
    public void buyStreet(Tile bought){
        if(bought instanceof Street){
            titleDeeds.add(bought);
            bought.setOwner(this);
        }
    }

    /**
     * Computes a transaction to buy the street.
     * @param street
     */
    public void buyProperty(Tile street){
        if(street instanceof Street){
            if(!street.isStreetOwned()){
                getMoneyDrawer().subtractMoney(street.getPrice());
                buyStreet(street);
            }
            else if (street.getOwner() == this){
                getMoneyDrawer().subtractMoney(street.getPropertyPrice());
                street.addProperty(1);
            }
            //else buy from someone else
        }
    }


    /**
     * Subtracts money from the player to pay rent
     * @param rent money to pay rent.
     * @return an array list of the money that was paid for rent
     */
    public ArrayList<Money> payRent(int rent){
        return cash.subtractMoney(rent);
    }

    /**
     * Add money from rent collected
     * @param rent amount of rent collected
     */
    public void getRent(int rent){
        cash.addMoney(rent);
    }



    /**
     * Sets the bankrupt status of the player.
     * @param bankrupt the bankrupt status of the player.
     */
    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
        if(bankrupt){
            tileIndex = -1;
        }
    }

    /**
     * Increases length of jail term by one turn;
     */
    public void setJailTurns(){
        jailTurns++;
        if(jailTurns == 3){
            jailed = false;
            jailTurns = 0;
        }
        else if (jailTurns == 1){
            jailed = true;
        }
    }
    // ACCESSORS-------------------------------------------------------------------------------------------

    /**
     * Gets the name of the player.
     * @return the name
     */
    public String getName(){return name;}

    /**
     * Gets the amount of jail turns
     * @return the amount of jail turns.
     */
    public int getJailTurns(){return jailTurns;}
    /**
     * Gets the tile index.
     * @return tile index.
     */
    public int getTileIndex(){ return tileIndex; }

    /**
     * Gets the money drawer of the player.
     * @return the cash money drawer
     */
    public MoneyDrawer getMoneyDrawer(){
        return cash;
    }

    /**
     * Gets the status of if player is jailed.
     * @return true if player is jailed.
     */
    public boolean getJailed(){ return jailed; }

    /**
     * Gets boolean for if player is bankrupt and lost the game.
     * @return true if bankrupted
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /* TITLE DEED METHOD ACCESSORS---------------------------------------------------- */
    /**
     * Gets the list of title deeds of the player's.
     * @return the list of title deeds
     */
   public ArrayList<Tile> getTitleDeeds(){
       String allDeeds = "";
       for(Tile street : titleDeeds){
           allDeeds += street + "\n";
       }
        return titleDeeds;
   }

    /**
     * Checks if player owns all cards in a tile group.
     * @param group the group of tile cards
     * @return true if player owns all cards in the group
     */
    public boolean ownsColorGroup(String group){
        int maxTile = 0;
        if(group.equals(Board.BROWN) || group.equals(Board.DARK_BLUE)){
            maxTile = 2;
        }
        else if(group.equals(Board.LIGHT_BLUE) || group.equals(Board.PINK) ||
                group.equals(Board.ORANGE) || group.equals(Board.RED) ||
                group.equals(Board.YELLOW)|| group.equals(Board.GREEN)){
            maxTile = 3;
        }
        else{ //group.equals(Board.RAILROAD)
            maxTile = 4;
        }
        int count = 0;
        for(Tile temp: titleDeeds){
            if(temp instanceof ColoredStreet temp2){
                if(temp2.getColor().equals(group)){
                    count++;
                }
            }
        }
        return count == maxTile;
    }

    /**
     * Checks if player owns all utility cards.
     * @return true if player owns all utility
     */
    public boolean ownsUtilGroup(){
        int max = 2;
        int count = 0;
        for(Tile temp: titleDeeds){
            if(temp instanceof Utility){
                count++;
            }
        }
        return count == max;
    }

    /**
     * Gets amount of railroads owned.
     * @return amount of railroads owned
     */
    public int getRailAmount(){
        int count = 0;
        for(Tile temp: titleDeeds){
            if(temp instanceof Railroad){
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the player name
     * @return name of player.
     */
    public String toString(){
        return getName();
    }



    //Testing methods.
    public static void main(String[] args){
        //Test accessors w/o titleDeed ones
        Player player = new Player("Name");
        System.out.println("Name: "+ player.getName());
        System.out.println("Tile Index: " + player.getTileIndex());
        System.out.println("Jail turns: " + player.getJailTurns());
        System.out.println("MoneyDrawer Total: " + player.getMoneyDrawer());
        System.out.println("Jailed: " + player.getJailed());
        System.out.println("Bankrupted?: "+ player.isBankrupt());

        //Test title deed accessors
        System.out.println("TitleDeeds: "+player.getTitleDeeds());
        System.out.println("Owns color groups in blue: "+player.ownsColorGroup("dark blue"));
        System.out.println("Owns utility group: "+player.ownsUtilGroup());
        System.out.println("Railroads owned: "+player.getRailAmount());

        //TESTING MUTATORS W/O TITLE DEEDS
        boolean passGo = player.move(39);
        if(passGo)
            System.out.println("Congrats, money distributed");
        System.out.println("Tile Index: " + player.getTileIndex());
        player.setJailed();
        System.out.println("Jailed: " + player.getJailed());
        System.out.println("Jail turns: " + player.getJailTurns() + "Expected: 3");
        player.setJailTurns();
        player.setJailTurns();
        System.out.println("Jail turns: " + player.getJailTurns() + "Expected: 2");
        player.setJailTurns();
        System.out.println("Jail turns: " + player.getJailTurns() + "Expected: 0");
        player.getRent(500);
        System.out.println("MoneyDrawer Total: " + player.getMoneyDrawer());
        player.setBankrupt(true);
        System.out.println("Bankrupted?: "+ player.isBankrupt());
        player.setBankrupt(false);
        player.payRent(600);
        System.out.println("MoneyDrawer Total: " + player.getMoneyDrawer());

        Player player2 = new Player("JJ");
        passGo = player2.move(41);
        if(passGo)
            System.out.println("Congrats, money distributed");
        System.out.println("Tile Index: " + player2.getTileIndex());

        //Testing title deed add and check all utilities method
        Tile util1 = new Utility("Utility 1",500,250);
        Tile util2 = new Utility("Utility 2", 60,30);
        player2.buyStreet(util1);
        player2.buyStreet(util2);
        System.out.println("TitleDeeds: "+player2.getTitleDeeds());
        System.out.println("Owns utility group: "+player2.ownsUtilGroup());

        //TEST getRailAmount method
        Tile railroad1 = new Railroad("B&O Railroad", 200,100, new int[]{25,50,100,200});
        Tile railroad2 = new Railroad("Short Line", 200, 100, new int[] {25,50,100,200});
        player2.buyStreet(railroad1);
        player2.buyStreet(railroad2);
        System.out.println("TitleDeeds: "+player2.getTitleDeeds());
        System.out.println("Railroads owned: "+player2.getRailAmount() + " Expected: 2");

        //Test get all colored street
        Tile street1 = new ColoredStreet("Park Place", 350,175,new int[]{35,175,500,1100,1300,1500},"dark blue",200);
        Tile street2 = new ColoredStreet("BoardWalk", 400,200,new int[]{50,200,600,1400,1700,2000},"dark blue",200);
        player2.buyStreet(street1);
        player2.buyStreet(street2);
        System.out.println("TitleDeeds: "+player2.getTitleDeeds());
        System.out.println("Owns color groups in blue: "+player2.ownsColorGroup("dark blue"));

        //public boolean move(int index) DONE
        //public void setJailed() DONE
        //public void buyStreet(Tile bought) DONE
        //public ArrayList<Money> payRent(int rent) DONE
        //public void getRent()) DONE
        //public void setBankrupt(boolean bankrupt) DONE
        //public void setJailTurns() DONE

        //ACCESSORS:

        //public String getName() DONE
        //public int getJailTurns() DONE
        //public int getTileIndex() DONE
        //public MoneyDrawer getMoneyDrawer() DONE
        //public boolean getJailed()DONE
        // public boolean isBankrupt() DONE

        //public ArrayList<Street> getTitleDeeds() DONE
        //public boolean ownsColorGroup(String group)
        // public boolean ownsUtilGroup(String util) DONE
        //public int getRailAmount(String rail) DONE
    }

}
