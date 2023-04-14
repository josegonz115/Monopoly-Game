package Board;

import Tiles.*;
import Player.Player;
import Bot.Bot;
import Dice.Dice;


import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The board of the Monopoly game.
 */
public class Board extends JPanel{
    //Board info instance variables
    public static final String BROWN = "brown";
    public static final String LIGHT_BLUE = "light blue";
    public static final String PINK = "pink";
    public static final String ORANGE = "orange";
    public static final String RED = "red";
    public static final String YELLOW = "yellow";
    public static final String GREEN = "green";
    public static final String DARK_BLUE = "dark blue";

    public static final int MAX_TILES = 40;
    public static final int GO_INDEX = 0;
    public static final int VISITING_JAIL = 10;
    public static final int FREE_PARKING = 20;
    public static final int JAIL_INDEX = 30;

    public static final int CHANCE_DECK_SIZE = 12;

    //Board normal info.
    private Tile[] tiles;
    private Player[] players;
    private Bot[] bots;
    private Dice dice;

    //Board GUI instance variables
    private ImageComponent boardComponent;
    private Image background;
    /**
     * Constructs Board.Board object
     * @param totalPlayers the total amount of players in the game.
     * @param botAmount how many of the players are bots.
     */
    public Board(int totalPlayers, String[] playerNames, int botAmount) throws IOException{
        setupTiles();
        players = new Player[totalPlayers];
        for(int i = 0; i < totalPlayers;i++){
            players[i] = new Player(playerNames[i]);
        }
        //Create bot class later....
        bots = new Bot[botAmount];
        for(int i = 0; i < botAmount; i++){
            bots[i] = new Bot();
        }
        dice = new Dice();
        boardComponent = setupBoardComponent();
    }
    //Helper Initializers------------------------------
    /**
     * Set up the tiles of the board.
     */
    private void setupTiles(){
        tiles = new Tile[MAX_TILES];
        tiles[0] = new Tile("Go");
        tiles[1] = new ColoredStreet("Mediterranean Avenue", 60, 30,  new int[] {2,10,30,90,160,250}, BROWN, 50);
        tiles[2] = new CommunityChest();
        tiles[3] = new ColoredStreet("Baltic Avenue", 60, 30, new int[] {4, 20, 60, 180, 320, 450}, BROWN, 50);
        tiles[4] = new Tax("Income Tax", 200);
        tiles[5] = new Railroad("Reading Railroad", 200, 100, new int[] {25, 50, 100, 200});
        tiles[6] = new ColoredStreet("Oriental Avenue", 100, 50, new int[] {6, 30, 90, 270, 400, 500}, LIGHT_BLUE, 50);
        tiles[7] = new Chance();
        tiles[8] = new ColoredStreet("Vermont Avenue", 100, 50, new int[] {6, 30, 90, 270, 400, 550}, LIGHT_BLUE,50);
        tiles[9] = new ColoredStreet("Connecticut Avenue", 120, 60, new int[] {8,40,100,300,450,600}, LIGHT_BLUE,50);
        tiles[10] = new Tile("Visting Jail");
        tiles[11] = new ColoredStreet("St. Charles Place", 140,70, new int[]{10,50,150,450,625,750}, PINK, 100);
        tiles[12] = new Utility("Electric Company", 150, 75);
        tiles[13] = new ColoredStreet("States Avenue", 140, 70, new int[]{10, 50,150,450,625,750},PINK, 100);
        tiles[14] = new ColoredStreet("Virginia Avenue", 160, 80, new int[]{12, 60, 180, 500, 700, 900},PINK, 100);
        tiles[15] = new Railroad("Pennsylvania Railroad", 200, 100, new int[]{25, 50, 100, 200});
        tiles[16] = new ColoredStreet("St. James Place", 180, 90, new int[]{14, 70, 200, 550, 750, 950},ORANGE,100);
        tiles[17] = new CommunityChest();
        tiles[18] = new ColoredStreet("Tennessee Avenue", 180, 90, new int[]{14,70,200,550,750,950}, ORANGE,100);
        tiles[19] = new ColoredStreet("New York Avenue", 200,100, new int[]{16,80,220,600,800,1000},ORANGE,100);
        tiles[20] = new Tile("Free Parking");
        tiles[21] = new ColoredStreet("Kentucky Avenue", 220,110, new int[]{18,90,250,700,875,1050},RED, 150);
        tiles[22] = new Chance();
        tiles[23] = new ColoredStreet("Indiana Avenue ", 220,110, new int[]{18,90,250,700,875,1050},RED,150);
        tiles[24] = new ColoredStreet("Illinois Avenue", 240,120, new int[]{20,100,300,750,925,1100},RED,150);
        tiles[25] = new Railroad("B&O Railroad", 200,100, new int[]{25,50,100,200});
        tiles[26] = new ColoredStreet("Atlantic Avenue", 260,130,new int[]{22,110,330,800,975,1150},YELLOW,150);
        tiles[27] = new ColoredStreet("Ventnor Avenue", 260,130, new int[]{22,110,330,800,975,1150},YELLOW,150);
        tiles[28] = new Utility("Water Works", 150,75);
        tiles[29] = new ColoredStreet("Marvin Gardens", 280,140,new int[]{24,120,360,850,1025,1200},YELLOW,150);
        tiles[30] = new Tile("Go To Jail");
        tiles[31] = new ColoredStreet("Pacific Avenue", 300,150,new int[]{26,130,390,900,1100,1275},GREEN,200);
        tiles[32] = new ColoredStreet("North Carolina Avenue", 300,150,new int[]{26,130,390,900,1100,1275},GREEN,200);
        tiles[33] = new CommunityChest();
        tiles[34] = new ColoredStreet("Pennsylvania Avenue", 320,160,new int[]{28,150,450,1000,1200,1400},GREEN,200);
        tiles[35] = new Railroad("Short Line", 200, 100, new int[] {25,50,100,200});
        tiles[36] = new Chance();
        tiles[37] = new ColoredStreet("Park Place", 350,175,new int[]{35,175,500,1100,1300,1500},DARK_BLUE,200);
        tiles[38] = new Tax("Luxury Tax",100);
        tiles[39] = new ColoredStreet("BoardWalk", 400,200,new int[]{50,200,600,1400,1700,2000},DARK_BLUE,200);
    }
    //MUTATORS ---------------------------------------------------------------

    /**
     * Starts the turn of the player who is next.
     * @param index the player who's turn it is
     */
    public void turn(int index){
        players[index].move(dice.roll());
        //DO REST WORK HERE
    }
    //GUI --------------------------------------------------------
    /**
     * Sets up the board panel of the GUI.
     */
    public ImageComponent setupBoardComponent() throws IOException {
        ImageComponent temp = new ImageComponent(players.length);
        temp.setBorder(new TitledBorder(new EtchedBorder(), "Monopoly"));
        for(int i = 0; i < players.length; i++){
            temp.toggleToken(i, true);
        }
        return temp;
    }

    /**
     * Get the JComponent of the board game.
     * @return the board component of the game.
     */
    public ImageComponent getBoardComponent(){
        return boardComponent;
    }


    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect(645,645,20,20);
        g2.setColor(Color.RED);
        //boardPanel.add(new ImageLabel(tokens[0]));
//        for(int i = 0; i < players.length;i++){
//            ImageIcon img = new ImageIcon(tokens[i]);//
//            g.drawImage(img.getImage(), 640,640, null);
//        }
    }
    //ACCESSORS---------------------------------------------------------------
    /**
     * Gets the tile at index spot.
     * @param index the tile location
     * @return the tile at index
     */
    public Tile getTile(int index){
        return tiles[index];
    }

    /**
     * Gets the players from the game.
     * @return the player objects
     */
    public Player[] getPlayers(){
        return players;
    }


    /**
     * Checks if the game is still in progress.
     * @return true if game is still on
     */
    public boolean gameRun(){
        int inGame = 0;
        for(Player player : players){
            if(!player.isBankrupt()) {
                inGame++;
            }
        }
        return inGame > 1;
    }

    /**
     * Checks if player is bankrupt.
     */
    public void checkBankrupt(){
        for(Player player : players){
            if(player.getMoneyDrawer().getTotal() <= 0) {
                player.setBankrupt(true);
            }
        }
    }
    /**
     * Checks if spot needs is owned and needs to be paid rent.
     * @param index player index, who's turn it is
     */
    public boolean checkPayRent(int index){
        int tileIndex = players[index].getTileIndex();
        if(tiles[tileIndex] instanceof Street tile){
            return tile.isStreetOwned() && tile.getOwner() != players[index];
        }
        return false;
    }

    /**
     * Checks to see if the player can buy the tile.
     * @param index the player index of who's turn it is
     * @return true if the player can buy the street
     */
    public boolean checkBuyStreet(int index){
        Tile tile = getTile(players[index].getTileIndex());
        //Checks to see if tile is not owned
        if(tile instanceof Street street){
            return !street.isStreetOwned() || street.getOwner().equals(players[index]);
        }
        return false;
    }


    /**
     * Checks if spot needs is owned and needs to be paid rent.
     * @param payer player, who's turn it is
     */
    public void payRent(Player payer){
        int tileIndex = payer.getTileIndex();
        Street tile = (Street)tiles[tileIndex];
        //Pay the owner money.
        int rent = tile.getRentPrices();
        //players[index].getMoneyDrawer().subtractMoney(rent);
        tile.getOwner().getMoneyDrawer().addMoney(payer.getMoneyDrawer().subtractMoney(rent));
    }



    /**
     * Action to buy the street on the tile.
     * @param index the player index
     * @param properties amount of properties to buy.
     */
    public boolean buyStreet(int index, int properties){
        Tile tile = getTile(players[index].getTileIndex());
        //Checks to see if tile is not owned
        if(tile instanceof Street street){
            if(!street.isStreetOwned()) {
                //prompt user if they want to buy street
                players[index].buyProperty(tile);
                return true;
            }
            //Checks to see if tile is owned and if it's not owned by current player
            else if(street.getOwner().equals(players[index])){
                int upgrade = street.getPropertyPrice();
                return street.addProperty(properties);
            }
        }
        return false;
    }

    // ACCESSORS --------------------------------------------------------------------------------

    /**
     * Gets a copy of the dice in the game.
     * @return the dice object.
     */
    public Dice getDice(){
        return dice;
    }

    /**
     * Get the board component of the board.
     */
    public JComponent getBoard(){return boardComponent;}

    /**
     * Gets the player assets from a player.
     * @param playerTurn which player according to turn.
     * @return a string containing a player's assets
     */
    public String getPlayerAssets(int playerTurn){
        Player player = players[playerTurn];
        String assets = "";
        assets += player + " assets:\nTitle Deeds: \n";
        //add the assets to assets.

        //Adds titledeeds
        ArrayList<Tile> titleDeeds = player.getTitleDeeds();
        for(Tile deed: titleDeeds) {
            assets += deed + "\n";
        }
        //Adds balance
        assets+= "Balance: $" + player.getMoneyDrawer() + "\n";
        return assets;
    }


    /**
     * Return the winner. Could use some mods.
     * @return the Player winner
     */
    public String returnWinner(){
        for(Player player: players){
            if(!player.isBankrupt()){
                return player.getName();
            }
        }
        return null;
    }

    //Testing tiles
    public static void main (String[] args){
//
//        Board board = new Board(1,0);
//
//        //Moves player 1 tile
//        board.players[0].move(1);
//
//        //Gets the tile object that the player is in
//        System.out.println(board.players[0].getTileIndex());
//        System.out.println(board.getTile(board.players[0].getTileIndex()));
//
//        //Player buys the street
//        board.players[0].buyStreet(board.getTile(board.players[0].getTileIndex()));
//
//        //Moves player 2 tile
//        board.players[0].move(2);
//
//        //Gets the tile object that the player is in
//        System.out.println(board.players[0].getTileIndex());
//        System.out.println(board.getTile(board.players[0].getTileIndex()));
//
//        //Player buys the street
//        board.players[0].buyStreet(board.getTile(board.players[0].getTileIndex()));
//
//        //Returns the players title deeds
//        System.out.println(board.players[0].getTitleDeeds());
//
//        //Returns true if player has all BROWN properties
//        System.out.println(board.players[0].ownsColorGroup(BROWN));
//
//board.players[0].buyProperty((Street) board.getTile(board.players[0].getTileIndex()), 1);
//



//        System.out.println(board.players[1].getTileIndex());

//       System.out.println(board.getTile(0)) ;


    }
}
