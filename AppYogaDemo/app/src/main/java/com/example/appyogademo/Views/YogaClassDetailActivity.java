package com.example.appyogademo.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.TimeUtils;
import com.example.appyogademo.ViewModels.CourseViewModel;
import com.example.appyogademo.ViewModels.UserViewModel;
import com.example.appyogademo.databinding.ActivityYogaClassDetailBinding;
import com.squareup.picasso.Picasso;

public class YogaClassDetailActivity extends AppCompatActivity {

    ActivityYogaClassDetailBinding binding;
    CourseViewModel courseViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYogaClassDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseViewModel = new CourseViewModel(this);
        userViewModel = new UserViewModel(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        var yogaClass = (YogaClass) intent.getSerializableExtra("yogaClass");

        if(yogaClass != null) {
            var course  = courseViewModel.getCourseById(yogaClass.getCourseId());
            var teacher = userViewModel.getUserById(yogaClass.getTeacherId());
            // Set image using Picasso
            Picasso.get()
                    .load(yogaClass.getImage())
                    .placeholder(R.drawable.yoga_class) // Placeholder image
                    .error(R.drawable.yoga_class) // Error image
                    .into(binding.ClassImage);

            Picasso.get()
                    .load(teacher.getImage())
                    .placeholder(R.drawable.yoga_class) // Placeholder image
                    .error(R.drawable.yoga_class) // Error image
                    .into(binding.imageTeacher);

            binding.classesName.setText(yogaClass.getClassName());
            binding.classDateAndDayOfWeek.setText(TimeUtils.convertDate(yogaClass.getDate().toString()) + " - " + TimeUtils.dayOfWeek(course.getDayOfWeek()));
            binding.Experience.setText("Experience: " + teacher.getExperience());
            binding.TeacherName.setText(teacher.getFullName());
            binding.ClassType.setText("Class Type: " + course.getClassType());
            binding.StartTime.setText("Start Time: " + course.getTime().toString());
            binding.Duration.setText("Duration: " + course.getDuration() + " minutes");
            binding.PricePerClass.setText(course.getPricePerClass() + " Per Person");
            binding.Description.setText(course.getDescription());


        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về Activity trước đó
                finish();
            }
        });
    }
}