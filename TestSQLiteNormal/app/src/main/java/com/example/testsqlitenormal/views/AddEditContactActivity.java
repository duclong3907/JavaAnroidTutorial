package com.example.testsqlitenormal.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.R;

public class AddEditContactActivity extends AppCompatActivity {
    private EditText editTextName; // Biến để nhập tên liên hệ
    private EditText editTextEmail; // Biến để nhập email liên hệ
    private Button buttonSave; // Nút lưu liên hệ
    private Contact contact; // Biến để lưu thông tin liên hệ (nếu chỉnh sửa)
    private boolean isEditMode = false; // Biến để xác định chế độ chỉnh sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact); // Thiết lập layout cho Activity

        // Kích hoạt biểu tượng back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Khởi tạo các View
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);

        // Kiểm tra xem có dữ liệu contact không (chế độ chỉnh sửa)
        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact"); // Lấy contact từ Intent
            editTextName.setText(contact.getName()); // Hiển thị tên liên hệ vào EditText
            editTextEmail.setText(contact.getEmail()); // Hiển thị email liên hệ vào EditText
            isEditMode = true; // Đặt chế độ chỉnh sửa thành true
        }

        // Thiết lập sự kiện click cho nút lưu
        buttonSave.setOnClickListener(v -> saveContact());
    }

    // Phương thức để lưu thông tin liên hệ
    private void saveContact() {
        String name = editTextName.getText().toString(); // Lấy tên từ EditText
        String email = editTextEmail.getText().toString(); // Lấy email từ EditText

        if (isEditMode) { // Nếu đang ở chế độ chỉnh sửa
            contact.setName(name); // Cập nhật tên liên hệ
            contact.setEmail(email); // Cập nhật email liên hệ
            Intent intent = new Intent(); // Tạo Intent để trả kết quả
            intent.putExtra("contact", contact); // Đưa contact đã chỉnh sửa vào Intent
            setResult(RESULT_OK, intent); // Trả kết quả OK
        } else { // Nếu đang thêm liên hệ mới
            Contact newContact = new Contact(name, email, 0); // Tạo contact mới
            Intent intent = new Intent(); // Tạo Intent để trả kết quả
            intent.putExtra("newContact", newContact); // Đưa contact mới vào Intent
            setResult(RESULT_OK, intent); // Trả kết quả OK
        }
        finish(); // Đóng Activity và trở về Activity trước đó
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
