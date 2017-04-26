package com.isep.android.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private String createStatement = "";
    private String dropStatement = "";
    private String tableName;
    private Context context;

    public DBHelper(Context context, String tableName, String fields) {
        super(context, tableName, null, DATABASE_VERSION);
        this.createStatement  = "CREATE TABLE IF NOT EXISTS ";
        this.createStatement += tableName + " (";
        this.createStatement += fields + ");";
        this.tableName = tableName;
        dropStatement= "DROP TABLE IF EXISTS " + this.tableName;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createStatement);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int pVer, int nVer) {
        db.execSQL(this.dropStatement);
        onCreate(db);
    }
}
