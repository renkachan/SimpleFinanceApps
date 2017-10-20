package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class IncomeRecorder extends AppCompatActivity   {
    View view1, view2, view3;
    String formattedValue, selectedMonth;
    CalendarView calendar;
    int selectedDay, selectedYear;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            selectedMonth = "";
            view1 = getLayoutInflater().inflate(R.layout.layout_income, null);
            view2 = getLayoutInflater().inflate(R.layout.layout_calendar, null);
            view3 = getLayoutInflater().inflate(R.layout.layout_input_income, null);
            setContentView(view1);
        }

        public  void selectDay(View v)  {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            setContentView(view2);
            Calendar normalCalendar = GregorianCalendar.getInstance();
            calendar = (CalendarView) findViewById(R.id.dateSelection);
            selectedDay = normalCalendar.get(Calendar.DAY_OF_MONTH);
            selectedMonth = normalCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
            selectedYear = normalCalendar.get(Calendar.YEAR);
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()    {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth)
                {
                    GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                    selectedMonth = calendar.getDisplayName(Calendar.MONTH,
                            Calendar.LONG, Locale.US);
                    selectedDay = dayOfMonth;
                    selectedYear = year;

                }
            });


        }

        public void confirmDateSelection(View v)    {
            view2.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            Toast.makeText(this, "You have selected " + selectedDay + " " + selectedMonth + " " + selectedYear, Toast.LENGTH_SHORT).show();
            setContentView(view1);
        }

        public void backToMainMenu (View v) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        public void openInputMenu(View v)   {
            if(!selectedMonth.equals(""))    {
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);
                setContentView(view3);
                getFormattedNumber();
            }
               else {
                Toast.makeText(this, "You haven't selected the day",Toast.LENGTH_SHORT).show();
            }
        }

        public void inputTheNumber(View v)  {
            Button b = (Button) v;
            String displayedText = "";
            TextView incomeValue = (TextView) findViewById(R.id.incomeInput);
            displayedText =  incomeValue.getText().toString();
            String buttonText = b.getText().toString();
            if(displayedText.equals(""))
            {
                displayedText = buttonText;
            }
            else    {
                displayedText += buttonText;
            }
            incomeValue.setText(displayedText);
        }

        public void removeNumber(View v)    {
            TextView incomeValue = (TextView) findViewById(R.id.incomeInput);
            if(!incomeValue.getText().equals("0"))
            {
                incomeValue.setText(incomeValue.getText().toString().substring(0,incomeValue.getText().length() - 1));
            }
            if(incomeValue.length() == 0)
            {
                incomeValue.setText("0");
            }
        }

        public void addThisMonthIncome(View v) {
            int incomeValueWithoutComma = 0;
            TextView incomeValue = (TextView) findViewById(R.id.incomeInput);

                if (!incomeValue.getText().equals("0")) {
                    if (incomeValue.getText().toString().contains(",")) {
                        incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().
                                toString().replaceAll(",", ""));
                    } else {
                        incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().toString());
                    }
                    DBHandler handler = new DBHandler(this);
                    Boolean dataExist = false;
                    DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),
                            selectedMonth, incomeValueWithoutComma);
                    handler.close();
                    Toast.makeText(this, "You have input income :"  + incomeValue.getText() + " at " + selectedMonth, Toast.LENGTH_SHORT).show();

//            dataExist = DBContract.TABLE_EXPINCOME.CheckDataIsExistOrNot(handler.getWritableDatabase(), selectedMonth);
//            if(dataExist == true)
//            {
//                DBContract.TABLE_EXPINCOME.updateIncomeData(handler.getWritableDatabase(), selectedMonth, Integer.parseInt(incomeValue.getText().toString()));
//            }
//            else    {
//                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),selectedMonth,  Integer.parseInt(incomeValue.getText().toString()));
//            }
                }

            view3.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            setContentView(view1);
        }

        public void getFormattedNumber()    {
            final TextView incomeValue = (TextView) findViewById(R.id.incomeInput);
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
                        formattedValue = formatter.format((Integer.parseInt
                                (incomeValue.getText().toString())));
                    }
                    else    {
                        formattedValue = "";
                    }
                    incomeValue.setText(formattedValue);
                    //incomeValue.setSelection(incomeValue.getText().length());
                    incomeValue.addTextChangedListener(this);
                }
            });
        }
    }

