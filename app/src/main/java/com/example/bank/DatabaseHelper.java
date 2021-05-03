package com.example.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "account.db";
    public static final String TABLE_NAME = "user_table";
    private static final String col0 = "ID";
    private static final String col1 = "FIRSTNAME";
    private static final String col2 = "LASTNAME";
    private static final String col3 = "USERNAME";
    private static final String col4 = "PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT,LASTNAME TEXT, USERNAME TEXT, PASSWORD TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    public boolean addData(String item, String item2, String item3, String item4 ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, item);
        contentValues.put(col2, item2);
        contentValues.put(col3, item3);
        contentValues.put(col4, item4);

        Log.d(TAG, "addData: Adding " + item + " " + item2 + " " + item3 + " " + item4 + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
}
