package com.example.appyogademo.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appyogademo.Fragments.YogaClassBottomSheetFragment;
import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.Utils.TimeUtils;
import com.example.appyogademo.ViewModels.YogaClassViewModel;
import com.example.appyogademo.databinding.ActivityCourseDetailBinding;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    ActivityCourseDetailBinding binding;
    private YogaClassViewModel yogaClassViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Kích hoạt biểu tượng back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");

        yogaClassViewModel = new YogaClassViewModel(this, course.getId());

        // Hiển thị thông tin khóa học
        if (course != null) {
            binding.txtCourseName.setText(course.getCourseName());
            binding.txtDayOfWeek.setText(TimeUtils.dayOfWeek(course.getDayOfWeek()));
            binding.txtTime.setText(course.getTime().toString());
            binding.txtCapacity.setText(String.valueOf(course.getCapacity()));
            binding.txtDuration.setText(String.valueOf(course.getDuration()));
            binding.txtPricePerClass.setText(String.valueOf(course.getPricePerClass()));
            binding.txtClassType.setText(course.getClassType());
            binding.txtDescription.setText(course.getDescription());
        }

        // Sự kiện click vào btnClassView
        binding.btnViewClass.setOnClickListener(v -> {
            // Lấy danh sách các lớp liên quan đến khóa học này (ví dụ từ cơ sở dữ liệu)
            List<YogaClass> relatedYogaClasses = yogaClassViewModel.getYogaClassesRelatedCourseId().getValue();

            // Hiển thị BottomSheet
            YogaClassBottomSheetFragment bottomSheet = YogaClassBottomSheetFragment.newInstance(relatedYogaClasses, course.getId());
            bottomSheet.show(getSupportFragmentManager(), "YogaClassBottomSheet");
        });

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