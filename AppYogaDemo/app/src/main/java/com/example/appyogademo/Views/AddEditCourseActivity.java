package com.example.appyogademo.Views;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appyogademo.Models.Course;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.ValidateInput;
import com.example.appyogademo.ViewModels.CourseViewModel;
import com.example.appyogademo.databinding.ActivityAddEditCourseBinding;
import java.sql.Time;
import java.util.Calendar;
import android.app.AlertDialog;

public class AddEditCourseActivity extends AppCompatActivity {
    private Course course;
    private boolean isEditMode = false; // Biến để xác định chế độ chỉnh sửa
    private CourseViewModel courseViewModel;

    ActivityAddEditCourseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditCourseBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_add_edit_course);
        setContentView(binding.getRoot());
        // Ensure ActionBar is not null before using it
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Khởi tạo ViewModel
        courseViewModel = new CourseViewModel(this);

        // Xử lý khi người dùng click vào inputTime để hiển thị TimePickerDialog
        binding.inputTime.setOnClickListener(v -> showTimePickerDialog());

        // Check if there is course data (edit mode)
        if (getIntent().hasExtra("course")) {
            course = (Course) getIntent().getSerializableExtra("course");
            binding.inputCourseName.setText(course.getCourseName());
            binding.inputDayOfWeek.setText(String.valueOf(course.getDayOfWeek()));
            binding.inputTime.setText(course.getTime().toString()); // Hiển thị thời gian dưới dạng chuỗi
            binding.inputCapacity.setText(String.valueOf(course.getCapacity()));
            binding.inputDuration.setText(String.valueOf(course.getDuration()));
            binding.inputPricePerClass.setText(String.valueOf(course.getPricePerClass()));
            binding.inputClassType.setSelection(getSpinnerIndex(binding.inputClassType, course.getClassType()));
            binding.inputDescription.setText(course.getDescription());
            isEditMode = true; // Set edit mode to true
        }


        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    saveCourse();
                }
            }
        });
    }

    // Phương thức hiển thị TimePickerDialog với nút OK và màu tùy chỉnh
    private void showTimePickerDialog() {
        // Lấy giờ hiện tại để làm mặc định cho TimePicker
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Tạo TimePickerDialog với theme tùy chỉnh
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                R.style.CustomTimePickerTheme, // Sử dụng theme tùy chỉnh
                (view, hourOfDay, minuteOfHour) -> {
                    // Định dạng giờ phút và hiển thị vào inputTime
                    String timeString = String.format("%02d:%02d", hourOfDay, minuteOfHour);
                    binding.inputTime.setText(timeString);  // Cập nhật giờ được chọn vào EditText
                }, hour, minute, true); // `true` cho 24 giờ, `false` cho 12 giờ AM/PM

        // Hiển thị TimePickerDialog
        timePickerDialog.show();
    }

    // Method to save course information
    private void saveCourse() {
        String courseName = binding.inputCourseName.getText().toString();
        int dayOfWeek = Integer.parseInt(binding.inputDayOfWeek.getText().toString());
        String timeString = binding.inputTime.getText().toString();
        String[] timeParts = timeString.split(":");
        Time time = new Time(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]), 0);
        int capacity = Integer.parseInt(binding.inputCapacity.getText().toString());
        int duration = Integer.parseInt(binding.inputDuration.getText().toString());
        Double pricePerClass = Double.parseDouble(binding.inputPricePerClass.getText().toString());
        String classType = binding.inputClassType.getSelectedItem().toString();
        String description = binding.inputDescription.getText().toString();

        // Tạo nội dung hiển thị trong AlertDialog
        String courseDetails = "Course Name: " + courseName +
                "\nDay of Week: " + dayOfWeek +
                "\nTime: " + timeString +
                "\nCapacity: " + capacity +
                "\nDuration: " + duration +
                "\nPrice per Class: " + pricePerClass +
                "\nClass Type: " + classType +
                "\nDescription: " + description;

        // Tạo AlertDialog để xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Confirm Course Information")
                .setMessage(courseDetails)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Lưu khóa học nếu người dùng chọn xác nhận
                    if (isEditMode) {
                        course.setCourseName(courseName);
                        course.setDayOfWeek(dayOfWeek);
                        course.setTime(time);
                        course.setCapacity(capacity);
                        course.setDuration(duration);
                        course.setPricePerClass(pricePerClass);
                        course.setClassType(classType);
                        course.setDescription(description);
                        Boolean result = courseViewModel.update(course);
                        if(result == true) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Course newCourse = new Course(0, courseName, dayOfWeek, time, capacity, duration, pricePerClass, classType, description);
                        Boolean result = courseViewModel.insert(newCourse);
                        if(result == true) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                   // finish(); // Quay về MainActivity sau khi lưu
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Không làm gì nếu người dùng chọn Cancel
                    dialog.dismiss();
                })
                .show();
    }



    // Helper method to get the index of a spinner item
    private int getSpinnerIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to the previous Activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateInputs() {
        boolean isValid = true; // Cờ để theo dõi xem tất cả các trường có hợp lệ hay không

        // Xóa tất cả các thông báo lỗi trước khi kiểm tra
        clearAllErrors();

        // Kiểm tra tên khóa học
        if (ValidateInput.isEmpty(binding.inputCourseName)) {
            TextView errorCourseName = findViewById(R.id.errorCourseName);
            errorCourseName.setText("Course Name cannot be empty");
            errorCourseName.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra ngày trong tuần (DayOfWeek phải là số từ 0-6)
        if (!ValidateInput.isValidDayOfWeek(binding.inputDayOfWeek)) {
            TextView errorDayOfWeek = findViewById(R.id.errorDayOfWeek);
            errorDayOfWeek.setText("Day of Week must be a valid number between 0 and 6");
            errorDayOfWeek.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra thời gian
        if (ValidateInput.isEmpty(binding.inputTime)) {
            TextView errorTime = findViewById(R.id.errorTime);
            errorTime.setText("Time cannot be empty");
            errorTime.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra dung lượng lớp học
        if (!ValidateInput.isPositiveNumber(binding.inputCapacity)) {
            TextView errorCapacity = findViewById(R.id.errorCapacity);
            errorCapacity.setText("Capacity must be a positive number");
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra thời lượng lớp học
        if (!ValidateInput.isPositiveNumber(binding.inputDuration)) {
            TextView errorDuration = findViewById(R.id.errorDuration);
            errorDuration.setText("Duration must be a positive number");
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra giá cho mỗi buổi học
        if (!ValidateInput.isPositiveDouble(binding.inputPricePerClass)) {
            TextView errorPricePerClass = findViewById(R.id.errorPricePerClass);
            errorPricePerClass.setText("Price per class must be a positive number");
            errorPricePerClass.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra mô tả khóa học
        if (ValidateInput.isEmpty(binding.inputDescription)) {
            TextView errorDescription = findViewById(R.id.errorDescription);
            errorDescription.setText("Description cannot be empty");
            errorDescription.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid; // Trả về true nếu tất cả đều hợp lệ
    }

    // Phương thức xóa tất cả các lỗi trước khi kiểm tra
    private void clearAllErrors() {
        binding.errorCourseName.setVisibility(View.GONE);
        binding.errorDayOfWeek.setVisibility(View.GONE);
        binding.errorTime.setVisibility(View.GONE);
        binding.errorCapacity.setVisibility(View.GONE);
        binding.errorDuration.setVisibility(View.GONE);
        binding.errorPricePerClass.setVisibility(View.GONE);
        binding.errorDescription.setVisibility(View.GONE);
    }


}
