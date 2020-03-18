package com.example.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.schedule.models.Favorite;
import com.example.schedule.models.IDataBase;

public class ScheduleDB extends SQLiteOpenHelper implements IDataBase {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scheduleDB";
    private static final String TABLE_USER = "user";
    private static final String TABLE_FAVORITE = "favorite";
    private static final String KEY_ID = "id";
    private static final String KEY_isStud = "isStudent";
    private static final String KEY_LABEL = "label";
    private static final String KEY_DESC = "description";
    private static final String KEY_TYPE = "type";
    private static final String KEY_isDef = "isDefault";

    public ScheduleDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_isStud + " BIT" + ")";
        String CREATE_FAVORITE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LABEL + " VARCHAR, " +
                KEY_DESC + " TEXT, " + KEY_TYPE + " VARCHAR, " + KEY_isDef + " BIT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }

    public void setUser(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_isStud, type);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean getUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,
                        KEY_isStud}, KEY_isStud, null, null, null, KEY_ID + " DESC ", " 1 ");

        if (cursor != null){
            cursor.moveToFirst();
        }

        boolean user = Boolean.parseBoolean(cursor.getString(0));

        return user;
    }

    public void setFavorite(Favorite favoriteItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_LABEL, favoriteItem.getLabel());
        values.put(KEY_DESC, favoriteItem.getDescription());
        values.put(KEY_TYPE, favoriteItem.getType());
        values.put(KEY_isDef, favoriteItem.getDef());

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
    }

    public int updateFavorite(int id, String type, boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, type);
        values.put(KEY_isDef, isDefault);

        return db.update(TABLE_FAVORITE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void removeFavorite(int id, String type){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
