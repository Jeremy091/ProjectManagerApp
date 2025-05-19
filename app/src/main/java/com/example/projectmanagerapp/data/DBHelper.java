package com.example.projectmanagerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "project_manager.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + Contract.UserEntry.TABLE_NAME + " (" +
                Contract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.UserEntry.COLUMN_USERNAME + " TEXT UNIQUE, " +
                Contract.UserEntry.COLUMN_PASSWORD + " TEXT)";

        String createProjects = "CREATE TABLE " + Contract.ProjectEntry.TABLE_NAME + " (" +
                Contract.ProjectEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.ProjectEntry.COLUMN_NAME + " TEXT, " +
                Contract.ProjectEntry.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.ProjectEntry.COLUMN_START_DATE + " TEXT, " +
                Contract.ProjectEntry.COLUMN_END_DATE + " TEXT, " +
                Contract.ProjectEntry.COLUMN_USER_ID + " INTEGER, " +
                "FOREIGN KEY(" + Contract.ProjectEntry.COLUMN_USER_ID + ") REFERENCES " +
                Contract.UserEntry.TABLE_NAME + "(" + Contract.UserEntry.COLUMN_ID + ") ON DELETE CASCADE)";

        String createTasks = "CREATE TABLE " + Contract.TaskEntry.TABLE_NAME + " (" +
                Contract.TaskEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TaskEntry.COLUMN_TITLE + " TEXT, " +
                Contract.TaskEntry.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.TaskEntry.COLUMN_START_DATE + " TEXT, " +
                Contract.TaskEntry.COLUMN_END_DATE + " TEXT, " +
                Contract.TaskEntry.COLUMN_STATUS + " TEXT, " +
                Contract.TaskEntry.COLUMN_PROJECT_ID + " INTEGER, " +
                "FOREIGN KEY(" + Contract.TaskEntry.COLUMN_PROJECT_ID + ") REFERENCES " +
                Contract.ProjectEntry.TABLE_NAME + "(" + Contract.ProjectEntry.COLUMN_ID + ") ON DELETE CASCADE)";

        db.execSQL(createUsers);
        db.execSQL(createProjects);
        db.execSQL(createTasks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.ProjectEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}