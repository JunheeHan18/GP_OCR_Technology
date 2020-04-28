package com.example.mobileproject;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SimpleDatabaseHelper extends SQLiteOpenHelper {

    SimpleDatabaseHelper(Context context, String db_name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, db_name, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDataWithoutPrimaryKey(SQLiteDatabase database, String id, String nickname, String password) {
        if (database != null) {
            database.execSQL("INSERT INTO userdata(id, nickname, password) VALUES"
                    + "(" + "'" + id + "'" + ", " + "'" + nickname + "'" + ", " + "'" + password + "'" + ");");
        }
    }

    public void insertData(SQLiteDatabase database, int user_number, String id, String nickname, String password) {
        if (database != null) {
            database.execSQL("INSERT INTO userdata(user_number, id, nickname, password) VALUES"
                    + "(" + String.valueOf(user_number) + ", " + "'" + id + "'" + ", " + "'" + nickname + "'" + ", " + "'" + password + "'" + ");");
        }
    }
}