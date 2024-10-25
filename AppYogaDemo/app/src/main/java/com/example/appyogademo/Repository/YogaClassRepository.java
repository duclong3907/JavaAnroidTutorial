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

    public void insert(YogaClass yogaClass) {
        yogaClassHelper.insertYogaClass(yogaClass);
        loadYogaClasses();
    }

    public void update(YogaClass yogaClass) {
        yogaClassHelper.updateYogaClass(yogaClass);
        loadYogaClasses();
    }

    public void delete(YogaClass yogaClass) {
        yogaClassHelper.deleteYogaClass(yogaClass);
        loadYogaClasses();
    }

    private void loadYogaClasses() {
        yogaClasses.postValue(yogaClassHelper.getAllYogaClasses());
    }
}
