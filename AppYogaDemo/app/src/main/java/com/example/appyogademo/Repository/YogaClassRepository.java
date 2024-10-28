package com.example.appyogademo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appyogademo.Data.Database;
import com.example.appyogademo.Data.YogaClassHelper;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.Utils.TimeUtils;

import java.util.List;

public class YogaClassRepository {
    private final YogaClassHelper yogaClassHelper;
    private final MutableLiveData<List<YogaClass>> yogaClasses;
    private final MutableLiveData<List<YogaClass>> yogaClassesByCourseId;

    public YogaClassRepository(Context context, @Nullable int courseId) {
        Database database = new Database(context);
        SQLiteDatabase db = database.getWritableDatabase();
        yogaClassHelper = new YogaClassHelper(db, context);
        yogaClasses = new MutableLiveData<>();
        yogaClassesByCourseId = new MutableLiveData<>();
        loadYogaClasses();
        loadYogaClassRelatedCourseId(courseId);
    }

    public MutableLiveData<List<YogaClass>> getAllYogaClasses() {
        return yogaClasses;
    }

    public MutableLiveData<List<YogaClass>> getAllYogaClassesByCourseId() {
        return yogaClassesByCourseId;
    }

    public Result insert(YogaClass yogaClass) {
        if(yogaClassHelper.isClassNameExists(yogaClass.getClassName(), -1)) {
            return new Result(false, "Class name already exists");
        }

        if (!yogaClassHelper.checkDayOfWeekMatch(yogaClass.getCourseId(), yogaClass.getDate())) {
            return new Result(false, "The date does not match the day of the week for the course. Expected: " +
                    TimeUtils.dayOfWeek(yogaClassHelper.getCourseDayOfWeek(yogaClass.getCourseId())));
        }

        yogaClassHelper.insertYogaClass(yogaClass);
        loadYogaClassRelatedCourseId(yogaClass.getCourseId());
        loadYogaClasses();
        return new Result(true, "Class added successfully");
    }

    public Result update(YogaClass yogaClass) {
        if(yogaClassHelper.isClassNameExists(yogaClass.getClassName(), yogaClass.getId())) {
            return new Result(false, "Class name already exists");
        }

        if (!yogaClassHelper.checkDayOfWeekMatch(yogaClass.getCourseId(), yogaClass.getDate())) {
            return new Result(false, "The date does not match the day of the week for the course. Expected: " +
                    TimeUtils.dayOfWeek(yogaClassHelper.getCourseDayOfWeek(yogaClass.getCourseId())));
        }

        yogaClassHelper.updateYogaClass(yogaClass);
        loadYogaClassRelatedCourseId(yogaClass.getCourseId());
        loadYogaClasses();
        return new Result(true, "Class updated successfully");
    }

    public void delete(YogaClass yogaClass) {
        yogaClassHelper.deleteYogaClass(yogaClass);
        loadYogaClassRelatedCourseId(yogaClass.getCourseId());
        loadYogaClasses();
    }

    private void loadYogaClasses() {
        yogaClasses.postValue(yogaClassHelper.getAllYogaClasses());
    }

    public void loadYogaClassRelatedCourseId(int courseId) {
        yogaClassesByCourseId.postValue(yogaClassHelper.getAllYogaByCourseId(courseId));
    }
}
