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
import android.widget.EditText;
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

public class ExpenditureRecorder extends AppCompatActivity {
    View view1;
    String formattedValue, selectedMonthInWords;
    int selectedDay, selectedYear, selectedMonth;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedMonthInWords = "";
        view1 = getLayoutInflater().inflate(R.layout.layout_input_expenditure , null);
        setContentView(view1);

        calendar = (CalendarView) findViewById(R.id.dateSelection);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                int dayOfMonth) {
                    GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                    selectedMonthInWords = calendar.getDisplayName(Calendar.MONTH,
                            Calendar.LONG, Locale.getDefault());
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
        TextView expValue = (TextView) findViewById(R.id.expInput);
        displayedText =  expValue.getText().toString();
        String buttonText = b.getText().toString();

        if (displayedText.equals("")) {
            displayedText = buttonText;
        } else {
            displayedText += buttonText;
        }
        expValue.setText(displayedText);
    }

    public void removeNumber(View v)    {
        TextView expValue = (TextView) findViewById(R.id.expInput);

        if (!expValue.getText().equals("0")) {
            expValue.setText(expValue.getText().toString().
                    substring(0,expValue.getText().length() - 1));
        }

        if (expValue.length() == 0) {
            expValue.setText("0");
        }
    }

    public void addThisMonthExpenditure(View v) {
        int expValueWithoutComma = 0;
        TextView expValue = (TextView) findViewById(R.id.expInput);

        Calendar c = Calendar.getInstance();
        c.set(selectedYear, selectedMonth, selectedDay);
        long timeInMillis = c.getTimeInMillis();

        if (!expValue.getText().equals("0")) {
            if (expValue.getText().toString().contains(",")) {
                expValueWithoutComma = Integer.parseInt(expValue.getText().
                        toString().replaceAll(",", ""));
            } else {
                expValueWithoutComma = Integer.parseInt(expValue.getText().toString());
        }

            FinanceModel record = new FinanceModel();
            record.setAmount(expValueWithoutComma);
            record.setDate(timeInMillis);
            record.setCategory("EXPENDITURE");

            SQLiteHelper sqlLiteHelper = new SQLiteHelper(this);
            sqlLiteHelper.insertRecord(record);

            Toast.makeText(this, "You have input expense : "  + expValue.getText()
                    + " at " + " " + selectedDay + " " + selectedMonthInWords + " "
                    + selectedYear, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class );
            startActivity(i);
        } else {
            Toast.makeText(this, "You haven't input anything",Toast.LENGTH_LONG).show();
        }
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

                if (expValue.getText().toString().contains(",")) {
                    expValue.setText(expValue.getText().toString().
                            replaceAll(",", ""));
                }

                if (expValue.getText().toString().contains(" ")) {
                    expValue.setText(expValue.getText().toString().
                            replaceAll(" ", ""));
                }

                if (expValue.length()!= 0) {
                    formattedValue = formatter.format((Integer.parseInt(expValue.
                            getText().toString())));
                } else    {
                    formattedValue = "";
                }

                expValue.setText(formattedValue);
                expValue.addTextChangedListener(this);
            }
        });
    }
}
