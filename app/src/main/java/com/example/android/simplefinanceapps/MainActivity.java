package com.example.android.simplefinanceapps;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openIncome (View v) {
        Intent i = new Intent(this, IncomeRecorder.class);
        startActivity(i);
    }

//    public void openExpenditure (View v) {
//        Intent i = new Intent(this, ExpenditureRecorder.class);
//        startActivity(i);
//    }

//    public void openReport(View v) {
//        Intent i = new Intent(this, ReportGenerator.class);
//        startActivity(i);
//    }

    public void openEdit(View v) {
        Intent i = new Intent(this, ReportEditor.class);
        startActivity(i);
    }
}
