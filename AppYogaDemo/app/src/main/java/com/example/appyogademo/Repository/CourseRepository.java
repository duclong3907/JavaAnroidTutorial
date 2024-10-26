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

    public Result insert(Course course) {
        if(courseHelper.isCourseNameExists(course.getCourseName(), -1)) {
            return new Result(false, "Course already exists");
        } else {
            courseHelper.insertCourse(course);
            loadCourses();
            return new Result(true, "Course added successfully");
        }
    }

    public Result update(Course course) {
        if(courseHelper.isCourseNameExists(course.getCourseName(), course.getId())) {
            return new Result(false, "Course name already exists");
        } else {
            courseHelper.updateCourse(course);
            loadCourses();
            return new Result(true, "Course updated successfully");
        }
    }

    public void delete(Course course) {
        courseHelper.deleteCourse(course);
        loadCourses();
    }

    public void loadCourses() {
        courses.postValue(courseHelper.getAllCourses());
    }

}
