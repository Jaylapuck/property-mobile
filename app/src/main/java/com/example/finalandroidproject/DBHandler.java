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
        super(context, "properties.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PROPERTY_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PROPERTY_NAME + " TEXT, " + COLUMN_PROPERTY_ADDRESS + " TEXT, " + COLUMN_PROPERTY_CITY + " TEXT, " + COLUMN_PROPERTY_REGION + " TEXT, " + COLUMN_PROPERTY_DESC + " TEXT )";

        db.execSQL(createTableStatement);
        //dummyData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROPERTY_TABLE);
        onCreate(db);
    }

    public void dummyData(){
        try (SQLiteDatabase db = this.getWritableDatabase()){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PROPERTY_NAME, "Hosta House"  );
            cv.put(COLUMN_PROPERTY_ADDRESS,"3192 Avenue Napolean" );
            cv.put(COLUMN_PROPERTY_CITY, "Toronto, Canada");
            cv.put(COLUMN_PROPERTY_REGION, "Ontario");
            cv.put(COLUMN_PROPERTY_DESC,"Corner Cottage" );
            db.insert(PROPERTY_TABLE, null, cv);
            /*
            cv.put(COLUMN_PROPERTY_NAME, "Russell Villa"  );
            cv.put(COLUMN_PROPERTY_ADDRESS," VIALE EUROPA 22");
            cv.put(COLUMN_PROPERTY_CITY, "Turin");
            cv.put(COLUMN_PROPERTY_REGION, "Italy");
            cv.put(COLUMN_PROPERTY_DESC,"Ivy Cottage" );
            db.insert(PROPERTY_TABLE, null, cv);
            cv.put(COLUMN_PROPERTY_NAME, "Casa De Canto"  );
            cv.put(COLUMN_PROPERTY_ADDRESS,"Paseode Puerta del Angel, 1" );
            cv.put(COLUMN_PROPERTY_CITY, "Madrid");
            cv.put(COLUMN_PROPERTY_REGION, "Spain");
            cv.put(COLUMN_PROPERTY_DESC,"Park" );
            db.insert(PROPERTY_TABLE, null, cv);
            cv.put(COLUMN_PROPERTY_NAME, "La Petite Maison"  );
            cv.put(COLUMN_PROPERTY_ADDRESS,"1300 Brickell Bay" );
            cv.put(COLUMN_PROPERTY_CITY, "Miami");
            cv.put(COLUMN_PROPERTY_REGION, "Florida, United-States of America");
            cv.put(COLUMN_PROPERTY_DESC,"Restaurant & Bar" );
            db.insert(PROPERTY_TABLE, null, cv);
            cv.put(COLUMN_PROPERTY_NAME, "El Paradiso"  );
            cv.put(COLUMN_PROPERTY_ADDRESS,"12820 SW 120th St" );
            cv.put(COLUMN_PROPERTY_CITY, "Miami");
            cv.put(COLUMN_PROPERTY_REGION, "Florida, United-States of America");
            cv.put(COLUMN_PROPERTY_DESC,"Restaurant & Bar" );
            db.insert(PROPERTY_TABLE, null, cv);
             */
        }
    }

    public PropertyModel searchForSpecificPropertyByName(String propertyNameString){

        PropertyModel propertyModel = null;

        String queryString = "SELECT * FROM " + PROPERTY_TABLE + " WHERE " + COLUMN_PROPERTY_NAME + " = ?";

        try (SQLiteDatabase db = this.getReadableDatabase();) {
            try (Cursor cursor = db.rawQuery(queryString, new String[] {propertyNameString});){
                if (cursor.moveToFirst()) {
                    int propertyID = cursor.getInt(0);
                    String propertyName = cursor.getString(1);
                    String propertyAddress = cursor.getString(2);
                    String propertyCity = cursor.getString(3);
                    String propertyRegion = cursor.getString(4);
                    String propertyDesc = cursor.getString(5);

                    propertyModel = new PropertyModel(propertyID,propertyName,propertyAddress,propertyCity,propertyRegion, propertyDesc);
                }
            }
        }
        return  propertyModel;
    }

    public void addOne(PropertyModel propertyModel){

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PROPERTY_NAME, propertyModel.getName());
            cv.put(COLUMN_PROPERTY_ADDRESS, propertyModel.getAddress());
            cv.put(COLUMN_PROPERTY_CITY, propertyModel.getCity());
            cv.put(COLUMN_PROPERTY_REGION, propertyModel.getRegion());
            cv.put(COLUMN_PROPERTY_DESC, propertyModel.getDescription());
            db.insert(PROPERTY_TABLE, null, cv);
        }


    }

    public String[] getName() {

        String queryString = "SELECT * FROM " + PROPERTY_TABLE;

        try ( SQLiteDatabase db = this.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery(queryString, null)) {

                int n = cursor.getCount();
                cursor.moveToFirst();
                String[] column = new String[n];
                int i = 0;

                if (n != 0){
                    do {
                        column[i] = cursor.getString(cursor.getColumnIndex(COLUMN_PROPERTY_NAME));
                        i++;
                    } while (cursor.moveToNext());
                }
                return column;
            }
        }
    }

    /*
    public List<PropertyModel> getAll() {

        List<PropertyModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PROPERTY_TABLE;

        try( SQLiteDatabase db = this.getReadableDatabase();){
            try( Cursor cursor = db.rawQuery(queryString, null);){
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
            }
        }
        return returnList;
    }
     */

    public void  deleteOne(String propertyName){
        try (SQLiteDatabase db = this.getWritableDatabase();){
            db.delete(PROPERTY_TABLE, COLUMN_PROPERTY_NAME + "=?", new String[]{propertyName});
        }
    }
}
