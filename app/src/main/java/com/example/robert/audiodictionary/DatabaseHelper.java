package com.example.robert.audiodictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by billk on 11/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VER = 5;
    private SQLiteDatabase database;
    public static final String DATABASE_NAME = "sound.db";

    public static final String TABLE_NAME = "tbl2";
    public static final String COLUMN_WORD = "word"; // word

    public static final String COLUMN_NAME = "name"; // user name
    public static final String COLUMN_REGION = "location"; // user region
   // public static final String COLUMN_ID = "deviceId"; // user's device id

    public static final String COLUMN_RECORDING= "recording"; // blob used to store sound file

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_WORD + " VARCHAR," +
                COLUMN_NAME + " VARCHAR," + COLUMN_REGION+ " VARCHAR," + COLUMN_RECORDING + " VARCHAR );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long insertEntry(EntryTable contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD,contact.getWord());
       // contentValues.put(COLUMN_ID,contact.getDeviceId());
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_REGION, contact.getRegion());
        contentValues.put(COLUMN_RECORDING,contact.getSoundLocation());

        long res = database.insert(TABLE_NAME, null, contentValues);
        if(res != -1) {
            database.close();
            return 1;
        }else {
            return 0;
        }
    }

    public ArrayList<EntryTable> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<EntryTable> contacts = new ArrayList<EntryTable>();
        EntryTable entryTable;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                entryTable = new EntryTable();
                entryTable.setWord(cursor.getString(0));
                entryTable.setName(cursor.getString(1));
//                entryTable.setSoundConverted(cursor.getBlob(cursor.getCount()-1));
                contacts.add(entryTable);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }

    public int updateEntry(EntryTable contact){

        String where = "name = '"+contact.getName()+"' AND word = '"+ contact.getWord()+"'";
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD,contact.getWord());
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_REGION, contact.getRegion());
        contentValues.put(COLUMN_RECORDING,contact.getSoundLocation());

        return database.update(TABLE_NAME,contentValues,where,null);

    }




    public  boolean checkIfExists(String TableName,String wordVal, String nameVal) {
        database = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where word = '" + wordVal + "' AND name = '"  + nameVal+"'";

        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public  ArrayList<EntryTable> checkWordEntries(String wordVal) {
        database = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where word = '" + wordVal +"'";
        ArrayList<EntryTable> selections = new ArrayList<>();
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor != null ) {
            while(cursor.moveToNext()) {
                EntryTable temp = new EntryTable();
                temp.setWord(cursor.getString(cursor.getColumnIndex("word")));
                temp.setName(cursor.getString(cursor.getColumnIndex("name")));
                temp.setSoundLocation(cursor.getString(cursor.getColumnIndex("recording")));
                temp.setRegion(cursor.getString(cursor.getColumnIndex("location")));
                selections.add(temp);
                Log.d("hey","cursor.move worked out");
            }
            return selections;
        }else {
            cursor.close();
            return null;
        }
    }


}
