package Board;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.io.IOException;
/**
 * An image JPanel background.
 */
public class ImageComponent extends JComponent {
    private final static String BOARD_FILE = "boardImage2.jpg";
    private final static String TOKEN0 = "carToken.png";
    private final static String TOKEN1 = "hatToken.png";
    private final static String TOKEN2 = "catToken.png";
    private final static String TOKEN3 = "shoeToken.png";
    private final static String CARD = "chanceImg.jpg";
    private final static int xTiles[] = {603,539,484,432,380,325,267,222,159,113,23,40,40,40,40,40,40,40,40,40,45,
    111,167,219,270,322,375,430,485,536, 605,605,605,605,605,605,605,605,605,605,605};
    private final static int yTiles[] = {601,605,605,605,605,605,605,605,605,605,631,527,484,428,375,324,273,217,160,108,
            43,36,36,36,36,36,36,36,36,37,40,109,161,213,268,325,375,432,479,540};

    private ImageIcon board;
    private ImageIcon[] tokens;
    private ImageIcon card;
    private int[] xTile;
    private int[] yTile;
    private int activePlayers;


    /**
     * Paint the background image.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Draw background.
        g2.drawImage(board.getImage(), 0,0,null);
        for(int i = 0; i < activePlayers;i++){
            if(tokens[i] != null)
                g2.drawImage(tokens[i].getImage(), xTile[i],yTile[i],null);
        }
        if(card != null)
            g2.drawImage(card.getImage(),360,356,null);
        //RESIZE which works.
//        token = new ImageIcon(token.getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH));
//        g2.drawImage(token.getImage(),400,400, null);
    }

    // CONSTRUCTOR
    public ImageComponent(int playersActive) throws IOException {
        activePlayers = playersActive;
        board = new ImageIcon(BOARD_FILE);
        board = new ImageIcon(board.getImage().getScaledInstance(650,650, Image.SCALE_SMOOTH));
        tokens = new ImageIcon[playersActive];
        xTile = new int[playersActive];
        yTile = new int[playersActive];
        for(int i = 0; i < tokens.length; i++){
            tokens[i] = new ImageIcon(setupToken(i).getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
            xTile[i] = 603;
            yTile[i] = 601 - 3;
        }
        card = null;
        int width = board.getIconWidth();
        int height = board.getIconHeight();

        setSize(width, height);
    }

    /**
     * Setup the tokens for the players
     * @param index tokenIndex
     * @return the image of the token of the player.
     */
    public ImageIcon setupToken(int index){
        if(index == 0){
            return new ImageIcon(TOKEN0);
        }
        else if(index ==1){
            return new ImageIcon(TOKEN1);
        }
        else if(index == 2){
            return new ImageIcon(TOKEN2);
        }
        else{//index == 3
            return new ImageIcon(TOKEN3);
        }
    }


    /**
     * Turns on or off the board
     * @param status the status of board
     */
    public void toggleBoard(Boolean status) throws IOException {
        board = status ? new ImageIcon(BOARD_FILE) : null;
    }

    /**
     * Turns on or off the token0
     * @param tokenIndex the index of the token selected
     * @param status the status of card
     */
    public void toggleToken(int tokenIndex, Boolean status) throws IOException{
        tokens[tokenIndex] = status ? new ImageIcon(
                setupToken(tokenIndex).getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)) : null;
    }

    /**
     * Sets the tile indexes for drawing it.
     * @param tileIndex the tile index
     * @param playerIndex the player being moved
     */
    public void setTileIndex(int tileIndex, int playerIndex)throws IOException{
        xTile[playerIndex] = xTiles[tileIndex];
        yTile[playerIndex] = yTiles[tileIndex - 3];
        if(tileIndex == 7 || tileIndex == 22 || tileIndex == 36 ||
                tileIndex == 2 || tileIndex == 17 || tileIndex == 33){
            toggleCard(true);
        }
        toggleCard(false);
    }

    /**
     * Turns on or off the card.
     * @param status the status of card
     */
    public void toggleCard(Boolean status) throws IOException{
        card = status ? new ImageIcon(
                new ImageIcon(CARD).getImage().getScaledInstance(125,166,Image.SCALE_SMOOTH)) : null;

    }


}
