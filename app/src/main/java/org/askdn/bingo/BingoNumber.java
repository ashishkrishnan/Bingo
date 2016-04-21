package org.askdn.bingo;

/**
 * Created by ashish on 15/4/16.
 */
public class BingoNumber {

    int number;
    boolean selected = false;

    BingoNumber(int num, boolean selected) {
       this.number = num;
        this.selected = true;
    }

    BingoNumber(int number) {
        this.number = number;
    }



    public int getNumber() {
        return number;
    }

    public boolean isSelected() {
        return selected;
    }
}
