package com.example.yoga_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "yoga-app.db";
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, fullName TEXT, phoneNumber TEXT, email TEXT, image TEXT, address TEXT, experience INTEGER, password TEXT, role TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public Boolean insert(String fullName, String phoneNumber, String email,
                          @Nullable String image, @Nullable String address, @Nullable Integer experience,
                          String password, String role) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName", fullName);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("email", email);
        contentValues.put("image", image);
        contentValues.put("address", address);
        contentValues.put("experience", experience);
        contentValues.put("password", password);
        contentValues.put("role", role);

        Log.d(TAG, "Inserting data: " + contentValues.toString());

        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            Log.e(TAG, "Failed to insert data");
            return false;
        } else {
            Log.d(TAG, "Data inserted successfully");
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkPhone(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE phoneNumber = ?", new String[]{phoneNumber});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        Log.d(TAG, "checkEmailPassword: email=" + email + ", password=" + password + ", exists=" + exists);
        cursor.close();
        return exists;
    }
}