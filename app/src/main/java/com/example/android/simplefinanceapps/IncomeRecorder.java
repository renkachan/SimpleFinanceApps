package com.example.android.simplefinanceapps;

import android.app.Activity;
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
import java.util.TimeZone;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class IncomeRecorder extends AppCompatActivity {
    View view1;
    String formattedValue;
    CalendarView calendar;
    String selectedMonthInWords;
    int selectedMonth, selectedDay, selectedYear;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            selectedMonthInWords = "";
           // view1 = getLayoutInflater().inflate(R.layout.layout_income, null);
            view1 = getLayoutInflater().inflate(R.layout.layout_input_income, null);
            setContentView(view1);

            calendar = (CalendarView) findViewById(R.id.dateSelection);
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()    {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth)
                {
                    GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                    selectedMonthInWords = calendar.getDisplayName(Calendar.MONTH,
                            Calendar.LONG, Locale.getDefault());
                    selectedMonth = month;
                    selectedDay = dayOfMonth;
                    selectedYear = year;

                }
            });

            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            selectedMonthInWords = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            selectedMonth = c.get(Calendar.MONTH);
            selectedDay = c.get(Calendar.DAY_OF_MONTH);
            selectedYear = c.get(Calendar.YEAR);

            getFormattedNumber();
        }

        public void inputTheNumber(View v) {
            Button b = (Button) v;
            String displayedText = "";
            TextView incomeValue = (TextView) findViewById(R.id.incomeInput);
            displayedText =  incomeValue.getText().toString();
            String buttonText = b.getText().toString();

            if (displayedText.equals("")) {
                displayedText = buttonText;
            } else {
                displayedText += buttonText;
            }
            incomeValue.setText(displayedText);
        }

        public void removeNumber(View v) {
            TextView incomeValue = (TextView) findViewById(R.id.incomeInput);

            if (!incomeValue.getText().equals("0"))
            {
                incomeValue.setText(incomeValue.getText().toString().substring(0,
                        incomeValue.getText().length() - 1));
            }

            if (incomeValue.length() == 0) {
                incomeValue.setText("0");
            }
        }

        public void addThisMonthIncome(View v) {
            Calendar c = Calendar.getInstance();
            c.set(selectedYear, selectedMonth, selectedDay);
            long timeInMillis = c.getTimeInMillis();

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
                DBContract.TABLE_EXPINCOME.insertIncomeData(handler.getWritableDatabase(),
                        timeInMillis, incomeValueWithoutComma, "INCOME");
                handler.close();

                Toast.makeText(this, "You have input income :"  + incomeValue.getText()
                                + " at " + "" + selectedDay + " " + selectedMonthInWords + " "
                                + selectedYear, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this, MainActivity.class );
                startActivity(i);
            } else {
                Toast.makeText(this, "You haven't input anything",
                        Toast.LENGTH_LONG).show();
            }
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
                    if(incomeValue.getText().toString().contains(",")) {
                        incomeValue.setText(incomeValue.getText().toString()
                                .replaceAll(",", ""));
                    }

                    if(incomeValue.getText().toString().contains(" ")) {
                        incomeValue.setText(incomeValue.getText().toString()
                                .replaceAll(" ", ""));
                    }
                    if(incomeValue.length()!= 0) {
                        formattedValue = formatter.format((Integer.parseInt
                                (incomeValue.getText().toString())));
                    }
                    else {
                        formattedValue = "";
                    }

                    incomeValue.setText(formattedValue);
                    incomeValue.addTextChangedListener(this);
                }
            });
        }
    }

