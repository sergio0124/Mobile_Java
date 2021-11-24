package com.example.laba4.Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Students.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS student (\n" +
                "    studentid integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    student_name character(100),\n" +
                "    age integer,\n" +
                "    isstuding bit);\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
        onCreate(db);
    }

}
