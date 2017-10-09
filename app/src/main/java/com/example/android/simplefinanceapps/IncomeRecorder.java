package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class IncomeRecorder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        Spinner spinner;
        String selectedMonth ,formattedValue;;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_income);
            spinner = (Spinner) findViewById(R.id.incomeSpinner);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            getFormattedNumber();
        }



        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView currentMonth = (TextView) view;
            selectedMonth = currentMonth.getText().toString();
        }

        public void backToMainMenu (View v) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        public void addThisMonthIncome(View v) {
            int incomeValueWithoutComma = 0;
            EditText incomeValue = (EditText) findViewById(R.id.incomeInput);
            if (incomeValue.length() == 0) {
                Toast.makeText(this, "Please input value", Toast.LENGTH_LONG).show();
            }
            else {
                if (incomeValue.getText().toString().contains(",")) {
                    incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().toString().replaceAll(",", ""));
                }
                else {
                    incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().toString());
                }
                DBHandler handler = new DBHandler(this);
                Boolean dataExist = false;
                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(), selectedMonth, incomeValueWithoutComma);
                handler.close();
//            dataExist = DBContract.TABLE_EXPINCOME.CheckDataIsExistOrNot(handler.getWritableDatabase(), selectedMonth);
//            if(dataExist == true)
//            {
//                DBContract.TABLE_EXPINCOME.updateIncomeData(handler.getWritableDatabase(), selectedMonth, Integer.parseInt(incomeValue.getText().toString()));
//            }
//            else    {
//                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),selectedMonth,  Integer.parseInt(incomeValue.getText().toString()));
//            }
            }
        }

        public void getFormattedNumber()    {
            final EditText incomeValue = (EditText) findViewById(R.id.incomeInput);
            incomeValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    incomeValue.removeTextChangedListener(this);
                    DecimalFormat  formatter = new DecimalFormat("##,###,###");
                    if(incomeValue.getText().toString().contains(","))
                    {
                        incomeValue.setText(incomeValue.getText().toString().replaceAll(",", ""));
                    }
                    if(incomeValue.getText().toString().contains(" "))
                    {
                        incomeValue.setText(incomeValue.getText().toString().replaceAll(" ", ""));
                    }
                    if(incomeValue.length()!= 0)
                    {
                        formattedValue = formatter.format((Integer.parseInt(incomeValue.getText().toString())));
                    }
                    else    {
                        formattedValue = "";
                    }
                    incomeValue.setText(formattedValue);
                    incomeValue.setSelection(incomeValue.getText().length());
                    incomeValue.addTextChangedListener(this);
                }
            });

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {


        }
    }

