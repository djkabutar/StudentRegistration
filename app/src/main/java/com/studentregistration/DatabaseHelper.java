package com.studentregistration;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table stud(username text, password text, name text" +
                ", address text, email text, dob text, phone bigint)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists stud");
    }

    public boolean insert(Student s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", s.getUsername());
        contentValues.put("password", s.getPassword());
        contentValues.put("name", s.getName());
        contentValues.put("address", s.getAddress());
        contentValues.put("email", s.getEmail());
        contentValues.put("dob", s.getDob());
        contentValues.put("phone", s.getPhone());

        long result = db.insert("stud", null, contentValues);
        return result > -1;
    }

    public boolean update(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", password);

        boolean flag = false;

        Cursor cursor = db.rawQuery("select * from stud where username=?", new String[] {username});

        if (cursor.moveToFirst()){
            do{
                long result = db.update("stud", contentValues, "username=?", new String[] {username});
                flag = result > -1;

                if (!flag) break;
            }while(cursor.moveToNext());
        }
        cursor.close();

        return flag;
    }

    @SuppressLint("Range")
    public ArrayList<Student> get(String username, String password) {
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        boolean flag = false;

        Cursor cursor = db.rawQuery("select * from stud where username=? and password=?",
                new String[] {username, password});

        boolean result = cursor.getCount() <= 0;
        if (!result) {
            do {
                String user = cursor.getString(cursor.getColumnIndex("username"));
                String pass = cursor.getString(cursor.getColumnIndex("password"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String dob = cursor.getString(cursor.getColumnIndex("dob"));
                long phone = cursor.getLong(cursor.getColumnIndex("phone"));
                students.add(new Student(user, pass, name, address, email, dob, phone));
            } while(cursor.moveToNext());
        }
        return students;
    }

}

