package com.example.appyogademo.ViewModels;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Repository.CourseRepository;
import com.example.appyogademo.Repository.Result;

import java.util.List;

public class CourseViewModel extends ViewModel {
    private final CourseRepository repository;
    private final LiveData<List<Course>> courses;
    private final Context context;
    public CourseViewModel(Context context) {
        this.context = context;
        repository = new CourseRepository(context);
        courses = repository.getAllCourses();
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public Boolean insert(Course course) {
       try{
           Result result = repository.insert(course);
           if(result.isStatus()) {
               Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
               return true;
           } else {
               Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
               return false;
           }
       }catch (Exception e){
           Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
           return false;
       }
    }

    public Boolean update(Course course) {
        try{
            Result result = repository.update(course);
            if(result.isStatus()) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void delete(Course course) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa khóa học")
                    .setMessage("Bạn có chắc chắn muốn xóa khóa học này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        repository.delete(course);
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

    public Course getCourseById(int courseId) {
        return repository.getCourseById(courseId);
    }

}
