package com.example.schedule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.schedule.models.Favorite;
import com.example.schedule.models.IDataBase;
import com.example.schedule.models.UserType;

public class ScheduleDB implements IDataBase {

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

    private  SQLiteDatabase db;

    public ScheduleDB(SQLiteDatabase db) {
        this.db = db;
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_isStud + " TINYINT" + ")";
        String CREATE_FAVORITE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LABEL + " VARCHAR, " +
                KEY_DESC + " TEXT, " + KEY_TYPE + " VARCHAR, " + KEY_isDef + " BIT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    public void setUser(UserType type){
        ContentValues values = new ContentValues();
        values.put(KEY_isStud, type.toString());

        db.insert(TABLE_USER, null, values);
    }

    public UserType getUser(){

        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        UserType user = UserType.NONE;
        if(query.moveToFirst()){
            user = UserType.valueOf(query.getString(1));
        }

        return user;
    }
    public void setFavorite(Favorite favoriteItem){
        ContentValues values = new ContentValues();

        values.put(KEY_LABEL, favoriteItem.label);
        values.put(KEY_DESC, favoriteItem.description);
        values.put(KEY_TYPE, favoriteItem.type.toString());
        values.put(KEY_isDef, favoriteItem.isDefault);

        db.insert(TABLE_FAVORITE, null, values);
        //db.close();

//        db.execSQL("INSERT INTO "+ TABLE_FAVORITE + " VALUES (" + favoriteItem.label + ", "
//        + favoriteItem.description + ", " + favoriteItem.type + ", " + favoriteItem.isDefault + ")");
    }

    public int updateFavorite(int id, String type, boolean isDefault){
        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, type);
        values.put(KEY_isDef, isDefault);

        return db.update(TABLE_FAVORITE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void removeFavorite(int id, String type){
        db.delete(TABLE_FAVORITE, KEY_ID + " = ?", new String[] { String.valueOf(id) });
    }
}
