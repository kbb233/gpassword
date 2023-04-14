package com.example.graphicalpassword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_SERIAL_NUMBER = "serial_number";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_TEXT_PASSWORD = "text_password";
    public static final String COLUMN_GRAPH_PASSWORD = "graph_password";
    public static final String COLUMN_PROFILE = "profile";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, "
            + COLUMN_TEXT_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_GRAPH_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_PROFILE + " TEXT, "
            + COLUMN_QUESTION + " TEXT, "
            + COLUMN_ANSWER + " TEXT);";

    public UserDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
