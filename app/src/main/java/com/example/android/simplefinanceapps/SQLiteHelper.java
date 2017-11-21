package com.example.android.simplefinanceapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;
    public static final String DATABASE_NAME = "financialReport.db";
    public static final String TABLE_NAME = "financeYearlyReports";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";

    public SQLiteHelper(Context context)
    {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY " +
                "KEY AUTOINCREMENT," + COLUMN_DATE + " INTEGER, " + COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_CATEGORY + " TEXT)");

    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(FinanceModel record) {
        database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, record.getDate());
        values.put(COLUMN_AMOUNT, record.getAmount());
        values.put(COLUMN_CATEGORY, record.getCategory());
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void updateRecord(FinanceModel record) {
        database = this.getReadableDatabase();
        database.execSQL("update " + TABLE_NAME + " set " +
                COLUMN_AMOUNT + " = '" + record.getAmount() +
                "' where " + COLUMN_ID + " = '" + record.getID() + "'");
        database.close();
    }

    public void deleteRecord(FinanceModel record) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + record.getID() + "'");
        database.close();
    }

    public  ArrayList<FinanceModel> getAllRecords() {
        database = this.getReadableDatabase();
        FinanceModel financeModel;
        ArrayList<FinanceModel> records = new ArrayList<FinanceModel>();
        Cursor cursor = database.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_DATE + ", " +
                COLUMN_AMOUNT + ", " + COLUMN_CATEGORY +
                " from financeYearlyReports order by " +COLUMN_DATE + " ASC" , null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                financeModel = new FinanceModel();
                financeModel.setID(cursor.getInt(0));
                financeModel.setDate(cursor.getLong(1));
                financeModel.setAmount(cursor.getInt(2));
                financeModel.setCategory(cursor.getString(3));

                records.add(financeModel);
            }
        }
        cursor.close();
        database.close();

        return records;
    }

}

