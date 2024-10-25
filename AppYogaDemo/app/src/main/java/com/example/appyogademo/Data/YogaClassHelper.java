package com.example.appyogademo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appyogademo.Models.YogaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class YogaClassHelper {
    private SQLiteDatabase db;
    private Context context;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        db.insert(YogaClass.TABLE_NAME, null, values);
    }

    // Lấy tất cả YogaClasses từ cơ sở dữ liệu
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
                        int teacherId = cursor.getInt(teacherIdIndex);
                        int courseId = cursor.getInt(courseIdIndex);

                        yogaClasses.add(new YogaClass(id, className, date, comments, image, teacherId, courseId));
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

        db.update(YogaClass.TABLE_NAME, values, YogaClass.COLUMN_ID + "=?", new String[]{String.valueOf(yogaClass.getId())});
    }

    // Xóa một YogaClass khỏi cơ sở dữ liệu
    public void deleteYogaClass(YogaClass yogaClass) {
        db.delete(YogaClass.TABLE_NAME, YogaClass.COLUMN_ID + "=?", new String[]{String.valueOf(yogaClass.getId())});
    }

    // Kiểm tra xem className đã tồn tại hay chưa (nếu cần)
    public boolean isClassNameExists(String className) {
        Cursor cursor = db.query(YogaClass.TABLE_NAME,
                null,
                YogaClass.COLUMN_CLASSNAME + " = ?",
                new String[]{className},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}

