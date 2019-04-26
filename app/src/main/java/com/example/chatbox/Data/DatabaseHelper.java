package com.example.chatbox.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.io.InvalidObjectException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chat";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + DatabaseContract.Users.TABLE_NAME
            + " (" + DatabaseContract.Users._ID + " INTEGER PRIMARY KEY,"
            + DatabaseContract.Users.USERNAME + " TEXT,"
            + DatabaseContract.Users.PASSWORD + " TEXT,"
            + DatabaseContract.Users.FULLNAME + " TEXT);";
    private static final String DELETE_USER_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseContract.Users.TABLE_NAME;

    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " + DatabaseContract.Messages.TABLE_NAME
            + " (" + DatabaseContract.Messages._ID + " INTEGER PRIMARY KEY,"
            + DatabaseContract.Messages.MESSAGE_TO + " TEXT,"
            + DatabaseContract.Messages.MESSAGE_FROM + " TEXT,"
            + DatabaseContract.Messages.MESSAGE + " TEXT,"
            + DatabaseContract.Messages.MESSAGE_CREATED_AT + " TEXT);";
    private static final String DELETE_MESSAGES_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseContract.Messages.TABLE_NAME;

    private Context context;
    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
try {
    db.execSQL(CREATE_USER_TABLE);
    db.execSQL(CREATE_MESSAGE_TABLE);
}catch (Exception e){
    Toast.makeText(context, "Error while creating table..", Toast.LENGTH_SHORT).show();
}

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_USER_ENTRIES);
        db.execSQL(DELETE_MESSAGES_ENTRIES);
        onCreate(db);
    }
}
