//package com.example.android.simplefinanceapps;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
///**
// * Created by robert.arifin on 09/10/2017.
// */
//
//public class ReportGenerator extends AppCompatActivity {
//    ArrayList<FinanceModel> monthlyReport = new ArrayList<>();
//    ReportGeneratorAdapter adapter;
//    ListView listView;
//
//    @Override
//    protected void onCreate(Bundle savedInstantState) {
//        super.onCreate(savedInstantState);
//        setContentView(R.layout.layout_report);
//        monthlyReport = retrieveReportFromDb();
//
//        adapter = new ReportGeneratorAdapter(
//                this, R.layout.layout_blueprint_report, monthlyReport);
//        listView = (ListView) findViewById(R.id.monthlyReport);
//        listView.setAdapter(adapter);
//    }
//
//    private ArrayList<FinanceModel> retrieveReportFromDb() {
//        ArrayList<FinanceModel>  reportFromDb = new ArrayList<>();
//        SQLiteHelper handler = new SQLiteHelper(this);
//        reportFromDb = DBContract.TABLE_EXPINCOME.getMonthlyData(handler.getWritableDatabase());
//        handler.close();
//
//        return reportFromDb;
//    }
//
//    public void backToMainMenu(View v)  {
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//    }
//
//    public void deleteAllReport(View v) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete All Reports")
//                .setMessage("Are you sure want to delete all the reports?")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SQLiteHelper handler = new SQLiteHelper(ReportGenerator.this);
//                        DBContract.TABLE_EXPINCOME.deleteTable(handler.getWritableDatabase());
//                        handler.close();
//                        monthlyReport.clear();
//                        adapter = new ReportGeneratorAdapter(
//                                ReportGenerator.this, R.layout.layout_blueprint_report
//                                , monthlyReport);
//                        listView = (ListView) findViewById(R.id.monthlyReport);
//                        listView.setAdapter(adapter);
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
//}
