package com.example.laba4.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.laba4.Database.DatabaseHelper;
import com.example.laba4.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentStorage {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "student";
    final String COLUMN_ID = "studentid";
    final String COLUMN_NAME = "student_name";
    final String COLUMN_AGE = "age";
    final String COLUMN_IS_STUDING = "isstuding";

    public StudentStorage(Context context) {
        sqlHelper = new DatabaseHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public StudentStorage open(){
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public List<Student> getFullList() {
        Cursor cursor = db.rawQuery("select * from " + TABLE, null);
        List<Student> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Student obj = new Student();
            obj.id = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.age = cursor.getInt((int) cursor.getColumnIndex(COLUMN_AGE));
            obj.isStuding = Boolean.parseBoolean(cursor.getString((int) cursor.getColumnIndex(COLUMN_IS_STUDING)));
            obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            list.add(obj);
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
        return list;
    }

    public List<Student> getFilteredList(Student model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE, null);
        List<Student> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Student obj = new Student();
            obj.id = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.age = cursor.getInt((int) cursor.getColumnIndex(COLUMN_AGE));
            obj.isStuding = Boolean.parseBoolean(cursor.getString((int) cursor.getColumnIndex(COLUMN_IS_STUDING)));
            obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            list.add(obj);
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
        return list;
    }

    public Student getElement(Student model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where "
                + COLUMN_ID + " = " + model.id, null);
        Student obj = new Student();
        if (!cursor.moveToFirst()) {
            return null;
        }
        obj.id = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
        obj.age = cursor.getInt((int) cursor.getColumnIndex(COLUMN_AGE));
        obj.isStuding = Boolean.parseBoolean(cursor.getString((int) cursor.getColumnIndex(COLUMN_IS_STUDING)));
        obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
        return obj;
    }

    public void insert(Student model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_AGE,model.age);
        content.put(COLUMN_IS_STUDING,model.isStuding);
        content.put(COLUMN_NAME,model.name);
        db.insert(TABLE,null,content);
    }

    public void update(Student model) {
        ContentValues content=new ContentValues();
        content.put(COLUMN_ID,model.id);
        content.put(COLUMN_IS_STUDING,model.isStuding);
        content.put(COLUMN_NAME,model.name);
        content.put(COLUMN_AGE,model.age);
        String where = COLUMN_ID+" = "+model.id;
        db.update(TABLE,content,where,null);
    }

    public void delete(Student model) {
        String where = COLUMN_ID+" = "+model.id;
        db.delete(TABLE,where,null);
    }

    public void delete() {
        db.delete(TABLE,null,null);
    }

    public int getElementCount(){
        return (int)DatabaseUtils.queryNumEntries(db, TABLE);
    }
}
