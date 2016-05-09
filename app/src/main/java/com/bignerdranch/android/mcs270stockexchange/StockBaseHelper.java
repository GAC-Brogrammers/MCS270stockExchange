package com.bignerdranch.android.mcs270stockexchange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nbens_000 on 4/21/2016.
 */
public class StockBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "stockBase.db";

    public StockBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + StockDbSchema.StockTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                StockDbSchema.StockTable.Cols.UUID + ", " +
                StockDbSchema.StockTable.Cols.TITLE + ", " +
                StockDbSchema.StockTable.Cols.WEIGHT + ", " +
                StockDbSchema.StockTable.Cols.OVERWEIGHT + ", " +
                StockDbSchema.StockTable.Cols.NEUTRAL + "," +
                StockDbSchema.StockTable.Cols.UNDERWEIGHT + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
