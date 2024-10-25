package com.example.appyogademo.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.Repository.YogaClassRepository;

import java.util.List;

public class YogaClassViewModel extends ViewModel {
    private final YogaClassRepository repository;
    private final LiveData<List<YogaClass>> yogaClasses;

    public YogaClassViewModel(Context context) {
        repository = new YogaClassRepository(context);
        yogaClasses = repository.getAllYogaClasses();
    }

    public LiveData<List<YogaClass>> getYogaClasses() {
        return yogaClasses;
    }

    public void insert(YogaClass yogaClass) {
        repository.insert(yogaClass);
    }

    public void update(YogaClass yogaClass) {
        repository.update(yogaClass);
    }

    public void delete(YogaClass yogaClass) {
        repository.delete(yogaClass);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(YogaClassViewModel.class)) {
                return (T) new YogaClassViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
