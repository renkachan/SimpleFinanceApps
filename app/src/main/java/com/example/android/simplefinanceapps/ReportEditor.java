package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by robert.arifin on 09/11/2017.
 */

public class ReportEditor extends AppCompatActivity {
    ArrayList<MonthlyExpenditureIncome> reports = new ArrayList<>();
    ReportEditorAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.layout_editing_exp_income);
        reports = retrieveReportFromDb();
        adapter = new ReportEditorAdapter(
                this, R.layout.layout_blueprint_editing_exp_income, reports);
        listView = (ListView) findViewById(R.id.dailyReport);
        listView.setAdapter(adapter);
    }

    private ArrayList<MonthlyExpenditureIncome> retrieveReportFromDb() {
        ArrayList<MonthlyExpenditureIncome>  reportFromDb = new ArrayList<>();
        DBHandler handler = new DBHandler(this);
        return DBContract.TABLE_EXPINCOME.getData(handler.getWritableDatabase());
    }

    public void backToMainMenu(View v)  {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
