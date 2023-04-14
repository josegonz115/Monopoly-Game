package Tiles;
import Player.Player;
/**
 * A tax tile.
 */
public class Tax extends Tile{
    private int taxAmount;

    /**
     * Constructs a tax tile.
     * @param name the name.
     * @param taxPrice the price to be taxed.
     */
    public Tax(String name, int taxPrice){
        super(name);
        taxAmount = taxPrice;
    }

    /**
     * Tax away money from the player.
     * @param player player whose money is getting taxed.
     */
    public void payTax(Player player){
       player.getMoneyDrawer().subtractMoney(taxAmount);
    }
}
