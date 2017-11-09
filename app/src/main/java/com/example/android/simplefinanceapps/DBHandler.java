package com.example.android.simplefinanceapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by robert.arifin on 09/10/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public  DBHandler (Context context)
    {
        super(context,DBContract.DATABASE_NAME, null , DBContract.DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DBContract.TABLE_EXPINCOME.CREATE_TABLE);

    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DBContract.TABLE_EXPINCOME.DELETE_TABLE);
        onCreate(db);
    }
}

