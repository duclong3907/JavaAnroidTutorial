package com.example.appyogademo.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Repository.CourseRepository;

import java.util.List;

public class CourseViewModel extends ViewModel {
    private final CourseRepository repository;
    private final LiveData<List<Course>> courses;

    public CourseViewModel(Context context) {
        repository = new CourseRepository(context);
        courses = repository.getAllCourses();
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public void insert(Course course) {
        repository.insert(course);
    }

    public void update(Course course) {
        repository.update(course);
    }

    public void delete(Course course) {
        repository.delete(course);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(CourseViewModel.class)) {
                return (T) new CourseViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
