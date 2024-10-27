package com.example.appyogademo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appyogademo.Data.Database;
import com.example.appyogademo.Data.UserHelper;
import com.example.appyogademo.Models.User;

import java.util.List;

public class UserRepository {
    private final UserHelper userHelper;
    private final MutableLiveData<List<User>> allTeachers;

    public UserRepository(Context context) {
        SQLiteDatabase db = new Database(context).getWritableDatabase();
        userHelper = new UserHelper(db);
        allTeachers = new MutableLiveData<>();
        loadAllTeachers();
    }

    // Method to load all teachers into LiveData
    private void loadAllTeachers() {
        List<User> teachers = userHelper.getAllTeachers();
        allTeachers.postValue(teachers);
    }

    // Returns LiveData for all teachers
    public LiveData<List<User>> getAllTeachers() {
        return allTeachers;
    }

    // Method to add a new user
    public void insertUser(User user) {
        userHelper.insertUser(user);
        loadAllTeachers(); // Refresh the data
    }

    // Method to update a user
    public boolean updateUser(User user) {
        boolean updated = userHelper.updateUser(user);
        if (updated) {
            loadAllTeachers(); // Refresh the data
        }
        return updated;
    }

    // Method to delete a user
    public void deleteUser(User user) {
        userHelper.deleteUser(user);
        loadAllTeachers(); // Refresh the data
    }

    // Method to get a user by ID
    public User getUserById(String userId) {
        return userHelper.getUser(userId);
    }
}
