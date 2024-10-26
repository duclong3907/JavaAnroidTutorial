package com.example.appyogademo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.appyogademo.Data.Database;
import com.example.appyogademo.Data.UserHelper;
import com.example.appyogademo.Models.User;

public class AuthRepository {
    private final UserHelper userHelper;

    public AuthRepository(Context context) {
        SQLiteDatabase db = new Database(context).getWritableDatabase();
        userHelper = new UserHelper(db);
    }

    public Result login(String email, String password) {
        return email.isEmpty() || password.isEmpty()
                ? new Result(false, "Email or password is empty")
                : userHelper.checkEmailPassword(email, password)
                ? new Result(true, "Login successfully")
                : new Result(false, "Email or password is incorrect");
    }

    public Result register(User user) {
        if (userHelper.checkEmail(user.getEmail())) {
            return new Result(false, "Email already exists");
        } else if (userHelper.checkPhone(user.getPhoneNumber())) {
            return new Result(false, "Phone already exists");
        } else {
            userHelper.insertUser(user);
            return new Result(true, "Register Successfully");
        }
    }

}
