package com.example.appyogademo.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    public static final String COLUMN_CREATED_AT = "createdAt";
    public static final String COLUMN_UPDATED_AT = "updatedAt";

    private int id;
    private String className;
    private Date date;
    private String comments;
    private String image;
    private String teacherId;
    private int courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public YogaClass(int id, String className, Date date, String comments, String image, String teacherId, int courseId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.className = className;
        this.date = date;
        this.comments = comments;
        this.image = image;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now(); // Ensure createdAt is not null
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now(); // Ensure updatedAt is not null
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CLASSNAME + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_COMMENTS + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_TEACHERID + " TEXT, " +
                    COLUMN_COURSEID + " INTEGER, " +
                    COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_UPDATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(" + COLUMN_TEACHERID + ") REFERENCES " + User.TABLE_NAME + "(" + User.COLUMN_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_COURSEID + ") REFERENCES " + Course.TABLE_NAME + "(" + Course.COLUMN_ID + ")" +
                    ")";
}
