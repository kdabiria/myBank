package com.example.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "account.db";
    public static final String TABLE_NAME = "user_table";
    private static final String col0 = "ID";
    private static final String col1 = "FIRSTNAME";
    private static final String col2 = "LASTNAME";
    private static final String col3 = "USERNAME";
    private static final String col4 = "PASSWORD";
    private static final String col5 = "BALANCE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT,LASTNAME TEXT, USERNAME TEXT, PASSWORD TEXT, BALANCE TEXT)";
        db.execSQL(createTable);
        createAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    private void createAdmin(SQLiteDatabase db) {
        Log.d(TAG, "ADMIN CREATED!!");
        ContentValues contentValues = new ContentValues();
//        contentValues.put(col1, (byte[]) null);
        contentValues.put(col1, "admin");
//        contentValues.put(col2, (byte[]) null);
        contentValues.put(col2, "admin");
        contentValues.put(col3, "admin");
        contentValues.put(col4, "iam@admin");
//        contentValues.put(col5, (byte[]) null);
        contentValues.put(col5, "admin");

        db.insert(TABLE_NAME,null, contentValues);
    }
    public boolean addData(String item, String item2, String item3, String item4, int item5) {
        if (!checkUsername(item3)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col1, item);
            contentValues.put(col2, item2);
            contentValues.put(col3, item3);
            contentValues.put(col4, item4);
            contentValues.put(col5, item5);

            Log.d(TAG, "addData: Adding " + item + " " + item2 + " " + item3 + " " + item4 + " " + item5 + " to " + TABLE_NAME);
            long result = db.insert(TABLE_NAME, null, contentValues);

            return result != -1;
        }

        return false;
    }

    public void updateBalance(String item, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col5,item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        db.update(TABLE_NAME, contentValues,"ID = ?", new String[]{id});
    }

    public ArrayList<String> checkUserPass(String user, String pass) {
        ArrayList<String> res = new ArrayList<>();
        String temp = "";
        String temp2 = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = '" + user + "' AND PASSWORD = '" + pass + "'";
        try {

            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                temp = cursor.getString(3);
                temp2 = cursor.getString(4);
            }
            res.add(temp);
            res.add(temp2);

        } catch (Exception e) {

        }

        return res;
    }


    public boolean checkUsername(String user) {
        String query = "SELECT USERNAME FROM " + TABLE_NAME + " WHERE "+ col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        return count >= 1;
    }


    public String getBalance(String user) {
        String amount = "0";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            amount = cursor.getString(5);

        Log.d(TAG, "checking the value for balance : " + amount);
        return amount;
    }

    public String getFirstName(String user) {
        String fname = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            fname = cursor.getString(1);

        Log.d(TAG, "checking the value for balance : " + fname);
        return fname;
    }

    public String getID(String user) {
        String id = "NONE";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        ArrayList<String> res = new ArrayList<>();
        while (cursor.moveToNext())
            id = cursor.getString(0);

        Log.d(TAG, "checking the value for balance : " + id);
        return id;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
