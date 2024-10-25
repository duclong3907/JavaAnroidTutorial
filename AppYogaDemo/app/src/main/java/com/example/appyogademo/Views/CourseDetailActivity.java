package com.example.appyogademo.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appyogademo.Fragments.YogaClassBottomSheetFragment;
import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    private TextView txtCourseName;
    private TextView txtDayOfWeek;
    private TextView txtTime;
    private TextView txtCapacity;
    private TextView txtDuration;
    private TextView txtPricePerClass;
    private TextView txtClassType;
    private TextView txtDescription;
    private Button btnClassView;

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
        txtClassType = findViewById(R.id.txtClassType);
        txtDescription = findViewById(R.id.txtDescription);
        btnClassView = findViewById(R.id.btnViewClass);

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

        // Sự kiện click vào btnClassView
        btnClassView.setOnClickListener(v -> {
            // Lấy danh sách các lớp liên quan đến khóa học này (ví dụ từ cơ sở dữ liệu)
            List<YogaClass> relatedYogaClasses = getRelatedYogaClasses(course);

            // Hiển thị BottomSheet
            YogaClassBottomSheetFragment bottomSheet = new YogaClassBottomSheetFragment(relatedYogaClasses);
            bottomSheet.show(getSupportFragmentManager(), "YogaClassBottomSheet");
        });

    }

    private List<YogaClass> getRelatedYogaClasses(Course course) {
        // Giả lập danh sách các YogaClass liên quan đến course
        // Bạn có thể thay thế phần này để lấy dữ liệu thực tế từ cơ sở dữ liệu

        List<YogaClass> yogaClasses = new ArrayList<>();
        yogaClasses.add(new YogaClass(1, "Morning Flow", new Date(), "Class for beginners", "image_url", 1, course.getId()));
        yogaClasses.add(new YogaClass(2, "Evening Stretch", new Date(), "Advanced class", "image_url", 2, course.getId()));
        // Thêm các YogaClass khác nếu cần

        return yogaClasses;
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