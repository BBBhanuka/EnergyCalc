package com.example.energycalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    //Declaration of row in tables in database
    public static final String DATABASE_NAME = "equipment.db";
    public static final String TABLE_NAME = "eqinfo_table";

    public static final String COL_1  = "ID";
    public static final String COL_2  = "NAME";
    public static final String COL_3  = "WATT";
    public static final String COL_4 = "USER";


    public static final String TABLE_NAME2 = "calculations_table";
    public static final String T2COL1  = "NAME";
    public static final String T2COL2  = "TIME";
    public static final String T2COL3  = "USAGE";


    public static final String TABLE_NAME3 = "user_table";

    public static final String T3COL1  = "ID";
    public static final String T3COL2  = "USERNAME";
    public static final String T3COL3  = "EMAIL";
    public static final String T3COL4  = "PASSWORD";
    public static final String T3COL5  = "BIRTHYEAR";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //on create method
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "NAME TEXT NOT NULL,WATT INTEGER NOT NULL, USER TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "NAME TEXT NOT NULL,TIME TEXT NOT NULL, USAGE TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_NAME3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "USERNAME TEXT NOT NULL,EMAIL TEXT NOT NULL, PASSWORD TEXT NOT NULL, BIRTHYEAR TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }


    //insertData method for equipment table
    public boolean insertData(String name, String watt, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, watt);
        contentValues.put(COL_4, user);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }



    //method for check if Username already exist in table
    public  boolean checkUsername(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.query(TABLE_NAME3, new String[]{"USERNAME"}, "USERNAME = ?", new String[]{username}, null, null, null);

        if (result.getCount() != 0) {
            return  true;
        }
        else {
            return false;
        }
    }

        //method for get login data when the username provide as a attribute
    public Cursor getLoginData(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.query(TABLE_NAME3, new String[]{"USERNAME","PASSWORD"}, "USERNAME = ?", new String[]{username}, null, null, null);
        return result;
    }

    //method for get user data for password reset
    public Cursor getUserDataReset(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.query(TABLE_NAME3, new String[]{"USERNAME","BIRTHYEAR"}, "USERNAME = ?", new String[]{username}, null, null, null);
        return result;

    }

    //method for update password when password reset process
    public boolean updatePassword (String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T3COL4, password);

        String selection = "USERNAME = ?";
        String[] selectionArgs = {username};

// Perform the update query
        int rowsUpdated = db.update(TABLE_NAME3, contentValues, selection, selectionArgs);

// Check the result
        if (rowsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }


    //method for insert user data in to user table in database while registration
    public boolean insertUserData(String username, String email, String password, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T3COL2, username);
        contentValues.put(T3COL3, email);
        contentValues.put(T3COL4, password);
        contentValues.put(T3COL5, birthday);


        long result = db.insert(TABLE_NAME3, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }



        //method for insert data from calculation
    public boolean insertCalculatedData(String name, String time, String usage) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2COL1, name);
        contentValues.put(T2COL2, time);
        contentValues.put(T2COL3, usage);
        long result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


    //method for clear calculation table
    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, null, null);

    }


    //method for clear Equipment information table
    public void clearTableEqinfo(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "USER = ?", new String[]{username});

    }


    //method for get row count in calculation table
    public int getRowCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_NAME2, null);
        int numberofRows = 0;

        if(result.moveToFirst()){
            do {
                numberofRows += 1;
            }
            while (result.moveToNext());
        }
        result.close();
        return numberofRows;

    }

    //method for get row count in Equipment information table
    public int getRowCountEQTable(String username) {

        SQLiteDatabase db = this.getWritableDatabase();

//        Cursor result = db.query(TABLE_NAME, new String[]{"ID","USER","NAME","WATT"}, "USER = ?", new String[]{username}, null, null, null);
        Cursor result = db.query(TABLE_NAME, new String[]{"ID"}, "USER = ?", new String[]{username}, null, null, null);

        int numberofResults = 0;

        if(result.moveToFirst()){
            do {
                numberofResults += 1;
            }
            while (result.moveToNext());
        }
        result.close();
        return numberofResults;

    }


    //method for get all equipment names from equipment table to load into spinner
    public List<String> getAllEQNames(String username) {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursorNames = db.query(TABLE_NAME, new String[]{"NAME","WATT"}, "USER = ?", new String[]{username}, null, null, null);

        if (cursorNames.moveToFirst()) {
            do {
                // Retrieve data from cursor and add it to dataList
                String name = cursorNames.getString(0);
                dataList.add(name);
            } while (cursorNames.moveToNext());
        }
        cursorNames.close();
        return dataList;
    }

    //method for get all equipment values from equipment table to load into spinner
    public List<String> getAllvalues(String username) {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursorValues = db.query(TABLE_NAME, new String[]{"NAME","WATT"}, "USER = ?", new String[]{username}, null, null, null);


        if (cursorValues.moveToFirst()) {
            do {
                // Retrieve data from cursor and add it to dataList
                String value = cursorValues.getString(1);
                dataList.add(value);
            } while (cursorValues.moveToNext());
        }
        cursorValues.close();
        return dataList;
    }



    //method for get all data from calculation table for display results
    public Cursor getAllCalculatedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_NAME2, null);
        return result;
    }

}
