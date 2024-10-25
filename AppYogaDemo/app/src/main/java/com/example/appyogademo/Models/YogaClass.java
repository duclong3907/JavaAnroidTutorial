package com.example.appyogademo.Models;

import java.io.Serializable;
import java.util.Date;

public class YogaClass implements Serializable {
    public static final String TABLE_NAME = "yogaClasses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CLASSNAME = "className";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMMENTS = "comments";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TEACHERID = "teacherId";
    public static final String COLUMN_COURSEID = "courseId";

    private int id;
    private String className;
    private Date date;
    private String comments;
    private String image;
    private int teacherId;
    private int courseId;

    public YogaClass() {}

    public YogaClass(int id, String className, Date date, String comments, String image, int teacherId, int courseId) {
        this.id = id;
        this.className = className;
        this.date = date;
        this.comments = comments;
        this.image = image;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CLASSNAME + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_COMMENTS + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_TEACHERID + " INTEGER, " +
                    COLUMN_COURSEID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_COURSEID + ") REFERENCES " +
                    Course.TABLE_NAME + "(" + Course.COLUMN_ID + ")" +
                    ")";
}
