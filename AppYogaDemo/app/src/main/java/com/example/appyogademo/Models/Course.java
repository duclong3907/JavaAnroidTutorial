package com.example.appyogademo.Models;
import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;

public class Course implements Serializable {
    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "courseName";
    public static final String COLUMN_DAYOFWEEK = "dayOfWeek";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CAPACITY= "capacity";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_PRICEPERCLASS= "pricePerClass";
    public static final String COLUMN_CLASSTYPE = "classType";
    public static final String COLUMN_DESCRIPTION = "description";

    private int id;
    private String courseName;
    private int dayOfWeek;
    private Time time;
    private int capacity;
    private int duration;
    private double pricePerClass;
    private String classType;
    private String description;


    public Course() {}

    public Course(int id, String courseName, int dayOfWeek, Time time, int capacity, int duration, double pricePerClass, String classType, String description) {
        this.id = id;
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.pricePerClass = pricePerClass;
        this.classType = classType;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPricePerClass() {
        return pricePerClass;
    }

    public void setPricePerClass(double pricePerClass) {
        this.pricePerClass = pricePerClass;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DAYOFWEEK + " INTERGER, " +
                    COLUMN_TIME + " TEXT, " +
                    COLUMN_CAPACITY + " INTEGER, " +
                    COLUMN_DURATION + " INTEGER, " +
                    COLUMN_PRICEPERCLASS + " REAL, " +
                    COLUMN_CLASSTYPE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT" + ")";
}
