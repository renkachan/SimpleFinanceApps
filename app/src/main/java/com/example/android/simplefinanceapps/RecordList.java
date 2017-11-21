package com.example.android.simplefinanceapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by robert.arifin on 09/11/2017.
 */

public class RecordList extends AppCompatActivity {
    ArrayList<FinanceModel> reports = new ArrayList<>();
    RecordListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.layout_record);
        retrieveReportFromDb();

    }

    private void retrieveReportFromDb() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

        adapter = new RecordListAdapter(
                this, R.layout.layout_blueprint_record,
                sqLiteHelper.getAllRecords());

        listView = (ListView) findViewById(R.id.dailyReport);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FinanceModel item  = (FinanceModel) parent.getItemAtPosition(position);

                Intent i = new Intent(RecordList.this, RecordEditor.class);
                i.putExtra("ID", item.getID());
                i.putExtra("date", item.getDate());
                i.putExtra("category", item.getCategory());
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View arg1,
                                           int position, long id) {

                final FinanceModel item = (FinanceModel) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordList.this);
                builder.setTitle("Delete record")
                        .setMessage("Delete this record?")
                        .setPositiveButton(android.R.string.yes, new
                                DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FinanceModel record = new FinanceModel();
                                record.setID(item.getID());
                                SQLiteHelper sqLiteHelper = new SQLiteHelper
                                        (RecordList.this);
                                sqLiteHelper.deleteRecord(record);
                                retrieveReportFromDb();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;
            }
        });
    }

    public void backToMainMenu(View v)  {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
