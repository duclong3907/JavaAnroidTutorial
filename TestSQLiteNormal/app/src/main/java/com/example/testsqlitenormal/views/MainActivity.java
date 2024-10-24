package com.example.testsqlitenormal.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testsqlitenormal.Adapters.ContactAdapter;
import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.R;
import com.example.testsqlitenormal.ViewModels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel; // ViewModel để quản lý dữ liệu liên hệ
    private RecyclerView recyclerView; // RecyclerView để hiển thị danh sách liên hệ
    private ContactAdapter adapter; // Adapter cho RecyclerView
    private EditText searchEditText; // Biến cho EditText tìm kiếm

    // Khởi tạo launcher để nhận kết quả từ Activity thêm hoặc chỉnh sửa liên hệ
    private ActivityResultLauncher<Intent> addEditContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) { // Kiểm tra nếu kết quả trả về thành công
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.hasExtra("newContact")) { // Nếu có contact mới
                            Contact newContact = (Contact) data.getSerializableExtra("newContact");
                            contactViewModel.insert(newContact); // Thêm contact mới
                        } else if (data.hasExtra("contact")) { // Nếu có contact được chỉnh sửa
                            Contact updatedContact = (Contact) data.getSerializableExtra("contact");
                            contactViewModel.update(updatedContact); // Cập nhật contact
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Thiết lập layout cho Activity

        recyclerView = findViewById(R.id.rv); // Khởi tạo RecyclerView
        adapter = new ContactAdapter(new ArrayList<>(), this); // Khởi tạo adapter với danh sách rỗng
        recyclerView.setAdapter(adapter); // Gán adapter cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Thiết lập LayoutManager cho RecyclerView

        // Sử dụng Factory để tạo ViewModel
        ContactViewModel.Factory factory = new ContactViewModel.Factory(this);
        contactViewModel = new ViewModelProvider(this, factory).get(ContactViewModel.class); // Khởi tạo ContactViewModel

        // Quan sát danh sách liên lạc và cập nhật adapter khi dữ liệu thay đổi
        contactViewModel.getContacts().observe(this, contacts -> {
            adapter.getContactList().clear(); // Xóa danh sách cũ
            adapter.getContactList().addAll(contacts); // Thêm tất cả contact mới vào adapter
            adapter.notifyDataSetChanged(); // Thông báo adapter thay đổi dữ liệu
        });

        // Tìm kiếm liên hệ
        searchEditText = findViewById(R.id.searchEditText); // Khởi tạo EditText tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString()); // Lọc danh sách khi người dùng nhập từ khóa
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Xử lý sự kiện nhấn vào nút thêm liên hệ (FAB)
        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class); // Mở AddEditContactActivity
            addEditContactLauncher.launch(intent); // Khởi chạy Activity với launcher
        });

        // Xử lý sự kiện nhấn nút chỉnh sửa trong adapter
        adapter.setOnEditClickListener(contact -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class); // Mở AddEditContactActivity
            intent.putExtra("contact", contact); // Gửi thông tin contact để chỉnh sửa
            addEditContactLauncher.launch(intent); // Khởi chạy Activity với launcher
        });

        // Xử lý sự kiện click vào từng item trong RecyclerView
        adapter.setOnItemClickListener(contact -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class); // Mở ContactDetailActivity
            intent.putExtra("contact", contact); // Gửi thông tin contact để xem chi tiết
            startActivity(intent); // Khởi chạy Activity
        });

        // Xử lý sự kiện nhấn nút xóa trong adapter
        adapter.setOnDeleteClickListener(contact -> {
            new AlertDialog.Builder(MainActivity.this) // Tạo AlertDialog xác nhận xóa
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa liên lạc này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        contactViewModel.delete(contact); // Xóa contact
                        Toast.makeText(MainActivity.this, "Đã xóa liên lạc", Toast.LENGTH_SHORT).show(); // Thông báo đã xóa
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()) // Đóng dialog nếu chọn không
                    .show();
        });
    }

    // Phương thức lọc danh sách contact theo từ khóa
    private void filter(String text) {
        if(text.isEmpty()){
            // Nếu không có từ khóa, quan sát và hiển thị tất cả liên hệ
            contactViewModel.getContacts().observe(this, contacts -> {
                adapter.getContactList().clear();
                adapter.getContactList().addAll(contacts);
                adapter.notifyDataSetChanged();
            });
        } else {
            // Nếu có từ khóa, lọc danh sách liên hệ
            contactViewModel.getContacts().observe(this, contacts -> {
                List<Contact> filteredList = new ArrayList<>(); // Danh sách lưu kết quả lọc
                for (Contact contact : contacts) {
                    // Kiểm tra nếu tên liên hệ chứa từ khóa
                    if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(contact); // Thêm vào danh sách kết quả nếu tìm thấy
                    }
                }
                adapter.getContactList().clear(); // Xóa danh sách cũ
                adapter.getContactList().addAll(filteredList); // Thêm kết quả lọc vào danh sách
                adapter.notifyDataSetChanged(); // Thông báo adapter thay đổi dữ liệu
            });
        }
    }
}
