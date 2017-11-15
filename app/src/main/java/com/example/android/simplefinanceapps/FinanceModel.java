package com.example.android.simplefinanceapps;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class FinanceModel {
    int amount, ID;
    long date;
    String  category;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getID() {
        return ID;
    }

    public long getDate()
    {
        return date;
    }

    public String getCategory () {
        return category;
    }
}
