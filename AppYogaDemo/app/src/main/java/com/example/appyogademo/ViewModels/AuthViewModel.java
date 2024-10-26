package com.example.appyogademo.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appyogademo.Models.User;
import com.example.appyogademo.Repository.AuthRepository;
import com.example.appyogademo.Repository.Result;

public class AuthViewModel extends ViewModel {
    private final AuthRepository repository;
    private final Context context;
    public AuthViewModel(Context context) {
        this.context = context;
        repository = new AuthRepository(context);
    }

    public boolean login(String email, String password) {
        Result result = repository.login(email, password);
        if(result.isStatus()) {
            Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            return true;
        } else{
            Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean register(User user) {
        try {
            Result result = repository.register(user);
            if(result.isStatus()) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            } else{
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("SignUp failed", e.getMessage());
            return false;
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AuthViewModel.class)) {
                return (T) new AuthViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
