package com.example.finalandroidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String PROPERTY_TABLE = "PROPERTY_TABLE";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PROPERTY_NAME = "PROPERTY_NAME";
    public static final String COLUMN_PROPERTY_ADDRESS = "PROPERTY_ADDRESS";
    public static final String COLUMN_PROPERTY_CITY = "PROPERTY_CITY";
    public static final String COLUMN_PROPERTY_REGION = "PROPERTY_REGION";
    public static final String COLUMN_PROPERTY_DESC = "PROPERTY_DESC";

    public DBHandler(@Nullable Context context) {
        super(context, "property.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatment = "CREATE TABLE " + PROPERTY_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PROPERTY_NAME + " TEXT, " + COLUMN_PROPERTY_ADDRESS + " TEXT, " + COLUMN_PROPERTY_CITY + " TEXT, " + COLUMN_PROPERTY_REGION + " TEXT, " + COLUMN_PROPERTY_DESC + " TEXT )";

        db.execSQL(createTableStatment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public PropertyModel searchForSpecificPropertyByName(String propertyNameString){

        PropertyModel propertyModel = null;

        String queryString = "SELECT * FROM " + PROPERTY_TABLE + " WHERE " + COLUMN_PROPERTY_NAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {propertyNameString});

        try {
            if (cursor.moveToFirst()) {
                int propertyID = cursor.getInt(0);
                String propertyName = cursor.getString(1);
                String propertyAddress = cursor.getString(2);
                String propertyCity = cursor.getString(3);
                String propertyRegion = cursor.getString(4);
                String propertyDesc = cursor.getString(5);

                propertyModel = new PropertyModel(propertyID,propertyName,propertyAddress,propertyCity,propertyRegion, propertyDesc);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return propertyModel;


    }

    public boolean addOne(PropertyModel propertyModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PROPERTY_NAME, propertyModel.getName());
        cv.put(COLUMN_PROPERTY_ADDRESS, propertyModel.getAddress());
        cv.put(COLUMN_PROPERTY_CITY, propertyModel.getCity());
        cv.put(COLUMN_PROPERTY_REGION, propertyModel.getRegion());
        cv.put(COLUMN_PROPERTY_DESC, propertyModel.getDescription());

        long insert = db.insert(PROPERTY_TABLE, null, cv);

        if (insert == 1){
            return false;
        }
        else {
            return true;
        }
    }

    public String[] getName() {

        String queryString = "SELECT * FROM " + PROPERTY_TABLE;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);
            int n = cursor.getCount();
            cursor.moveToFirst();
            String[] column = new String[n]; int i=0;

            do
            {
                column[i]=cursor.getString(cursor.getColumnIndex(COLUMN_PROPERTY_NAME));
                i++;
            } while(cursor.moveToNext());

            cursor.close();
            return column;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public List<PropertyModel> getAll() {

        List<PropertyModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PROPERTY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int propertyID = cursor.getInt(0);
                String propertyName = cursor.getString(1);
                String propertyAddress = cursor.getString(2);
                String propertyCity = cursor.getString(3);
                String propertyRegion = cursor.getString(4);
                String propertyDesc = cursor.getString(5);

                PropertyModel propertyModel = new PropertyModel(propertyID,propertyName,propertyAddress,propertyCity,propertyRegion, propertyDesc);
                returnList.add(propertyModel);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public void  deleteOne(String propertyName){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROPERTY_TABLE, COLUMN_PROPERTY_NAME + "=?", new String[]{propertyName});
        db.close();
    }
}
