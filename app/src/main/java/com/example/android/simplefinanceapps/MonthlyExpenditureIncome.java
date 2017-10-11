package com.example.android.simplefinanceapps;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class MonthlyExpenditureIncome {
    int income;
    int expenditure;
    String month;

    public MonthlyExpenditureIncome(String month, int income, int expenditure )
    {
        this.month = month;
        this.income = income;
        this.expenditure = expenditure;
    }

    public int getExpenditure()
    {
        return expenditure;
    }

    public int getIncome()
    {
        return income;
    }

    public String getMonth () {
        return month;
    }
}
