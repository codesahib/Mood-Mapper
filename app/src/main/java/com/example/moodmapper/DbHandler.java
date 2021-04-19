package com.example.moodmapper;

import android.content.ContentValues; // Special class that works as HashMap
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHandler extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "userdata";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BORED = "bored";
    public static final String COLUMN_LETHARGIC = "lethargic";
    public static final String COLUMN_PRODUCTIVE = "productive";

    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_DATE + " TEXT PRIMARY KEY, " + COLUMN_BORED + " INTEGER, " + COLUMN_LETHARGIC + " INTEGER, " + COLUMN_PRODUCTIVE + " INTEGER)";
        db.execSQL(create);
    }

    // In case the app version changes, it ensures that prev version's DB gets updated with new DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS " + TABLE_NAME ;
        db.execSQL(drop);
        onCreate(db);
    }

    public boolean addRecord(Record r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,r.getDate());
        values.put(COLUMN_BORED,r.getBored());
        values.put(COLUMN_LETHARGIC,r.getLethargic());
        values.put(COLUMN_PRODUCTIVE,r.getProductive());
        long insert = db.insert(TABLE_NAME,null, values);
        Log.d("mytag insert",Long.toString(insert));
        db.close();

        return insert != -1;
    }

    public Cursor getRecord(String this_date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null ,
                "date=?", new String[]{this_date} ,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            Log.d("mytag get1", cursor.getString(0));
            Log.d("mytag get2", cursor.getString(1));
            Log.d("mytag get3", cursor.getString(2));
            Log.d("mytag get4", cursor.getString(3));
            return cursor;
        }
        else{
            Log.d("mytag get error","Error occured");
            return null;
        }
    }

    public Cursor getRecordBetweenRange(String from_date, String to_date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null ,
                "date BETWEEN ? AND ?", new String[]{from_date,to_date} ,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            Log.d("mytag get", cursor.getString(0));
            Log.d("mytag get", cursor.getString(1));
            Log.d("mytag get", cursor.getString(2));
            Log.d("mytag get", cursor.getString(3));
            return cursor;
        }
        else{
            Log.d("mytag get error","Error occured");
            return null;
        }
    }

    public void updateRecord(String this_date, String mood, int mood_count){
        Log.d("update_record",this_date+" "+mood+" "+mood_count);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mood,mood_count);
        db.update(TABLE_NAME,values,"date=?",new String[]{this_date});
    }

    public void removeTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        String drop = "DROP TABLE IF EXISTS " + TABLE_NAME ;
        db.execSQL(drop);
        onCreate(db);
    }
}

