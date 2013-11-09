package com.fortytwo.discipline;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "fortytwo";
    private static final String SENSOR_TABLE_NAME = "sensor_history";
    
    private static final String KEY_MAC = "mac";
    private static final String KEY_TIME = "last_timestamp";
    private static final String KEY_STORE = "store_name";

    
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + SENSOR_TABLE_NAME + " (" +
                		KEY_MAC + " TEXT, " +
                		KEY_TIME + " TIMESTAMP, "+
    					KEY_STORE + " TEXT, PRIMARY KEY("+KEY_MAC+"));";
    
	
	public DatabaseConnector(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int verion1, int version2) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + SENSOR_TABLE_NAME);
	// Create tables again
	onCreate(db);

	}
	
	public void addSensor(Sensor sensor) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	    ContentValues values = new ContentValues();
	    values.put(KEY_MAC, sensor.getBSSID()); 
	    values.put(KEY_TIME,dateFormat.format(new Date()));
	    values.put(KEY_STORE, sensor.getShopname());
	 
	    
	    
	    db.insert(SENSOR_TABLE_NAME, null, values);
	    db.close(); 
	}

	public boolean checkSensor(Sensor s) {
		boolean result = false;
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(SENSOR_TABLE_NAME, new String[] { KEY_MAC,
	            KEY_TIME, KEY_STORE }, KEY_MAC + "=?",
	            new String[] { s.getBSSID() }, null, null, null, null);
	    if (cursor != null && cursor.getCount()>0){
	        cursor.moveToFirst();
	        result = true;
		    Sensor sensor = new Sensor(cursor.getString(0),
		            s.getRSSI(), cursor.getString(2));
		    
		    sensor.setDate(new Date(cursor.getString(1)));
	    }else{
	    	result = false;
	    }

	    return result;
	}
	
	public int updateContact(Sensor sensor) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	    ContentValues values = new ContentValues();
	    values.put(KEY_MAC, sensor.getBSSID()); 
	    values.put(KEY_TIME,dateFormat.format(new Date()));
	    values.put(KEY_STORE, sensor.getShopname());
	 
	    int res = db.update(SENSOR_TABLE_NAME, values, KEY_MAC + " = ?",
	            new String[] { String.valueOf(sensor.getBSSID()) });
	    db.close();
	    
	    return res;
	}
	
}
