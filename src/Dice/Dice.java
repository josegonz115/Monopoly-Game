package Dice;

import java.util.Random;
//TEST FINISHED
/**
 * A pair of 6-sixed dice.
 */
public class Dice {
    int dice1;
    int dice2;
    Random rand;
    int lastRoll;

    /**
     * Constructs a dice object.
     */
    public Dice(){
        dice1 = 0;
        dice2 = 0;
        rand = new Random();
        lastRoll = 0;
    }

    /**
     * Rolls the pair of dice.
     * @return the results of the roll.
     */
    public int roll(){
        dice1 = 1 + rand.nextInt(6);
        dice2 = 1 + rand.nextInt(6);
        lastRoll = dice1 + dice2;
        return lastRoll;
    }

    /**
     * Gets the result from last roll.
     * @return the amount from last roll
     */
    public int getLastRoll(){ return lastRoll; }

    /**
     * Checks if the pair of dice is a double.
     * @return true if double was rolled
     */
    public boolean isDouble(){ return dice1 == dice2; }

    //TEST RUN
//    public static void main(String[] args){
//        Dice dice1 = new Dice();
//        System.out.println(dice1.roll());
//        if(dice1.isDouble()){
//            System.out.println("IS A DOUBLE");
//        }
//        System.out.println(dice1.getLastRoll() +" -> "+ dice1.roll());
//        if(dice1.isDouble()){
//            System.out.println("IS A DOUBLE");
//        }
//    }
}
