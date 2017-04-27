package com.isep.android.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class DBAdapter {
    private DBHelper dbHelper;

    private static final String TABLE = "WEATHERINFO";
    private static final String ID = "ID";
    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private static final String TEMPERATURE = "TEMPERATURE";
    private static final String MAX = "MAX";
    private static final String MIN = "MIN";
    private static final String WIND = "WIND";
    private static final String HUMIDITY = "HUMIDITY";
    private static final String RAIN = "RAIN";

    private String query =
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LAT + " TEXT,"
            + LNG + " TEXT,"
            + TEMPERATURE + " TEXT,"
            + MAX + " TEXT,"
            + MIN + " TEXT,"
            + WIND + " TEXT,"
            + HUMIDITY + " TEXT,"
            + RAIN + " TEXT";

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context, TABLE, query);
    }

    //Make all CRUD operations (Create, Read, Update and Delete)
    public boolean insertWeatherCondition (String lat, String lng, String temperature, String max, String min, String wind, String humidity, String rain) {
        try {
            //Call data base
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //Create ContentValues too put the values before insert
            ContentValues values = new ContentValues();
            values.put(LAT, lat);
            values.put(LNG, lng);
            values.put(TEMPERATURE, temperature);
            values.put(MAX, max);
            values.put(MIN, min);
            values.put(WIND, wind);
            values.put(HUMIDITY, humidity);
            values.put(RAIN, rain);

            //Insert values in table
            db.insert(TABLE, null, values);
            db.close();
        } catch (SQLException e) {
            Log.e("Insert into table error", e.getMessage());
            return false;
        }
        return true;
    }

    public WeatherConditions getWeatherConditions(int id) {
        WeatherConditions conditions = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            conditions = new WeatherConditions(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            cursor.close();
        }
        db.close();
        return conditions;
    }

    public ArrayList<WeatherConditions> getAllWeatherConditions() {
        String query = "SELECT * FROM " + TABLE;
        ArrayList<WeatherConditions> weatherConditions = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                weatherConditions.add(new WeatherConditions(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return weatherConditions;
    }
}
