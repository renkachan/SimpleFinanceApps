package com.example.android.simplefinanceapps;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robert.arifin on 09/11/2017.
 */

public class ReportEditorAdapter extends ArrayAdapter<FinanceModel> {
        private ArrayList<FinanceModel> data = new ArrayList<>();

        private static class ViewHolder {
            private TextView date, amount, category;

        }

        public ReportEditorAdapter(Context context, int resource,
                                      ArrayList<FinanceModel> item)
        {
            super(context, resource, item);
            data = item;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.layout_blueprint_editing_exp_income, null);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView)convertView.findViewById(R.id.selectedDate);
                viewHolder.amount = (TextView) convertView.findViewById
                        (R.id.bluePrint_amountValue);
                viewHolder.category = (TextView) convertView.findViewById
                        (R.id.bluePrint_category);

                convertView.setTag(viewHolder);
                convertView.setTag(R.id.selectedDate, viewHolder.date);
                convertView.setTag(R.id.bluePrint_category, viewHolder.category);
                convertView.setTag(R.id.bluePrint_amountValue, viewHolder.amount);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String dateString = new SimpleDateFormat("MM/dd/yyyy").
                    format(new Date(data.get(position).getDate()));

            viewHolder.date.setText(dateString);
            viewHolder.amount.setText(String.format("%d", data.get(position).getAmount()));
            viewHolder.category.setText(data.get(position).getCategory());

            return convertView;
        }
}

