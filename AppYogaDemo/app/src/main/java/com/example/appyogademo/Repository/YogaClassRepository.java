package com.example.appyogademo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appyogademo.Data.Database;
import com.example.appyogademo.Data.YogaClassHelper;
import com.example.appyogademo.Models.YogaClass;

import java.util.List;

public class YogaClassRepository {
    private final YogaClassHelper yogaClassHelper;
    private final MutableLiveData<List<YogaClass>> yogaClasses;

    public YogaClassRepository(Context context) {
        Database database = new Database(context);
        SQLiteDatabase db = database.getWritableDatabase();
        yogaClassHelper = new YogaClassHelper(db, context);
        yogaClasses = new MutableLiveData<>();
        loadYogaClasses();
    }

    public MutableLiveData<List<YogaClass>> getAllYogaClasses() {
        return yogaClasses;
    }

    public Result insert(YogaClass yogaClass) {
        if(yogaClassHelper.isClassNameExists(yogaClass.getClassName(), -1)) {
            return new Result(false, "Class name already exists");

        } else {
            yogaClassHelper.insertYogaClass(yogaClass);
            loadYogaClasses();
            return new Result(true, "Class added successfully");
        }
    }

    public Result update(YogaClass yogaClass) {
        if(yogaClassHelper.isClassNameExists(yogaClass.getClassName(), yogaClass.getId())) {
            return new Result(false, "Class name already exists");
        } else {
            yogaClassHelper.updateYogaClass(yogaClass);
            loadYogaClasses();
            return new Result(true, "Class updated successfully");
        }
    }

    public void delete(YogaClass yogaClass) {
        yogaClassHelper.deleteYogaClass(yogaClass);
        loadYogaClasses();
    }

    private void loadYogaClasses() {
        yogaClasses.postValue(yogaClassHelper.getAllYogaClasses());
    }
}
