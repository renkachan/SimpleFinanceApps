package com.example.android.simplefinanceapps;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class MonthlyExpenditureIncome {
    int amount;
    long date;
    String category;


    public MonthlyExpenditureIncome(long date, int amount, String category)
    {
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getDate()
    {
        return date;
    }


    public String getCategory () {
        return category;
    }
}
