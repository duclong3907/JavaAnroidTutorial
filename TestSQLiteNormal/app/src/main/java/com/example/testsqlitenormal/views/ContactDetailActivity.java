package com.example.testsqlitenormal.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.R;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView textViewName; // Biến để hiển thị tên liên hệ
    private TextView textViewEmail; // Biến để hiển thị email liên hệ
    private Button buttonEdit; // Nút để chỉnh sửa thông tin liên hệ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail); // Thiết lập layout cho Activity

        // Kích hoạt biểu tượng back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Khởi tạo các View
        textViewName = findViewById(R.id.textViewName); // Lấy TextView để hiển thị tên
        textViewEmail = findViewById(R.id.textViewEmail); // Lấy TextView để hiển thị email

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent(); // Lấy Intent đã gửi đến Activity này
        Contact contact = (Contact) intent.getSerializableExtra("contact"); // Lấy contact từ Intent

        // Hiển thị thông tin liên hệ
        if (contact != null) { // Kiểm tra nếu contact không null
            textViewName.setText(contact.getName()); // Hiển thị tên liên hệ
            textViewEmail.setText(contact.getEmail()); // Hiển thị email liên hệ
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
