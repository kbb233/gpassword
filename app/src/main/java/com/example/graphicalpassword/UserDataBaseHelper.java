package com.example.graphicalpassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public static final String TABLE_LOGIN_INFO = "login_info";
    public static final String LOGIN_ID = "_id";
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_IP_ADDRESS = "ip_address";
    public static final String LOGIN_DATE_TIME = "date_time";
    public static final String LOGIN_DEVICE = "device";
    public static final String LOGIN_OS = "os";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, "
            + COLUMN_TEXT_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_GRAPH_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_PROFILE + " TEXT, "
            + COLUMN_QUESTION + " TEXT, "
            + COLUMN_ANSWER + " TEXT);";

    private static final String CREATE_LOGIN_INFO_TABLE = "CREATE TABLE " + TABLE_LOGIN_INFO + " (" +
            LOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LOGIN_USERNAME + " TEXT, " +
            LOGIN_IP_ADDRESS + " TEXT, " +
            LOGIN_DATE_TIME + " TEXT, " +
            LOGIN_DEVICE + " TEXT, " +
            LOGIN_OS + " TEXT)";

    public UserDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(CREATE_LOGIN_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_INFO);
        onCreate(db);
    }

    public Cursor getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        return cursor;
    }

    public int  updateUser(String username, String password, String graphicalPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEXT_PASSWORD, password);
        contentValues.put(COLUMN_GRAPH_PASSWORD, graphicalPassword);

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = new String[]{username};

        int affectedRows = db.update(TABLE_USERS, contentValues, whereClause, whereArgs);
        db.close();

        return affectedRows;
    }

    public void addLoginInfo(String username, String ipAddress, String dateTime, String device, String os) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOGIN_USERNAME, username);
        values.put(LOGIN_IP_ADDRESS, ipAddress);
        values.put(LOGIN_DATE_TIME, dateTime);
        values.put(LOGIN_DEVICE, device);
        values.put(LOGIN_OS, os);

        db.insert(TABLE_LOGIN_INFO, null, values);
        db.close();
    }

    public List<LogInfo> getAllLoginInfo(String username) {
        List<LogInfo> loginInfoList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                LOGIN_ID,
                LOGIN_USERNAME,
                LOGIN_IP_ADDRESS,
                LOGIN_DATE_TIME,
                LOGIN_DEVICE,
                LOGIN_OS
        };

        String selection = LOGIN_USERNAME + "=?";
        String[] selectionArgs = {username};
        String order = LOGIN_DATE_TIME + " DESC";

        Cursor cursor = db.query(
                TABLE_LOGIN_INFO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(LOGIN_ID));
                String ipAddress = cursor.getString(cursor.getColumnIndex(LOGIN_IP_ADDRESS));
                String dateTime = cursor.getString(cursor.getColumnIndex(LOGIN_DATE_TIME));
                String device = cursor.getString(cursor.getColumnIndex(LOGIN_DEVICE));
                String operatingSystem = cursor.getString(cursor.getColumnIndex(LOGIN_OS));

                LogInfo loginInfo = new LogInfo(id, username, ipAddress, dateTime, device, operatingSystem);
                loginInfoList.add(loginInfo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return loginInfoList;
    }


}
