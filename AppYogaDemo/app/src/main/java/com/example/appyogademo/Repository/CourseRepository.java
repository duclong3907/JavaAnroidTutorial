package com.example.appyogademo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appyogademo.Data.CourseHelper;
import com.example.appyogademo.Data.Database;
import com.example.appyogademo.Models.Course;

import java.util.List;

public class CourseRepository {
    private final CourseHelper courseHelper;
    private final MutableLiveData<List<Course>> courses;

    public CourseRepository(Context context) {
        Database database = new Database(context);
        SQLiteDatabase db = database.getWritableDatabase();
        courseHelper = new CourseHelper(db, context);
        courses = new MutableLiveData<>();
        loadCourses();
    }

    public MutableLiveData<List<Course>> getAllCourses() {
        return courses;
    }

    public void insert(Course course) {
        courseHelper.insertCourse(course);
        loadCourses();
    }

    public void update(Course course) {
        courseHelper.updateCourse(course);
        loadCourses();
    }

    public void delete(Course course) {
        courseHelper.deleteCourse(course);
        loadCourses();
    }

    public void loadCourses() {
        courses.postValue(courseHelper.getAllCourses());
    }

}
