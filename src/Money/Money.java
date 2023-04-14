package Money;

/**
    A money bill.
 */
public class Money {
    private int value;

    /**
        Constructs the money bill with a value.
        @param value th value of the bill
     */
    public Money(int value){
        this.value = value;
    }

    /**
        Returns the value of the money bill.
        @return the value
     */
    public int getValue(){
        return value;
    }

    /**
        Compares equality of bill with another bill.
        @param otherObject another potential bill
     */
    public boolean equals(Object otherObject){
        if(otherObject instanceof Money){
            Money other = (Money)otherObject;
            return value == other.value;
        }
        return false;
    }

    /**
        Checks if bill is greater than another bill.
        @param rhs another bill
     */
    public boolean greaterThan(Money rhs){

        return value > rhs.value;
    }

    /**
        Checks if bill is less than another bill.
        @param rhs another bill
     */
    public boolean lessThan(Money rhs){

        return value < rhs.value;
    }

    /*
        A string representation of the money.
     */
    public String toString(){
        return value + "";
    }
}
