package Money;
import java.util.ArrayList;
//TEST RUN COMPLETE
/**
 A money drawer that holds bills.
 */
public class MoneyDrawer {
    private ArrayList<Money> bills;
    /**
     Constructs the MoneyDrawer with $1500 worth of bills.
     */
    public MoneyDrawer(){
        bills = new ArrayList<Money>();
        for(int i = 0; i < 2; i++){
            bills.add(new FiveHundred());
            bills.add(new Hundred());
            bills.add(new Fifty());
        }
        for(int i = 0; i < 6; i++){
            bills.add(new Twenty());
        }
        for(int i = 0; i < 5; i++){
            bills.add(new Ten());
            bills.add(new Five());
            bills.add(new One());
        }
        sort();
    }

    /**
     * Breaks down the single bill into multiple bills of lower value.
     * @param bill the bill to be broken.
     */
    private void breakUp(Money bill){
        ArrayList<Money> temp = new ArrayList<Money>();
        if(bill instanceof FiveHundred){
            for(int i = 0; i < 5; i++){
                bills.add(new Hundred());
            }
        }
        else if(bill instanceof Hundred){
            for(int i = 0; i < 2; i++){
                bills.add(new Fifty());
            }
        }
        else if(bill instanceof Fifty){
            for(int i = 0; i < 5; i++){
                bills.add(new Ten());
            }
        }
        else if(bill instanceof Twenty){
            for(int i = 0; i < 4; i++){
                bills.add(new Five());
            }
        }
        else if(bill instanceof Ten){
            for(int i = 0; i < 2; i++){
                bills.add(new Five());
            }
        }
        else if(bill instanceof Five){
            for(int i = 0; i < 5; i++){
                bills.add(new One());
            }
        }
        else { //bill instance of one
            bills.add(new One());
        }
        bills.remove(bill);
        sort();
    }

    /**
     * Ensures bill memory location will not be transferred.
     * @param bill the money object
     * @return a new money object of same value.
     */
    private Money copy(Money bill){
        Money temp;
        if(bill instanceof FiveHundred){
            temp = new FiveHundred();
        }
        else if(bill instanceof Hundred){
            temp = new Hundred();
        }
        else if(bill instanceof Fifty){
            temp = new Fifty();
        }
        else if(bill instanceof Twenty){
            temp = new Twenty();
        }
        else if(bill instanceof Ten){
            temp = new Ten();
        }
        else if(bill instanceof Five){
            temp = new Five();
        }
        else { //bill instance of one
            temp = new One();
        }
        return temp;
    }


    /**
     * Sorts the cash in the ArrayList.
     */
    private void sort(){
        for(int i = 0; i < bills.size(); i++){
            Money bill1 = bills.get(i);
            Money min = bill1;
            int minValue = bill1.getValue();
            int minIndex = i;
            for(int j = i+1; j < bills.size(); j++){
                Money bill2 = bills.get(j);
                int bill2Value = bill2.getValue();
                if(bill2Value < minValue){
                    min = bill2;
                    minValue = bill2Value;
                    minIndex = j;
                }
            }
            //Swapping.
            Money temp = bill1;
            bills.set(i, min);
            bills.set(minIndex, temp);
        }
    }

    /**
        Adds bills to drawer.
        @param bills the bill that is being added
     */
    public void addMoney(ArrayList<Money> bills){
        this.bills.addAll(bills);
        sort();
    }

    /**
     * Adds bills to drawer.
     * @param amount the amount of bills that should be added.
     */
    public void addMoney(int amount){
        int remain = amount;
        //ArrayList<Money> temp = new ArrayList<Money>();
        while(remain > 0){
            if(remain >= FiveHundred.FIVE_HUNDRED){
                bills.add(new FiveHundred());
                remain -= FiveHundred.FIVE_HUNDRED;
            }
            else if(remain >= Hundred.HUNDRED){
                bills.add(new Hundred());
                remain -= Hundred.HUNDRED;
            }
            else if(remain >= Fifty.FIFTY){
                bills.add(new Fifty());
                remain -= Fifty.FIFTY;
            }
            else if(remain >= Twenty.TWENTY){
                bills.add(new Twenty());
                remain -= Twenty.TWENTY;
            }
            else if(remain >= Ten.TEN){
                bills.add(new Ten());
                remain -= Ten.TEN;
            }
            else if(remain >= Five.FIVE){
                bills.add(new Five());
                remain -= Five.FIVE;
            }
            else{ //remain >= One.ONE
                bills.add(new One());
                remain -= One.ONE;
            }
        }
        sort();
        //addMoney(temp);
    }

    /**
        Subtracts all bills from drawer.
        @return the money subtracted
     */
    public ArrayList<Money> subtractAllMoney(){
        ArrayList<Money> temp = new ArrayList<Money>();
        while(!bills.isEmpty()){
           temp.add(copy(bills.get(0)));
           bills.remove(0);
        }
        return temp;
    }



    /**
     * Subtracts bills from drawer.
     * @param amount the amount of money to be removed
     * @return the money subtracted
     */
    public ArrayList<Money> subtractMoney(int amount){
        if(amount > getTotal()){
            subtractAllMoney();
            return null;
        }
        int amountCopy = amount;
        int total = 0;
        int i = 0;
        ArrayList<Money> temp = new ArrayList<Money>();
        while(total < amount){
            Money bill = bills.get(i);
            int value = bill.getValue();
            if(value + total <= amount){
                temp.add(copy(bill));
                bills.remove(bill);
                total += value;
            }
            else{//(bills.size() == 1)
                breakUp(bill);
            }
            //else{i++;}
        }
        sort();
        return temp;
    }

    /**
     Gets the bills from the drawer.
     */
    public ArrayList<Money> getMoney(){
        return bills;
    }

    /**
        Gets the total amount of the bills in the drawer.
     */
    public int getTotal(){
        int total = 0;
        for(Money bill : bills){
            total += bill.getValue();
        }
        return total;
    }

    /**
     * String version of getTotal()
     * @return total
     */
    public String toString(){
        return getTotal() + "";
    }

    //Test run:
    public static void main(String[] args) {
        MoneyDrawer drawer1 = new MoneyDrawer();
        MoneyDrawer drawer2 = new MoneyDrawer();

        System.out.println(drawer1.getTotal());
        System.out.println(drawer2.getTotal());

        System.out.println(drawer1.getMoney());

        int bonus = 200;
        int tax = 400;

        drawer1.addMoney(bonus);
        System.out.println(drawer1.getTotal());


        drawer1.addMoney(drawer2.subtractAllMoney());
        System.out.println(drawer1.getTotal());
        System.out.println(drawer2.getTotal());

        System.out.println(drawer1.getMoney());
        drawer1.subtractMoney(3179); // TEST OUT subtractMoney(int amount) again!
        System.out.println(drawer1.getTotal());
        System.out.println(drawer1.getMoney());
//        if(drawer1.subtractMoney(200) == null){
//            System.out.println("NOT ENOUGH MONEY");
//        }

        //public void addMoney(ArrayList<Money> bills);     GOOD
        //public void addMoney(int amount);                 GOOD
        //public ArrayList<Money> subtractAllMoney(int amount)          GOOD
        //public ArrayList<Money> subtractMoney(int amount)             GOOD
        //public ArrayList<Money> getMoney()                GOOD
        //public int getTotal()                             GOOD
    }
}
