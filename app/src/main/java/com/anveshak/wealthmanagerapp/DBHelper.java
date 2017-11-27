package com.anveshak.wealthmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        contentValues.put("Score", -10);
        db.insert("Score_Reference", null, contentValues);
        db.close();
        return true;
    }
    public Map<String, ScoreDetailsDto> fetchScoreDetails(final String month,final String customerId){
        Map<String, ScoreDetailsDto> scoreDetailsDtoMap = new HashMap<String, ScoreDetailsDto>();
        SQLiteDatabase db = this.getReadableDatabase();
        final String query = "select c.Customer_ID,strftime('%m', `c.Transaction_DateTime`) as month,sum(c.Debit),sum(c.Credit)," +
                "t.Type_of_Expence from Customer_Table c,Transaction_Type_Table t " +
                "where c.Merchant_Details=t.Merchant_Details and c.Customer_Id=? " +
                "group by c.Customer_ID,strftime('%m', `c.Transaction_DateTime`),t.Type_of_Expence" +
                " having strftime('%m', `c.Transaction_DateTime`) = ?";
        final Cursor cursor = db.rawQuery(query, new String[]{customerId, month});
        if (cursor.moveToFirst()) {
            do {
                final ScoreDetailsDto scoreDetails = new ScoreDetailsDto();
                scoreDetails.setCustomerId(cursor.getString(cursor.getColumnIndexOrThrow("Customer_ID")));
                scoreDetails.setMerchantType(cursor.getString(cursor.getColumnIndexOrThrow("Type_of_Expence")));
                if("Income".equalsIgnoreCase(scoreDetails.getMerchantType())){
                    scoreDetails.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("Credit")));
                }
                else{
                    scoreDetails.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("Debit")));
                }
                scoreDetails.setMonth(cursor.getString(cursor.getColumnIndexOrThrow("month")));
                scoreDetailsDtoMap.put(scoreDetails.getMerchantType(), scoreDetails);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return  scoreDetailsDtoMap;
    }
    public boolean deleteScoreDetails(String customerId, String month, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[3];
        args[0] = customerId;
        args[1] = month;
        args[2] = type;
        db.delete("Score_Table","Customer_ID=? and Month=? and Type_of_Expence=?",args);
        db.close();
        return true;
    }
    public boolean insertIntoScoreTable(ScoreDetailsDto dto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Customer_ID", dto.getCustomerId());
        contentValues.put("Month", dto.getMonth());
        contentValues.put("Type_of_Expence", dto.getMerchantType());
        contentValues.put("Score", dto.getScore());
        db.insert("Score_Table", null, contentValues);
        db.close();
        return true;
    }
    public Integer fetchScore(String type, Double percentage){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Score from Score_Reference where Type_of_Expence =? and Start_Percentage <= ? and End_Percentage >= ?";
        String[] args = new String[3];
        args[0] = type;
        args[1] = String.valueOf(percentage);
        args[2] = String.valueOf(percentage);
        final Cursor cursor = db.rawQuery(query, args);
        cursor.moveToFirst();
        Integer result = cursor.getInt(cursor.getColumnIndexOrThrow("Score"));
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return result;
    }
    public Map<String,Double> fetchFutureMeanScores(final String customerId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select avg(Score) as avgScore,Type_of_Expence from Score_Table " +
                "where month <> strftime('%m', 'now') and Customer_ID = ? " +
                "group by Type_of_Expence";
        Map<String,Double> futureScoresMap =new HashMap<String,Double>();
        final Cursor cursor = db.rawQuery(query, new String[]{customerId});
        if (cursor.moveToFirst()) {
            do {
                futureScoresMap.put(cursor.getString(cursor.getColumnIndexOrThrow("Type_of_Expence")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("avgScore")));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return futureScoresMap;
    }
}
