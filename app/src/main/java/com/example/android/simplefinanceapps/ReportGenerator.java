package com.example.android.simplefinanceapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class ReportGenerator extends AppCompatActivity {
    ArrayList<FinanceModel> monthlyReport = new ArrayList<>();
    GraphView graph;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.layout_report_chart);
        generateGraph();


    }

    private void generateGraph () {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        monthlyReport = sqLiteHelper.getAllRecords();
        int size = 0;
        int expSize = 0;

        for ( int i = 0; i < monthlyReport.size(); i ++) {
            if (monthlyReport.get(i).getCategory().equals("INCOME")) {
               size++;
            }
        }

        DataPoint[] incomeDataPoints = new DataPoint[size];
        int x = 0;

        for ( int i = 0; i < monthlyReport.size(); i ++) {
            Date d = new Date(monthlyReport.get(i).getDate());
            if (monthlyReport.get(i).getCategory().equals("INCOME")) {
                incomeDataPoints[x] = new DataPoint(d, monthlyReport.get(i).getAmount());
                x++;
            }
        }

        x = 0;

        for ( int i = 0; i < monthlyReport.size(); i ++) {
            if (monthlyReport.get(i).getCategory().equals("EXPENDITURE")) {
                expSize++;
            }
        }

        DataPoint[] expDataPoints = new DataPoint[expSize];

        for ( int i = 0; i < monthlyReport.size(); i ++) {
            Date d = new Date(monthlyReport.get(i).getDate());
            if (monthlyReport.get(i).getCategory().equals("EXPENDITURE")) {
                expDataPoints[x] = new DataPoint(d, monthlyReport.get(i).getAmount());
                x++;
            }
        }

        graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> incomeSeries = new LineGraphSeries<>(incomeDataPoints);
        LineGraphSeries<DataPoint> expSeries = new LineGraphSeries<>(expDataPoints);

        graph.addSeries(incomeSeries);
        graph.addSeries(expSeries);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(ReportGenerator.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        Calendar c = Calendar.getInstance();
        Date max = c.getTime();
        Date min = new Date(monthlyReport.get(0).getDate());

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(min.getTime());
        graph.getViewport().setMaxX(max.getTime());

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(5000000);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        incomeSeries.setColor(Color.GREEN);
        expSeries.setColor(Color.RED);
        incomeSeries.setTitle("income");
        expSeries.setTitle("expenditure");
        graph.getLegendRenderer().setVisible(true);
    }
}
