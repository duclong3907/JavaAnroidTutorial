package com.example.appyogademo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appyogademo.Models.Course;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CourseHelper {
    private SQLiteDatabase db;
    private Context context;

    public CourseHelper(SQLiteDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    // thêm 1 course vào cơ sở dữ liệu
    public void insertCourse(Course course) {
        if (isCourseNameExists(course.getCourseName())) {
            Toast.makeText(context, "Course name already exists", Toast.LENGTH_SHORT).show();
            return; // Không thực hiện insert nếu đã tồn tại
        }

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_NAME, course.getCourseName());
        values.put(Course.COLUMN_DAYOFWEEK, course.getDayOfWeek());
        values.put(Course.COLUMN_TIME, course.getTime().toString());
        values.put(Course.COLUMN_CAPACITY, course.getCapacity());
        values.put(Course.COLUMN_DURATION, course.getDuration());
        values.put(Course.COLUMN_PRICEPERCLASS, course.getPricePerClass());
        values.put(Course.COLUMN_CLASSTYPE, course.getClassType());
        values.put(Course.COLUMN_DESCRIPTION, course.getDescription());

        db.insert(Course.TABLE_NAME, null, values);
    }


    //lấy tất cả courses từ cơ sở dữ liệu
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Cursor cursor = db.query(Course.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex(Course.COLUMN_ID);
                    int courseNameIndex = cursor.getColumnIndex(Course.COLUMN_NAME);
                    int dayOfWeekIndex = cursor.getColumnIndex(Course.COLUMN_DAYOFWEEK);
                    int timeIndex = cursor.getColumnIndex(Course.COLUMN_TIME);
                    int capacityIndex = cursor.getColumnIndex(Course.COLUMN_CAPACITY);
                    int durationIndex = cursor.getColumnIndex(Course.COLUMN_DURATION);
                    int pricePerClassIndex = cursor.getColumnIndex(Course.COLUMN_PRICEPERCLASS);
                    int classTypeIndex = cursor.getColumnIndex(Course.COLUMN_CLASSTYPE);
                    int descriptionIndex = cursor.getColumnIndex(Course.COLUMN_DESCRIPTION);

                    if (idIndex != -1 && courseNameIndex != -1 && dayOfWeekIndex != -1 && timeIndex != -1 && capacityIndex != -1 && durationIndex != -1 && pricePerClassIndex != -1 && classTypeIndex != -1 && descriptionIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(courseNameIndex);
                        int dayOfWeek = cursor.getInt(dayOfWeekIndex);
                        Time time = Time.valueOf(cursor.getString(timeIndex));
                        int capacity = cursor.getInt(capacityIndex);
                        int duration = cursor.getInt(durationIndex);
                        double pricePerClass = cursor.getDouble(pricePerClassIndex);
                        String classType = cursor.getString(classTypeIndex);
                        String description = cursor.getString(descriptionIndex);
                        courses.add(new Course(id, name, dayOfWeek, time, capacity, duration, pricePerClass, classType, description));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return courses;
    }

    //cập nhật thông tin của 1 course
    public void updateCourse(Course course) {
        // Nếu courseName tồn tại và không phải của khóa học hiện tại (trong trường hợp chỉnh sửa)
        if (isCourseNameExists(course.getCourseName())) {
            Toast.makeText(context, "Course name already exists", Toast.LENGTH_SHORT).show();
            return; // Không thực hiện update nếu đã tồn tại
        }

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_NAME, course.getCourseName());
        values.put(Course.COLUMN_DAYOFWEEK, course.getDayOfWeek());
        values.put(Course.COLUMN_TIME, course.getTime().toString());
        values.put(Course.COLUMN_CAPACITY, course.getCapacity());
        values.put(Course.COLUMN_DURATION, course.getDuration());
        values.put(Course.COLUMN_PRICEPERCLASS, course.getPricePerClass());
        values.put(Course.COLUMN_CLASSTYPE, course.getClassType());
        values.put(Course.COLUMN_DESCRIPTION, course.getDescription());

        db.update(Course.TABLE_NAME, values, Course.COLUMN_ID + "=?", new String[]{String.valueOf(course.getId())});
    }

    //xóa 1 course khỏi cơ sở dữ liệu
    public void deleteCourse(Course course) {
        db.delete(Course.TABLE_NAME, Course.COLUMN_ID + "=?", new String[]{String.valueOf(course.getId())});
    }

    public boolean isCourseNameExists(String courseName) {
        Cursor cursor = db.query(Course.TABLE_NAME,
                null,
                Course.COLUMN_NAME + " = ?",
                new String[]{courseName},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


}
