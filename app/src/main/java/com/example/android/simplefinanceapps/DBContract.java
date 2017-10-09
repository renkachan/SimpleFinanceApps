package com.example.android.simplefinanceapps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class DBContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "simpleFinanceApps.db";

    private DBContract() {
    }

    public static final class TABLE_EXPINCOME implements BaseColumns {
        public static final String TABLE_NAME = "financeReporting";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_INCOME = "incomePerMonth";
        public static final String COLUMN_EXPENDITURE = "expPerMonth";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_MONTH + " TEXT, " +
                        COLUMN_INCOME + " TEXT," +
                        COLUMN_EXPENDITURE + " TEXT )";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;


        public static long insertIncomeData(SQLiteDatabase db,
                                      String month,
                                      int income) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MONTH, month);
            values.put(COLUMN_INCOME, income);
            return db.insert(TABLE_NAME, null, values);
        }

        public static long insertExpData(SQLiteDatabase db,
                                            String month,
                                            int expenditure) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MONTH, month);
            values.put(COLUMN_INCOME, expenditure);
            return db.insert(TABLE_NAME, null, values);
        }

        public static void updateIncomeData (SQLiteDatabase db, String month, int data )
        {
            db.execSQL("update " + TABLE_NAME
                    + " set " + COLUMN_INCOME + " = " + COLUMN_INCOME + data + " where "
                    + COLUMN_MONTH  + " = '" + month + "'");
        }

        public static void updateExpenditureData (SQLiteDatabase db, String month, int data )
        {
            db.execSQL("update " + TABLE_NAME
                    + " set " + COLUMN_EXPENDITURE + " = " + COLUMN_EXPENDITURE + data + " where "
                    + COLUMN_MONTH  + " = '" + month + "'");
        }


//        public static void deleteData(SQLiteDatabase db, String playerName) {
//            String selection = COLUMN_MONTH + "=?";
//            String[] selectionArgs = {playerName};
//            db.delete(TABLE_NAME, selection, selectionArgs);
//            //db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PLAYER_NAME + " = '" + playerName + "'" );
//        }

//        public static boolean CheckDataIsExistOrNot(SQLiteDatabase db, String month) {
//            String query = "Select * from " + TABLE_NAME + " where " + COLUMN_MONTH + " = " + month;
//            Cursor cursor = db.rawQuery(query, null);
//            if(cursor.getCount() <= 0)
//            {
//                cursor.close();
//                return false;
//            }
//            cursor.close();
//            return true;
//        }
        public static List<MonthlyExpenditureIncome> getData(SQLiteDatabase db) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
            List<MonthlyExpenditureIncome> items = new ArrayList<>();

            while (cursor.moveToNext())
            {
                items.add(addItem(cursor));
            }

            cursor.close();
            return items;
        }

        public static MonthlyExpenditureIncome addItem(Cursor cursor) {
            MonthlyExpenditureIncome financeData = new MonthlyExpenditureIncome(
                    cursor.getString(cursor.getColumnIndexOrThrow(TABLE_EXPINCOME.COLUMN_MONTH)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENDITURE)));
            return financeData;
        }
    }
}
