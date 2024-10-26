package com.example.appyogademo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appyogademo.Models.User;

public class UserHelper {
    private SQLiteDatabase db;
    private Context context;

    public UserHelper(SQLiteDatabase db) {
        this.db = db;
    }

    // thêm 1 user vào cơ sở dữ liệu
    public void insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_ID, user.getId());
        values.put(User.COLUMN_FULLNAME, user.getFullName());
        values.put(User.COLUMN_PHONENUMBER, user.getPhoneNumber());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_IMAGE, user.getImage());
        values.put(User.COLUMN_ADDRESS, user.getAddress());
        values.put(User.COLUMN_EXPERIENCE, user.getExperience());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        values.put(User.COLUMN_ROLE, user.getRole());
        db.insert(User.TABLE_NAME, null, values);
    }

    // cập nhật thông tin của một user
    public Boolean updateUser(User user) {
        if(checkEmail(user.getEmail())) {
            return false;
        }

        else if(checkPhone(user.getPhoneNumber())) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_FULLNAME, user.getFullName());
        values.put(User.COLUMN_PHONENUMBER, user.getPhoneNumber());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_IMAGE, user.getImage());
        values.put(User.COLUMN_ADDRESS, user.getAddress());
        values.put(User.COLUMN_EXPERIENCE, user.getExperience());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        values.put(User.COLUMN_ROLE, user.getRole());
        db.update(User.TABLE_NAME, values, User.COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
        return true;
    }

    // xóa một user khỏi cơ sở dữ liệu
    public void deleteUser(User user) {
        db.delete(User.TABLE_NAME, User.COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public Boolean checkEmail(String email) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkPhone(String phoneNumber) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE phoneNumber = ?", new String[]{phoneNumber});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmailPassword(String email, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}