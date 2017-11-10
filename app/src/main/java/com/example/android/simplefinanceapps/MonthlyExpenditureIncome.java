package com.example.android.simplefinanceapps;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class MonthlyExpenditureIncome {
    int income, expenditure, year, date;
    String month;


    public MonthlyExpenditureIncome(int date, String month, int year, int income, int expenditure )
    {
        this.month = month;
        this.year = year;
        this.date = date;
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

    public int getYear()
    {
        return year;
    }

    public int getDate()
    {
        return date;
    }

    public String getMonth () {
        return month;
    }
}
