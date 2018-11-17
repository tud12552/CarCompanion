package com.example.psusweng.carcompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "USER_CAR_INFO";
    public static final String COL0 = "id";
    public static final String COL1 = "year";
    public static final String COL2 = "make";
    public static final String COL3 = "model";
    public static final String COL4 = "miles";
    public static final String COL5 = "yearly_miles";
    public static final String COL6 = "last_oil_change";

    public DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT, " + COL2 +
                " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 +
                " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addNewCar(String make, String model, String year, String miles, String yearlyMiles, String lastOilChange)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, year);
        contentValues.put(COL2, make);
        contentValues.put(COL3, model);
        contentValues.put(COL4, miles);
        contentValues.put(COL5, yearlyMiles);
        contentValues.put(COL6, lastOilChange);

        Log.d(TAG, "addNewCar: Adding " + year + " " + make + " " + model);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllCars()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getOneCar(String[] carInfo) // carInfo is organized as Year, Make, Model, and Miles.
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // SELECT * FROM Orders WHERE Customer="Smith";
        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL0 + "=" + id;

        String selection = COL1 + "=? and " + COL2 + "=? and " + COL3 + "=? and " + COL4 + "=?";

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + selection, carInfo);

        return data;
    }


    public void deleteCar(String[] car)  // car is organized as Year, Make, Model, and Miles.
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,"year=? and make=? and model=? and miles=?",new String[]{car[0],car[1],car[2],car[3]});

    }

    public void deleteAllCars()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,null,null);
    }
}
