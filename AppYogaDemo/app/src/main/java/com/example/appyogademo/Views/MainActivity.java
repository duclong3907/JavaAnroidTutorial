package com.example.appyogademo.Views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyogademo.Adapters.CourseAdapter;
import com.example.appyogademo.Models.Course;
import com.example.appyogademo.R;
import com.example.appyogademo.ViewModels.CourseViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;

    // Khởi tạo launcher để nhận kết quả từ Activity thêm hoặc chỉnh sửa liên hệ
    private ActivityResultLauncher<Intent> addEditCourseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.hasExtra("newCourse")) { // Nếu có course mới
                            Course newCourse = (Course) data.getSerializableExtra("newCourse");
                            courseViewModel.insert(newCourse);
                        } else if (data.hasExtra("course")) {
                            Course updatedCourse = (Course) data.getSerializableExtra("course");
                            courseViewModel.update(updatedCourse);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv); // Khởi tạo RecyclerView
        adapter = new CourseAdapter(new ArrayList<>(), this); // Khởi tạo adapter với danh sách rỗng
        recyclerView.setAdapter(adapter); // Gán adapter cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Thiết lập LayoutManager cho RecyclerView

        // Sử dụng Factory để tạo ViewModel
        // Modify the instantiation of the Factory in MainActivity
        CourseViewModel.Factory factory = new CourseViewModel.Factory(this);
        courseViewModel = new ViewModelProvider(this, factory).get(CourseViewModel.class);

        //Quan sát danh sách liên lạc và cập nhật adapter khi dữ liệu thay đổi
        courseViewModel.getCourses().observe(this, courses -> {
            adapter.getCourseList().clear();
            adapter.getCourseList().addAll(courses);
            adapter.notifyDataSetChanged();
        });

        //xu ly su kien kien nut them
        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCourseActivity.class);
            addEditCourseLauncher.launch(intent);
        });

        //xu ly su kien click vao item
        adapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
            intent.putExtra("course", course);
            startActivity(intent);
        });

        //xu ly su kien click vao nut edit
        adapter.setOnEditClickListener(course -> {
            Intent intent = new Intent(MainActivity.this, AddEditCourseActivity.class);
            intent.putExtra("course", course);
            addEditCourseLauncher.launch(intent);
        });

        //xu ly su kien click vao nut delete
        adapter.setOnDeleteClickListener(course ->{
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xóa khóa học")
                    .setMessage("Bạn có chắc chắn muốn xóa khóa học này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        courseViewModel.delete(course);
                        Toast.makeText(MainActivity.this, "Đã xóa khóa học", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });






    }
}