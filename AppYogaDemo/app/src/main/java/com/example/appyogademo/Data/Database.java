package com.example.appyogademo.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Models.User;
import com.example.appyogademo.Models.YogaClass;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yoga_app.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Course.CREATE_TABLE);
        db.execSQL(YogaClass.CREATE_TABLE);
        db.execSQL(User.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + YogaClass.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(db);
    }
}
