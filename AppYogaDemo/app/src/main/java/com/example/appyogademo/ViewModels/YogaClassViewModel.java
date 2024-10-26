package com.example.appyogademo.ViewModels;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private final Context context;
    public YogaClassViewModel(Context context) {
        this.context = context;
        repository = new YogaClassRepository(context);
        yogaClasses = repository.getAllYogaClasses();
    }

    public LiveData<List<YogaClass>> getYogaClasses() {
        return yogaClasses;
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

    public void update(YogaClass yogaClass) {

        try {
            Result result = repository.update(yogaClass);
            if(result.isStatus()) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(YogaClass yogaClass) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa khóa học")
                    .setMessage("Bạn có chắc chắn muốn xóa class này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        repository.delete(yogaClass);
                        Toast.makeText(context, "Đã xóa khóa học", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
