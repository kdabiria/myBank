package com.example.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private static final String col6 = "EMAIL";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT,LASTNAME TEXT, USERNAME TEXT, PASSWORD TEXT, BALANCE TEXT, EMAIL TEXT)";
        db.execSQL(createTable);
        createAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    /***
     * Creating default admin
     * @param db
     */
    private void createAdmin(SQLiteDatabase db) {
        Log.d(TAG, "ADMIN CREATED!!");
        ContentValues contentValues = new ContentValues();
//        contentValues.put(col1, (byte[]) null);
        contentValues.put(col1, "admin");
//        contentValues.put(col2, (byte[]) null);
        contentValues.put(col2, "admin");
        contentValues.put(col3, "admin");
        contentValues.put(col4, "5456bff3949bdb3461939a7a85898fd1df345638ad2d355aeb59db2852120");
//        contentValues.put(col5, (byte[]) null);
        contentValues.put(col5, "0");
        contentValues.put(col6, "admin@uci.edu");

        db.insert(TABLE_NAME,null, contentValues);
    }

    /***
     * adding data to db
     * @param item
     * @param item2
     * @param item3
     * @param item4
     * @param item5
     * @param item6
     * @return
     */
    public boolean addData(String item, String item2, String item3, String item4, Float item5, String item6) {
        if (!checkUsername(item3)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col1, item);
            contentValues.put(col2, item2);
            contentValues.put(col3, item3);
            contentValues.put(col4, hash(item4) );
            contentValues.put(col5, item5);
            contentValues.put(col6, item6);

            long result = db.insert(TABLE_NAME, null, contentValues);

            return result != -1;
        }

        return false;
    }

    public String hash(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            //create Hex string
            StringBuffer hexString = new StringBuffer();
            for(int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            Log.d(TAG, hexString.toString());
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /***
     * updateing balance for given user
     * @param item
     * @param id
     */
    public void updateBalance(String item, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col5,item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        db.update(TABLE_NAME, contentValues,"ID = ?", new String[]{id});
    }

    /***
     * checking if password matches
     * @param pass
     * @return
     */
    public boolean checkPassword(String pass) {
        String query = "SELECT PASSWORD FROM " + TABLE_NAME + " WHERE "+ col4 + " = ?";
        String[] whereArgs = {pass};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        return count >= 1;
    }

    /***
     * Checking if username exists in db
     * @param user
     * @return
     */
    public boolean checkUsername(String user) {
        String query = "SELECT USERNAME FROM " + TABLE_NAME + " WHERE "+ col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        return count >= 1;
    }

    public String getUsername(String user) {
        String username = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            username = cursor.getString(3);

        return username;
    }

    /***
     * Retrieving balance for given user
     * @param user
     * @return
     */
    public String getBalance(String user) {
        String amount = "0";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            amount = cursor.getString(5);

        return amount;
    }

    /***
     * Retrieving first name for given user
     * @param user
     * @return
     */
    public String getFirstName(String user) {
        String fname = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            fname = cursor.getString(1);

        return fname;
    }

    /***
     * Retrieving last name for given user
     * @param user
     * @return
     */
    public String getLasttName(String user) {
        String lname = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            lname = cursor.getString(2);

        return lname;
    }

    /***
     * Retrieving email for given user
     * @param user
     * @return
     */
    public String getEmail(String user) {
        String email = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        while (cursor.moveToNext())
            email = cursor.getString(6);

        return email;
    }

    /***
     * Retrieving ID for the given user
     * @param user
     * @return
     */
    public String getID(String user) {
        String id = "NONE";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + col3 + " = ?";
        String[] whereArgs = {user};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        ArrayList<String> res = new ArrayList<>();
        while (cursor.moveToNext())
            id = cursor.getString(0);

        return id;
    }

    /***
     * Retrieving all the information in db
     * @return
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    /***
     * updating passowrd given the user
     * @param username
     * @param password
     * @return
     */
    public Boolean updatePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col4, password );
        long result = db.update(TABLE_NAME, contentValues, "USERNAME = ?", new String[]{username});

        return result != -1;

    }
}
