import Board.Board;
import Dice.Dice;
import Player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * The monopoly game.
 */
public class Game extends JFrame{
    //Instance Variables of Game Info
    private int playerAmount;
    private int playerSpots;
    private int botAmount;
    private Board board;
    private Player currentPlayer;
    private int playerTurn;

    //private int currentPlayer;

    private boolean gameFinished;
    private boolean turnFinished;



    //Instance Variables of GUI
    private static final int FRAME_WIDTH = 1170;
    private static final int FRAME_HEIGHT = 690;
    private JComponent boardComponent;
    private ImageIcon monoBoard;

    private JPanel assetPanel;
    private JTextArea playerAssets;
    private JTextArea infoTurn;

    private JButton rollDiceBtn;
    private JButton buyBtn;
    private JButton payRentBtn;
    private JButton nextTurnBtn;

    /**
     * Constructs the Monopoly game!
     * @param players the players in game
     * @param playerNames the names of the players
     * @param bots how many players are ai controlled
     */
    public Game(int players, String[] playerNames, int bots){
        setup(players,playerNames,bots);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
    }

    //MUTATORS___HELPERS----------------------------------------------------------------
    /**
     * Determine the first person to start the game.
     */
    public void decideStartPlayer(){
        Dice temp = new Dice();
        int[]diceRolls = new int[playerAmount];
        diceRolls[0] = temp.roll();
        int max = 0;
        for(int i = 1; i < playerAmount; i++){
            diceRolls[i] = temp.roll();
            if(diceRolls[i] > diceRolls[max]){
                max = i;
            }
        }
        playerTurn = max;
    }

    /**
     * Setups the game's variables and GUI.
     * @param players the players in game
     * @param playerNames the names of the players
     * @param bots how many players are ai controlled
     */
    private void setup(int players, String[] playerNames, int bots) {
        try {
            playerAmount = players;
            botAmount = bots;
            playerTurn = 0;
            turnFinished = false;
            decideStartPlayer();
            board = new Board(players,playerNames, bots); //most important class!
            currentPlayer = board.getPlayers()[playerTurn];

            boardComponent = board.getBoardComponent();
            assetPanel = setupAssetPanel();
            JPanel buttons = setupButtonsPanel();
            assetPanel.add(buttons);

            add(boardComponent, BorderLayout.CENTER);
            add(assetPanel, BorderLayout.EAST);
        }
        catch(IOException exception){
            System.out.println("Mistake loading an image in Board/BoardComponent\nCheck it out.");
        }

//        CardComponent chance = new CardComponent(500,250,"chanceImg.jpg");
//        add(chance);
    }

    private JPanel setupAssetPanel(){
        final int TEXT_WIDTH = 10;
        final int TEXT_HEIGHT = 5;

        JPanel temp = new JPanel(new GridLayout(3,1));

        //Set up player assets box.
        playerAssets = new JTextArea(TEXT_WIDTH,TEXT_HEIGHT);
        playerAssets.setBackground(Color.YELLOW);
        displayAssets();
//        playerAssets.append("Player " + (playerTurn + 1) + " assets:\n\n");
//        playerAssets.append("Current Balance: 1500\n");
//        playerAssets.append("Title Deeds: \n");
        playerAssets.setEditable(false);
        JScrollPane scrollPlayer1Info = new JScrollPane(playerAssets);

        //Set up game information box.
        infoTurn = new JTextArea(TEXT_WIDTH,TEXT_HEIGHT);
        infoTurn.setText(currentPlayer + " starts the game.\n");
        infoTurn.setEditable(false);

        //Add JTextAreas to JPanel
        temp.add(scrollPlayer1Info);
        temp.add(infoTurn);
        return temp;
    }

    public class RollBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            try{
                if(rollDiceBtn.isEnabled()){
                    rollDice();
                    rollDiceBtn.setEnabled(false);
                    processTurn();
                    board.getBoardComponent().repaint();
                }
            }
            catch (IOException e) {
                System.out.println("Error: Images not loading for the tokens!");
            }
        }
    }

    public class BuyBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(buyBtn.isEnabled()){
                displayBuyInfo();
                board.getBoardComponent().repaint();
                int tileIndex = currentPlayer.getTileIndex();
                if(board.getTile(tileIndex).getMaxProperty()){
                    buyBtn.setEnabled(false);
                }
            }
        }
    }
    public class RentBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(payRentBtn.isEnabled()){
                //DO SOMETHING
                board.payRent(currentPlayer);
                int tileIndex = currentPlayer.getTileIndex();
                infoTurn.append(currentPlayer + "has paid their rent to " + board.getTile(tileIndex).getOwner());
                displayAssets();
                //turnFinished = true;
                payRentBtn.setEnabled(false);
                nextTurnBtn.setEnabled(true);
                board.getBoardComponent().repaint();
            }
        }
    }

    public class NextTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(nextTurnBtn.isEnabled()){
                finishTurn();
                displayAssets();
                board.checkBankrupt();
                if(isGameRunning()){
                    rollDiceBtn.setEnabled(true);
                }
                else{
                    displayWinner();
                }
                board.getBoardComponent().repaint();
            }
        }
    }

    /**
     * Sets up the buttons for the Monopoly game.
     * @return a JPanel with the four buttons.
     */
    private JPanel setupButtonsPanel(){
        //Set up four buttons.
        JPanel btnPanel = new JPanel(new GridLayout(1,3));
        //Set up the roll dice button.
        rollDiceBtn = new JButton("Roll Dice");
        //Setup buy and pay rent on same line.
        JPanel buyAndRent = new JPanel(new GridLayout(1,2));

        buyBtn = new JButton("Buy");
        buyBtn.setEnabled(false);

        payRentBtn = new JButton("Pay Rent");
        payRentBtn.setEnabled(false);
        buyAndRent.add(buyBtn);
        buyAndRent.add(payRentBtn);

        //Setup the next turn button.
        nextTurnBtn = new JButton("Next Turn");
        nextTurnBtn.setEnabled(false);
        //Finally add all buttons to btnPanel.
        btnPanel.add(rollDiceBtn);
        btnPanel.add(buyAndRent);
        btnPanel.add(nextTurnBtn);

        //Add the action listeners.
        rollDiceBtn.addActionListener(new RollBtnListener());
        buyBtn.addActionListener(new BuyBtnListener());
        payRentBtn.addActionListener(new RentBtnListener());
        nextTurnBtn.addActionListener(new NextTurnListener());
        return btnPanel;
    }


    /**
     * A turn in the monopoly game.
     */
    public void rollDice()throws IOException {
        rollDiceBtn.setEnabled(true);
        turnFinished = false;
        board.turn(playerTurn);
        //Display on screen
        infoTurn.setText(currentPlayer + " rolled a " + board.getDice().getLastRoll() + "\n");
        int tileIndex = currentPlayer.getTileIndex();
        infoTurn.append(currentPlayer + " landed on " + board.getTile(tileIndex).getName() + "\n");
        board.getBoardComponent().setTileIndex(tileIndex, playerTurn);
    }


    /**
     * Processes one turn of the Monopoly game.
     */
    public void processTurn(){
        if(board.checkBuyStreet(playerTurn)){
            buyBtn.setEnabled(true);
            nextTurnBtn.setEnabled(true);
        }
        else if(board.checkPayRent(playerTurn)){
            payRentBtn.setEnabled(true);
            buyBtn.setEnabled(false);
            nextTurnBtn.setEnabled(false);
        }
        else if (!board.checkPayRent(playerTurn)){
            turnFinished = true;
            nextTurnBtn.setEnabled(true);
        }
    }

    /**
     * End the turn for the round.
     */
    public void finishTurn(){
        buyBtn.setEnabled(false);
        payRentBtn.setEnabled(false);
        rollDiceBtn.setEnabled(false);
        nextTurnBtn.setEnabled(false);
        turnFinished = true;
        infoTurn.setText(currentPlayer + "'s turn is over.\n");
        nextPlayer();
        if(isGameRunning()){
            while(currentPlayer.isBankrupt() || currentPlayer.getJailed()){
                if(currentPlayer.getJailed()){
                    infoTurn.append(currentPlayer + " has been jailed for " + currentPlayer.getJailTurns() + "\n");
                }
                else if(currentPlayer.isBankrupt()){
                    infoTurn.append(currentPlayer + " has been bankrupted!\n");
                }
                nextPlayer();
            }
            infoTurn.append("It is " + currentPlayer + "'s turn\n");
        }
    }

    /**
     * Iterates to the next player in line.
     */
    public void nextPlayer(){
        playerTurn++;
        if(playerTurn >= playerAmount){
            playerTurn = 0;
        }
        currentPlayer = board.getPlayers()[playerTurn];
    }

    /**
     * Checks to see if game is running.
     * @return true if game is still in session.
     */
    public boolean isGameRunning(){
        return board.gameRun();
    }

    /**
     * Display assets on JTextAreaPanel on each turn.
     */
    public void displayAssets(){
        playerAssets.setText(board.getPlayerAssets(playerTurn));
    }

    /**
     * Displays information about the current buying transaction.
     */
    public void displayBuyInfo(){
        int tileIndex = board.getPlayers()[playerTurn].getTileIndex();
        if(board.buyStreet(playerTurn, 1)){
            infoTurn.setText(currentPlayer + " bought " + board.getTile(tileIndex).getProperties() + " property on " +
                    board.getTile(tileIndex).getName());
        }
        else{
            infoTurn.setText(currentPlayer + " could not buy a property on " +
                    board.getTile(tileIndex).getName());
        }
        displayAssets();
    }

    /**
     * Obtains the status of the current player in turn.
     * @return true if player is still playing.
     */
    public boolean checkPlayerStatus(){
        return !(currentPlayer.getJailed() ||
                currentPlayer.isBankrupt());
    }


    /**Displays the winner on the JText Area
     *
     */
    public void displayWinner(){
        infoTurn.setText(board.returnWinner() + " has won the game!!!");
    }

    /**
     * Starts the game.
     * @param args none will be used.
     */
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        System.out.print("Enter amount of players: ");
        int playerAmt = console.nextInt();
        String[] playerNames = new String[playerAmt];
        //System.out.print("Enter bot amount: ");
        //int bots = console.nextInt();
        int bots = 0;
        console.nextLine();
        for(int i = 0; i < playerAmt; i++){
            System.out.print("Enter player " + (i+1) + "'s name: ");
            playerNames[i] = console.nextLine();
        }

        Game monopoly = new Game(playerAmt,playerNames,bots);


        monopoly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        monopoly.setVisible(true);
        monopoly.setTitle("MONOPOLY");
    }
}