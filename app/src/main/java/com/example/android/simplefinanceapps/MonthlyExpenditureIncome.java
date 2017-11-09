package com.example.android.simplefinanceapps;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class MonthlyExpenditureIncome {
    int income, expenditure, year, dateOfMonth;
    String month;


    public MonthlyExpenditureIncome(String month,int dateOfMonth, int year, int income, int expenditure )
    {
        this.month = month;
        this.year = year;
        this.dateOfMonth = dateOfMonth;
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

    public int getDateOfMonth()
    {
        return dateOfMonth;
    }

    public String getMonth () {
        return month;
    }
}
