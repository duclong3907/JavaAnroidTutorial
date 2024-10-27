package com.example.appyogademo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appyogademo.Models.User;
import com.example.appyogademo.Repository.Result;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {
    private SQLiteDatabase db;
    private Context context;

    User user = new User("admin", "Đức Long", "0123456789", "dlongminhtan@gmail.com", null, null, 5, "Long3907@mt", "Admin");
    User user1 = new User("teacher1", "Nguyễn Thanh An", "0123456789", "ancute3907@gmail.com", null, null, 5, "Long3907@mt", "Teacher");
    User user2 = new User("teacher2", "Trần Đức Long", "0123496789", "dlongmt2k3@gmail.com", null, null, 6, "Long3907@mt", "Teacher");
    User user3 = new User("teacher3", "Nguyễn Hoàng Minh", "0123456759", "chatgpt3907@gmail.com", null, null, 5, "Long3907@mt", "Teacher");

    public UserHelper(SQLiteDatabase db) {
        this.db = db;
//        insertUser(user);
//        insertUser(user1);
//        insertUser(user2);
//        insertUser(user3);
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

    public Result checkEmailPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        if(cursor.getCount() > 0) {
            Cursor checkRole = db.rawQuery(query+ "AND role = 'Admin'", new String[]{email, password});
            if(checkRole.getCount() > 0) {
                return new Result(true, "Login successfully");
            } else {
                return new Result(false, "Your account is not admin");
            }
        }
        cursor.close();
        return new Result(false, "Email or password is incorrect");
    }

    // Get all users with the role "Teacher"
    public List<User> getAllTeachers() {
        List<User> teachers = new ArrayList<>();
        String query = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_ROLE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"Teacher"});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(User.COLUMN_ID);
                int fullNameIndex = cursor.getColumnIndex(User.COLUMN_FULLNAME);
                int phoneNumberIndex = cursor.getColumnIndex(User.COLUMN_PHONENUMBER);
                int emailIndex = cursor.getColumnIndex(User.COLUMN_EMAIL);
                int imageIndex = cursor.getColumnIndex(User.COLUMN_IMAGE);
                int addressIndex = cursor.getColumnIndex(User.COLUMN_ADDRESS);
                int experienceIndex = cursor.getColumnIndex(User.COLUMN_EXPERIENCE);
                int passwordIndex = cursor.getColumnIndex(User.COLUMN_PASSWORD);
                int roleIndex = cursor.getColumnIndex(User.COLUMN_ROLE);

                if (idIndex >= 0 && fullNameIndex >= 0 && phoneNumberIndex >= 0 && emailIndex >= 0 &&
                        imageIndex >= 0 && addressIndex >= 0 && experienceIndex >= 0 && passwordIndex >= 0 && roleIndex >= 0) {

                    String id = cursor.getString(idIndex);
                    String fullName = cursor.getString(fullNameIndex);
                    String phoneNumber = cursor.getString(phoneNumberIndex);
                    String email = cursor.getString(emailIndex);
                    String image = cursor.getString(imageIndex);
                    String address = cursor.getString(addressIndex);
                    int experience = cursor.getInt(experienceIndex);
                    String password = cursor.getString(passwordIndex);
                    String role = cursor.getString(roleIndex);

                    User teacher = new User(id, fullName, phoneNumber, email, image, address, experience, password, role);
                    teachers.add(teacher);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return teachers;
    }

}