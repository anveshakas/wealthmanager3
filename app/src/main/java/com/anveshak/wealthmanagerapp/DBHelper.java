package com.anveshak.wealthmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tamajeet on 24-11-2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "ANVESKS_DB" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Customer_Table (\n" +
                "\tID integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\tCustomer_ID varchar,\n" +
                "\tTransaction_DateTime datetime,\n" +
                "\tMerchant_Details varchar,\n" +
                "\tDebit decimal,\n" +
                "\tCredit decimal,\n" +
                "\tBalance decimal\n" +
                ")");
        sqLiteDatabase.execSQL("CREATE TABLE Transaction_Type_Table (\n" +
                "\tID integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\tTransaction_Type varchar,\n" +
                "\tMerchant_Details varchar)");
        sqLiteDatabase.execSQL("CREATE TABLE Score_Table (\n" +
                "\tID integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\tCustomer_ID varchar,\n" +
                "\tType_of_Expence varchar,\n" +
                "\tMonth varchar,\n" +
                "\tScore integer\n" +
                ")");
        sqLiteDatabase.execSQL("CREATE TABLE Score_Reference (\n" +
                "\tID integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\tType_of_Expence varchar,\n" +
                "\tStart_Percentage integer,\n" +
                "\tEnd_Percentage integer,\n" +
                "\tScore integer\n" +
                ")");
        sqLiteDatabase.execSQL("CREATE TABLE \"Investment_Table\" (\n" +
                "\tID integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\tScoreStart integer,\n" +
                "\tScoreEnd integer,\n" +
                "\tInvestment_Options varchar\n" +
                ")");
        sqLiteDatabase.execSQL("DELETE FROM Customer_Table");
        sqLiteDatabase.execSQL("DELETE FROM Score_Table");
        sqLiteDatabase.execSQL("DELETE FROM Score_Reference");
        sqLiteDatabase.execSQL("DELETE FROM Investment_Table");
        sqLiteDatabase.execSQL("DELETE FROM Transaction_Type_Table");
        insertContent();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Customer_Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Score_Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Score_Reference");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Investment_Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Transaction_Type_Table");
        onCreate(sqLiteDatabase);
    }
    public boolean insertContent () {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Type_of_Expence", "Investment");
        contentValues.put("Start_Percentage", 0);
        contentValues.put("End_Percentage", 5);
        contentValues.put("End_Percentage", -10);
        db.insert("Score_Reference", null, contentValues);
        return true;
    }
}
