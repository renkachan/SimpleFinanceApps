package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class ReportGenerator extends AppCompatActivity {
    ArrayList<MonthlyExpenditureIncome> monthlyReport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.layout_report);
        monthlyReport = retrieveReportFromDb();
        ReportGeneratorAdapter adapter = new ReportGeneratorAdapter(
                this, R.layout.layout_blueprint_report, monthlyReport);
        ListView listView = (ListView) findViewById(R.id.monthlyReport);
        listView.setAdapter(adapter);
    }

    private ArrayList<MonthlyExpenditureIncome> retrieveReportFromDb() {
        ArrayList<MonthlyExpenditureIncome>  reportFromDb = new ArrayList<>();
        DBHandler handler = new DBHandler(this);
        reportFromDb = DBContract.TABLE_EXPINCOME.getData(handler.getWritableDatabase());
        handler.close();
        return reportFromDb;
    }

    public void backToMainMenu(View v)  {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
