package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class ExpenditureRecorder extends AppCompatActivity {
    View view1, view2;
    String selectedMonth ,formattedValue;
    int selectedDay, selectedYear;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.layout_expenditure, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_calendar, null);
        setContentView(view1);
        getFormattedNumber();
    }

    public void backToMainMenu (View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public  void selectDay(View v) {
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        setContentView(view2);
        calendar = (CalendarView) findViewById(R.id.dateSelection);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                selectedMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
                selectedDay = dayOfMonth;
                selectedYear = year;

            }
        });
    }

    public void addThisMonthExpenditure(View v) {
        int expValueWithoutComma = 0;
        EditText expValue = (EditText) findViewById(R.id.expenditureInput);
        String trimmedExpValue = expValue.getText().toString().trim();
        if (expValue.length() == 0) {
            Toast.makeText(this, "Please input value", Toast.LENGTH_LONG).show();
        }
        else {
            if(expValue.getText().toString().contains(",")) {
                expValueWithoutComma = Integer.parseInt(expValue.getText().
                        toString().replaceAll(",", ""));
            }
            else {
                expValueWithoutComma = Integer.parseInt(expValue.getText().toString());
            }
            DBHandler handler = new DBHandler(this);
            Boolean dataExist = false;
            DBContract.TABLE_EXPINCOME.insertExpData(handler.getWritableDatabase(),
                    selectedMonth, expValueWithoutComma);
            handler.close();

//            dataExist = DBContract.TABLE_EXPINCOME.CheckDataIsExistOrNot(handler.getWritableDatabase(), selectedMonth);
//            if(dataExist == true)
//            {
//                DBContract.TABLE_EXPINCOME.updateIncomeData(handler.getWritableDatabase(), selectedMonth, Integer.parseInt(expValue.getText().toString()));
//            }
//            else    {
//                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),selectedMonth,  Integer.parseInt(expValue.getText().toString()));
//            }
        }
    }

    public void confirmDateSelection(View v)    {
        view2.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        setContentView(view1);
    }

    public void getFormattedNumber()    {
        final EditText expValue = (EditText) findViewById(R.id.expenditureInput);
        expValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                expValue.removeTextChangedListener(this);
                DecimalFormat formatter = new DecimalFormat("##,###,###");
                if(expValue.getText().toString().contains(","))
                {
                    expValue.setText(expValue.getText().toString().replaceAll(",", ""));
                }
                if(expValue.getText().toString().contains(" "))
                {
                    expValue.setText(expValue.getText().toString().replaceAll(" ", ""));
                }
                if(expValue.length()!= 0)
                {
                    formattedValue = formatter.format((Integer.parseInt(expValue.
                            getText().toString())));
                }
                else    {
                    formattedValue = "";
                }
                expValue.setText(formattedValue);
                expValue.setSelection(expValue.getText().length());
                expValue.addTextChangedListener(this);
            }
        });
    }
}
