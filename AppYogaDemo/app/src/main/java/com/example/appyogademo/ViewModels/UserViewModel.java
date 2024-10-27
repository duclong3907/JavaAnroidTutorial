package com.example.appyogademo.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appyogademo.Models.User;
import com.example.appyogademo.Repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<User>> allTeachers;
    private final Context context;

    public UserViewModel(Context context) {
        this.context = context;
        userRepository = new UserRepository(context);
        allTeachers = userRepository.getAllTeachers();
    }

    public LiveData<List<User>> getAllTeachers() {
        return allTeachers;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(UserViewModel.class)) {
                return (T) new UserViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}