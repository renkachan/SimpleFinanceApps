package com.example.android.simplefinanceapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robert.arifin on 10/11/2017.
 */

public class RecordEditor extends AppCompatActivity {
    String  formattedValue, category;
    int id;
    long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_record_editor);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        date = intent.getLongExtra("date", 0);
        category = intent.getStringExtra("date");
        getFormattedNumber();
    }

    public void inputTheNumber(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();

        TextView incomeValue = (TextView) findViewById(R.id.inputField);

        String displayedText = "";
        displayedText =  incomeValue.getText().toString();

        if (displayedText.equals("")) {
            displayedText = buttonText;
        } else {
            displayedText += buttonText;
        }

        incomeValue.setText(displayedText);
    }

    public void removeNumber(View v) {
        TextView incomeValue = (TextView) findViewById(R.id.inputField);

        if(!incomeValue.getText().equals("0"))
        {
            incomeValue.setText(incomeValue.getText().toString().substring(0,incomeValue.getText().length() - 1));
        }
        if(incomeValue.length() == 0)
        {
            incomeValue.setText("0");
        }
    }

    public void updateValue(View v) {
        int incomeValueWithoutComma = 0;
        TextView incomeValue = (TextView) findViewById(R.id.inputField);

        if (!incomeValue.getText().equals("0")) {
            if (incomeValue.getText().toString().contains(",")) {
                incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().
                        toString().replaceAll(",", ""));
            } else {
                incomeValueWithoutComma = Integer.parseInt(incomeValue.getText().toString());
            }
            String dateString = new SimpleDateFormat("MM/dd/yyyy").
                    format(new Date(date));
            FinanceModel record = new FinanceModel();
            record.setAmount(incomeValueWithoutComma);
            record.setID(id);

            SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
            sqLiteHelper.updateRecord(record);

            Toast.makeText(this, "You have updated " + category + " :"
                    + incomeValue.getText() + " at " + dateString, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, RecordList.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "You haven't change anything",Toast.LENGTH_LONG).show();
        }
    }

    public void getFormattedNumber() {
        final TextView incomeValue = (TextView) findViewById(R.id.inputField);
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
                DecimalFormat formatter = new DecimalFormat("##,###,###");
                if (incomeValue.getText().toString().contains(",")) {
                    incomeValue.setText(incomeValue.getText().toString()
                            .replaceAll(",", ""));
                }

                if (incomeValue.getText().toString().contains(" ")) {
                    incomeValue.setText(incomeValue.getText().toString()
                            .replaceAll(" ", ""));
                }

                if (incomeValue.length()!= 0) {
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
