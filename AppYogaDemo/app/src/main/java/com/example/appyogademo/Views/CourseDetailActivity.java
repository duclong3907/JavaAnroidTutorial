package com.example.appyogademo.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.R;

public class CourseDetailActivity extends AppCompatActivity {
    private TextView txtCourseName;
    private TextView txtDayOfWeek;
    private TextView txtTime;
    private TextView txtCapacity;
    private TextView txtDuration;
    private TextView txtPricePerClass;
    private TextView txtClassType;
    private TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Kích hoạt biểu tượng back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Khởi tạo các View
        txtCourseName = findViewById(R.id.txtCourseName);
        txtDayOfWeek = findViewById(R.id.txtDayOfWeek);
        txtTime = findViewById(R.id.txtTime);
        txtCapacity = findViewById(R.id.txtCapacity);
        txtDuration = findViewById(R.id.txtDuration);
        txtPricePerClass = findViewById(R.id.txtPricePerClass);
        txtClassType = findViewById(R.id.classType);
        txtDescription = findViewById(R.id.txtDescription);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");

        // Hiển thị thông tin khóa học
        if (course != null) {
            txtCourseName.setText(course.getCourseName());
            txtDayOfWeek.setText(String.valueOf(course.getDayOfWeek())); // Chuyển đổi sang chuỗi
            txtTime.setText(course.getTime().toString());
            txtCapacity.setText(String.valueOf(course.getCapacity()));
            txtDuration.setText(String.valueOf(course.getDuration()));
            txtPricePerClass.setText(String.valueOf(course.getPricePerClass()));
            txtClassType.setText(course.getClassType());
            txtDescription.setText(course.getDescription());
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Quay lại Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}