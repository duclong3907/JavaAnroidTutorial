package com.example.appyogademo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.widget.Toast;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Models.YogaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class YogaClassHelper {
    private SQLiteDatabase db;
    private Context context;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public YogaClassHelper(SQLiteDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    // Thêm một YogaClass vào cơ sở dữ liệu
    public void insertYogaClass(YogaClass yogaClass) {
        ContentValues values = new ContentValues();
        values.put(YogaClass.COLUMN_CLASSNAME, yogaClass.getClassName());
        values.put(YogaClass.COLUMN_DATE, dateFormat.format(yogaClass.getDate()));
        values.put(YogaClass.COLUMN_COMMENTS, yogaClass.getComments());
        values.put(YogaClass.COLUMN_IMAGE, yogaClass.getImage());
        values.put(YogaClass.COLUMN_TEACHERID, yogaClass.getTeacherId());
        values.put(YogaClass.COLUMN_COURSEID, yogaClass.getCourseId());
        values.put(YogaClass.COLUMN_CREATED_AT, LocalDateTime.now().format(DATE_TIME_FORMATTER));
        values.put(YogaClass.COLUMN_UPDATED_AT, LocalDateTime.now().format(DATE_TIME_FORMATTER));

        db.insert(YogaClass.TABLE_NAME, null, values);
    }

    public List<YogaClass> getAllYogaClasses() {
        List<YogaClass> yogaClasses = new ArrayList<>();
        Cursor cursor = db.query(YogaClass.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex(YogaClass.COLUMN_ID);
                    int classNameIndex = cursor.getColumnIndex(YogaClass.COLUMN_CLASSNAME);
                    int dateIndex = cursor.getColumnIndex(YogaClass.COLUMN_DATE);
                    int commentsIndex = cursor.getColumnIndex(YogaClass.COLUMN_COMMENTS);
                    int imageIndex = cursor.getColumnIndex(YogaClass.COLUMN_IMAGE);
                    int teacherIdIndex = cursor.getColumnIndex(YogaClass.COLUMN_TEACHERID);
                    int courseIdIndex = cursor.getColumnIndex(YogaClass.COLUMN_COURSEID);
                    int createdAtIndex = cursor.getColumnIndex(YogaClass.COLUMN_CREATED_AT);
                    int updatedAtIndex = cursor.getColumnIndex(YogaClass.COLUMN_UPDATED_AT);

                    if (idIndex != -1 && classNameIndex != -1 && dateIndex != -1 &&
                            commentsIndex != -1 && imageIndex != -1 && teacherIdIndex != -1 && courseIdIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String className = cursor.getString(classNameIndex);
                        Date date = null;
                        try {
                            date = dateFormat.parse(cursor.getString(dateIndex));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String comments = cursor.getString(commentsIndex);
                        String image = cursor.getString(imageIndex);
                        String teacherId = cursor.getString(teacherIdIndex);
                        int courseId = cursor.getInt(courseIdIndex);
                        LocalDateTime createdAt = LocalDateTime.parse(cursor.getString(createdAtIndex), DATE_TIME_FORMATTER);
                        LocalDateTime updatedAt = LocalDateTime.parse(cursor.getString(updatedAtIndex), DATE_TIME_FORMATTER);

                        yogaClasses.add(new YogaClass(id, className, date, comments, image, teacherId, courseId, createdAt, updatedAt));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return yogaClasses;
    }

    // Lấy tất cả YogaClasses từ cơ sở dữ liệu theo courseId
    public List<YogaClass> getAllYogaByCourseId(int courseId) {
        List<YogaClass> yogaClasses = new ArrayList<>();
        Cursor cursor = db.query(YogaClass.TABLE_NAME, null, YogaClass.COLUMN_COURSEID + "=?", new String[]{String.valueOf(courseId)}, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex(YogaClass.COLUMN_ID);
                    int classNameIndex = cursor.getColumnIndex(YogaClass.COLUMN_CLASSNAME);
                    int dateIndex = cursor.getColumnIndex(YogaClass.COLUMN_DATE);
                    int commentsIndex = cursor.getColumnIndex(YogaClass.COLUMN_COMMENTS);
                    int imageIndex = cursor.getColumnIndex(YogaClass.COLUMN_IMAGE);
                    int teacherIdIndex = cursor.getColumnIndex(YogaClass.COLUMN_TEACHERID);
                    int courseIdIndex = cursor.getColumnIndex(YogaClass.COLUMN_COURSEID);
                    int createdAtIndex = cursor.getColumnIndex(YogaClass.COLUMN_CREATED_AT);
                    int updatedAtIndex = cursor.getColumnIndex(YogaClass.COLUMN_UPDATED_AT);

                    if (idIndex != -1 && classNameIndex != -1 && dateIndex != -1 &&
                            commentsIndex != -1 && imageIndex != -1 && teacherIdIndex != -1 && courseIdIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String className = cursor.getString(classNameIndex);
                        Date date = null;
                        try {
                            date = dateFormat.parse(cursor.getString(dateIndex));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String comments = cursor.getString(commentsIndex);
                        String image = cursor.getString(imageIndex);
                        String teacherId = cursor.getString(teacherIdIndex);
                        LocalDateTime createdAt = LocalDateTime.parse(cursor.getString(createdAtIndex), DATE_TIME_FORMATTER);
                        LocalDateTime updatedAt = LocalDateTime.parse(cursor.getString(updatedAtIndex), DATE_TIME_FORMATTER);

                        yogaClasses.add(new YogaClass(id, className, date, comments, image, teacherId, courseId, createdAt, updatedAt));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return yogaClasses;
    }

    // Cập nhật thông tin của một YogaClass
    public void updateYogaClass(YogaClass yogaClass) {
        ContentValues values = new ContentValues();
        values.put(YogaClass.COLUMN_CLASSNAME, yogaClass.getClassName());
        values.put(YogaClass.COLUMN_DATE, dateFormat.format(yogaClass.getDate()));
        values.put(YogaClass.COLUMN_COMMENTS, yogaClass.getComments());
        values.put(YogaClass.COLUMN_IMAGE, yogaClass.getImage());
        values.put(YogaClass.COLUMN_TEACHERID, yogaClass.getTeacherId());
        values.put(YogaClass.COLUMN_COURSEID, yogaClass.getCourseId());
        values.put(YogaClass.COLUMN_UPDATED_AT, LocalDateTime.now().format(DATE_TIME_FORMATTER));
        db.update(YogaClass.TABLE_NAME, values, YogaClass.COLUMN_ID + "=?", new String[]{String.valueOf(yogaClass.getId())});
    }

    // Xóa một YogaClass khỏi cơ sở dữ liệu
    public void deleteYogaClass(YogaClass yogaClass) {
        db.delete(YogaClass.TABLE_NAME, YogaClass.COLUMN_ID + "=?", new String[]{String.valueOf(yogaClass.getId())});
    }

    // Kiểm tra xem className đã tồn tại hay chưa (nếu cần)
    public boolean isClassNameExists(String className, int classId) {
        Cursor cursor = db.query(YogaClass.TABLE_NAME,
                null,
                YogaClass.COLUMN_CLASSNAME+ " = ? AND " + YogaClass.COLUMN_ID + " != ?",
                new String[]{className, String.valueOf(classId)},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Get the dayOfWeek of a Course by its courseId
    public int getCourseDayOfWeek(int courseId) {
        Cursor cursor = db.query(Course.TABLE_NAME, new String[]{Course.COLUMN_DAYOFWEEK}, Course.COLUMN_ID + "=?", new String[]{String.valueOf(courseId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int dayOfWeekIndex = cursor.getColumnIndex(Course.COLUMN_DAYOFWEEK);
            if (dayOfWeekIndex != -1) {
                int dayOfWeek = cursor.getInt(dayOfWeekIndex);
                cursor.close();
                return dayOfWeek;
            }
            cursor.close();
        }
        return -1; // Return -1 if not found
    }

    //check day of week in course match date in yoga class
    public boolean checkDayOfWeekMatch(int courseId, Date date) {
        int courseDayOfWeek = getCourseDayOfWeek(courseId);
        if (courseDayOfWeek == -1) {
            return false;
        }

        // Convert Date to Calendar to get the day of the week
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yogaClassDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return courseDayOfWeek == yogaClassDayOfWeek;
    }

}

