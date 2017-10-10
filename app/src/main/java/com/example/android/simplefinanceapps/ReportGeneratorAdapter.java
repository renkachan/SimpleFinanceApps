package com.example.android.simplefinanceapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.arifin on 10/10/2017.
 */

public class ReportGeneratorAdapter extends ArrayAdapter<MonthlyExpenditureIncome> {
    private ArrayList<MonthlyExpenditureIncome> monthlyData = new ArrayList<>();

    private static class ViewHolder {
        private TextView month, income, expenditure;

    }

    public ReportGeneratorAdapter(Context context, int resource, ArrayList<MonthlyExpenditureIncome> item)
    {
        super(context, resource, item);
        monthlyData = item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_blueprint_report, null);
            viewHolder = new ViewHolder();
            viewHolder.month = (TextView)convertView.findViewById(R.id.selectedMonth);
            viewHolder.income = (TextView) convertView.findViewById(R.id.bluePrint_incomeValue);
            viewHolder.expenditure = (TextView) convertView.findViewById(R.id.bluePrint_expValue);

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.selectedMonth, viewHolder.month);
            convertView.setTag(R.id.bluePrint_incomeValue, viewHolder.income);
            convertView.setTag(R.id.bluePrint_expValue, viewHolder.expenditure);
        }
        else    {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.month.setText(monthlyData.get(position).getMonth());
        viewHolder.income.setText(String.format("%d", monthlyData.get(position).getIncome()));
        viewHolder.expenditure.setText(String.format("%d", monthlyData.get(position).getExpenditure()));

        return convertView;
    }
}
