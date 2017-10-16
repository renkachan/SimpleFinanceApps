package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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
    View view1, view2, view3;
    String selectedMonth ,formattedValue;
    int selectedDay, selectedYear;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedMonth = "";
        view1 = getLayoutInflater().inflate(R.layout.layout_expenditure, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_calendar, null);
        view3 = getLayoutInflater().inflate(R.layout.layout_input_expenditure , null);
        setContentView(view1);
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
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                selectedMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
                selectedDay = dayOfMonth;
                selectedYear = year;

            }
        });
    }

    public void openInputMenu(View v)   {
        if(!selectedMonth.equals(""))    {
            view1.setVisibility(View.GONE);
            view3.setVisibility(View.VISIBLE);
            setContentView(view3);
            getFormattedNumber();
        }
        else {
            Toast.makeText(this, "You haven't selected the day", Toast.LENGTH_SHORT).show();
        }

    }

    public void inputTheNumber(View v)  {
        Button b = (Button) v;
        String displayedText = "";
        TextView expValue = (TextView) findViewById(R.id.expInput);
        displayedText =  expValue.getText().toString();
        String buttonText = b.getText().toString();
        if(displayedText.equals(""))
        {
            displayedText = buttonText;
        }
        else    {
            displayedText += buttonText;
        }
        expValue.setText(displayedText);
    }

    public void removeNumber(View v)    {
        TextView expValue = (TextView) findViewById(R.id.expInput);
        if(!expValue.getText().equals("0"))
        {
            expValue.setText(expValue.getText().toString().substring(0,expValue.getText().length() - 1));
        }
        if(expValue.length() == 0)
        {
            expValue.setText("0");
        }
    }


    public void addThisMonthExpenditure(View v) {
        int expValueWithoutComma = 0;
        TextView expValue = (TextView) findViewById(R.id.expInput);
        //String trimmedExpValue = expValue.getText().toString().trim();
        if (!expValue.getText().equals("0")) {
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
            Toast.makeText(this, "You have input expenditure: " + expValue.getText() + " at " + selectedMonth, Toast.LENGTH_SHORT).show();

//            dataExist = DBContract.TABLE_EXPINCOME.CheckDataIsExistOrNot(handler.getWritableDatabase(), selectedMonth);
//            if(dataExist == true)
//            {
//                DBContract.TABLE_EXPINCOME.updateIncomeData(handler.getWritableDatabase(), selectedMonth, Integer.parseInt(expValue.getText().toString()));
//            }
//            else    {
//                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),selectedMonth,  Integer.parseInt(expValue.getText().toString()));
//            }
        }

        view3.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        setContentView(view1);
    }

    public void confirmDateSelection(View v)    {
        view2.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        Toast.makeText(this, "You have selected " + selectedDay + " " + selectedMonth + " " + selectedYear, Toast.LENGTH_SHORT).show();
        setContentView(view1);

    }

    public void getFormattedNumber()    {
        final TextView expValue = (TextView) findViewById(R.id.expInput);
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
                expValue.addTextChangedListener(this);
            }
        });
    }
}
