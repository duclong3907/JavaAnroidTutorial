package com.example.appyogademo.ViewModels;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.Repository.Result;
import com.example.appyogademo.Repository.YogaClassRepository;

import java.util.List;

public class YogaClassViewModel extends ViewModel {
    private final YogaClassRepository repository;
    private final LiveData<List<YogaClass>> yogaClasses;
    private final LiveData<List<YogaClass>> yogaClassesRelatedCourseId;
    private final Context context;
    public YogaClassViewModel(Context context, @Nullable int courseId) {
        this.context = context;
        repository = new YogaClassRepository(context, courseId);
        yogaClasses = repository.getAllYogaClasses();
        yogaClassesRelatedCourseId = repository.getAllYogaClassesByCourseId();
    }

    public LiveData<List<YogaClass>> getYogaClasses() {
        return yogaClasses;
    }
    public LiveData<List<YogaClass>> getYogaClassesRelatedCourseId() {
        return yogaClassesRelatedCourseId;
    }

    public Boolean insert(YogaClass yogaClass) {
        try {
            Result result = repository.insert(yogaClass);
            if(result.isStatus()) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean update(YogaClass yogaClass) {
        try {
            Result result = repository.update(yogaClass);
            if(result.isStatus()) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void delete(YogaClass yogaClass) {
        try {
            repository.delete(yogaClass);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
