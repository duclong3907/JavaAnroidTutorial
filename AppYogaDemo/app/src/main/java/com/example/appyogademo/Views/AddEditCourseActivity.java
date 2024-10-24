package com.example.appyogademo.Views;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appyogademo.Models.Course;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.ValidateInput;

import java.sql.Time;
import java.util.Calendar;

public class AddEditCourseActivity extends AppCompatActivity {

    private EditText inputCourseName;
    private EditText inputDayOfWeek;
    private EditText inputTime; // Đổi từ TimePicker thành EditText
    private EditText inputCapacity;
    private EditText inputDuration;
    private EditText inputPricePerClass;
    private Spinner inputClassType;
    private EditText inputDescription;
    private Button buttonSave;
    private Course course;
    private boolean isEditMode = false; // Biến để xác định chế độ chỉnh sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        // Ensure ActionBar is not null before using it
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        inputCourseName = findViewById(R.id.inputCourseName);
        inputDayOfWeek = findViewById(R.id.inputDayOfWeek);
        inputTime = findViewById(R.id.inputTime); // Là EditText
        inputCapacity = findViewById(R.id.inputCapacity);
        inputDuration = findViewById(R.id.inputDuration);
        inputPricePerClass = findViewById(R.id.inputPricePerClass);
        inputClassType = findViewById(R.id.inputClassType);
        inputDescription = findViewById(R.id.inputDescription);
        buttonSave = findViewById(R.id.buttonSave);

        // Xử lý khi người dùng click vào inputTime để hiển thị TimePickerDialog
        inputTime.setOnClickListener(v -> showTimePickerDialog());

        // Check if there is course data (edit mode)
        if (getIntent().hasExtra("course")) {
            course = (Course) getIntent().getSerializableExtra("course");
            inputCourseName.setText(course.getCourseName());
            inputDayOfWeek.setText(String.valueOf(course.getDayOfWeek()));
            inputTime.setText(course.getTime().toString()); // Hiển thị thời gian dưới dạng chuỗi
            inputCapacity.setText(String.valueOf(course.getCapacity()));
            inputDuration.setText(String.valueOf(course.getDuration()));
            inputPricePerClass.setText(String.valueOf(course.getPricePerClass()));
            inputClassType.setSelection(getSpinnerIndex(inputClassType, course.getClassType()));
            inputDescription.setText(course.getDescription());
            isEditMode = true; // Set edit mode to true
        }

        // Set click event for save button
        buttonSave.setOnClickListener(v -> {
            if (validateInputs()) {
                saveCourse();
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
                    inputTime.setText(timeString);  // Cập nhật giờ được chọn vào EditText
                }, hour, minute, true); // `true` cho 24 giờ, `false` cho 12 giờ AM/PM

        // Hiển thị TimePickerDialog
        timePickerDialog.show();
    }

    // Method to save course information
    private void saveCourse() {
        String courseName = inputCourseName.getText().toString();
        int dayOfWeek = Integer.parseInt(inputDayOfWeek.getText().toString());
        String timeString = inputTime.getText().toString();
        // Chuyển đổi thời gian từ chuỗi thành Time
        String[] timeParts = timeString.split(":");
        Time time = new Time(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]), 0);
        int capacity = Integer.parseInt(inputCapacity.getText().toString());
        int duration = Integer.parseInt(inputDuration.getText().toString());
        Double pricePerClass = Double.parseDouble(inputPricePerClass.getText().toString());
        String classType = inputClassType.getSelectedItem().toString();
        String description = inputDescription.getText().toString();

        if (isEditMode) {
            course.setCourseName(courseName);
            course.setDayOfWeek(dayOfWeek);
            course.setTime(time);
            course.setCapacity(capacity);
            course.setDuration(duration);
            course.setPricePerClass(pricePerClass);
            course.setClassType(classType);
            course.setDescription(description);
            Intent intent = new Intent();
            intent.putExtra("course", course);
            setResult(RESULT_OK, intent);
        } else {
            Course newCourse = new Course(0, courseName, dayOfWeek, time, capacity, duration, pricePerClass, classType, description);
            Intent intent = new Intent();
            intent.putExtra("newCourse", newCourse);
            setResult(RESULT_OK, intent);
        }
        finish();
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
        if (ValidateInput.isEmpty(inputCourseName)) {
            TextView errorCourseName = findViewById(R.id.errorCourseName);
            errorCourseName.setText("Course Name cannot be empty");
            errorCourseName.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra ngày trong tuần (DayOfWeek phải là số từ 0-6)
        if (!ValidateInput.isValidDayOfWeek(inputDayOfWeek)) {
            TextView errorDayOfWeek = findViewById(R.id.errorDayOfWeek);
            errorDayOfWeek.setText("Day of Week must be a valid number between 0 and 6");
            errorDayOfWeek.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra thời gian
        if (ValidateInput.isEmpty(inputTime)) {
            TextView errorTime = findViewById(R.id.errorTime);
            errorTime.setText("Time cannot be empty");
            errorTime.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra dung lượng lớp học
        if (!ValidateInput.isPositiveNumber(inputCapacity)) {
            TextView errorCapacity = findViewById(R.id.errorCapacity);
            errorCapacity.setText("Capacity must be a positive number");
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra thời lượng lớp học
        if (!ValidateInput.isPositiveNumber(inputDuration)) {
            TextView errorDuration = findViewById(R.id.errorDuration);
            errorDuration.setText("Duration must be a positive number");
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra giá cho mỗi buổi học
        if (!ValidateInput.isPositiveDouble(inputPricePerClass)) {
            TextView errorPricePerClass = findViewById(R.id.errorPricePerClass);
            errorPricePerClass.setText("Price per class must be a positive number");
            errorPricePerClass.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra mô tả khóa học
        if (ValidateInput.isEmpty(inputDescription)) {
            TextView errorDescription = findViewById(R.id.errorDescription);
            errorDescription.setText("Description cannot be empty");
            errorDescription.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid; // Trả về true nếu tất cả đều hợp lệ
    }

    // Phương thức xóa tất cả các lỗi trước khi kiểm tra
    private void clearAllErrors() {
        findViewById(R.id.errorCourseName).setVisibility(View.GONE);
        findViewById(R.id.errorDayOfWeek).setVisibility(View.GONE);
        findViewById(R.id.errorTime).setVisibility(View.GONE);
        findViewById(R.id.errorCapacity).setVisibility(View.GONE);
        findViewById(R.id.errorDuration).setVisibility(View.GONE);
        findViewById(R.id.errorPricePerClass).setVisibility(View.GONE);
        findViewById(R.id.errorDescription).setVisibility(View.GONE);
    }


}
