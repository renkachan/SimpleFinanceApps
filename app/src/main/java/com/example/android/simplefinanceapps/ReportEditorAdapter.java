package com.example.android.simplefinanceapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robert.arifin on 09/11/2017.
 */

public class ReportEditorAdapter extends ArrayAdapter<MonthlyExpenditureIncome> {
        private ArrayList<MonthlyExpenditureIncome> data = new ArrayList<>();

        private static class ViewHolder {
            private TextView date, income, expenditure;

        }

        public ReportEditorAdapter(Context context, int resource,
                                      ArrayList<MonthlyExpenditureIncome> item)
        {
            super(context, resource, item);
            data = item;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.layout_blueprint_editing_exp_income, null);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView)convertView.findViewById(R.id.selectedDate);
                viewHolder.income = (TextView) convertView.findViewById
                        (R.id.bluePrint_incomeValue);
                viewHolder.expenditure = (TextView) convertView.findViewById
                        (R.id.bluePrint_expValue);

                convertView.setTag(viewHolder);
                convertView.setTag(R.id.selectedDate, viewHolder.date);
                convertView.setTag(R.id.bluePrint_incomeValue, viewHolder.income);
                convertView.setTag(R.id.bluePrint_expValue, viewHolder.expenditure);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.date.setText(  data.get(position).getDate() + " " + data.get(position).getMonth() + " " + data.get(position).getYear());
            viewHolder.income.setText(String.format("%d", data.get(position).getIncome()));
            viewHolder.expenditure.setText(String.format("%d", data.
                                            get(position).getExpenditure()));

            return convertView;
        }
}

