package com.example.testsqlitenormal;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testsqlitenormal.Adapters.ContactAdapter;
import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.Repository.ContactRepository;
import com.example.testsqlitenormal.ViewModels.ContactViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private EditText searchEditText; // Thêm biến cho EditText tìm kiếm

    private ActivityResultLauncher<Intent> addEditContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.hasExtra("newContact")) {
                            Contact newContact = (Contact) data.getSerializableExtra("newContact");
                            contactViewModel.insert(newContact);
                        } else if (data.hasExtra("contact")) {
                            Contact updatedContact = (Contact) data.getSerializableExtra("contact");
                            contactViewModel.update(updatedContact);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        adapter = new ContactAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sử dụng Factory để tạo ViewModel
        ContactViewModel.Factory factory = new ContactViewModel.Factory(this);
        contactViewModel = new ViewModelProvider(this, factory).get(ContactViewModel.class);

        // Quan sát danh sách liên lạc
        contactViewModel.getContacts().observe(this, contacts -> {
            adapter.getContactList().clear();
            adapter.getContactList().addAll(contacts);
            adapter.notifyDataSetChanged();
        });

        // Tìm kiếm liên hệ
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            addEditContactLauncher.launch(intent);
        });

        adapter.setOnEditClickListener(contact -> {
            // Khi nhấn vào nút chỉnh sửa, mở trang AddEditContactActivity
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            intent.putExtra("contact", contact);
            addEditContactLauncher.launch(intent);
        });

        // Xử lý sự kiện click vào từng item trong RecyclerView
        adapter.setOnItemClickListener(contact -> {
            // Khi nhấn vào item, mở trang ContactDetailActivity
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        });

        adapter.setOnDeleteClickListener(contact -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa liên lạc này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        contactViewModel.delete(contact);
                        Toast.makeText(MainActivity.this, "Đã xóa liên lạc", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });


    }


    private void filter(String text) {
       if(text.isEmpty()){
           contactViewModel.getContacts().observe(this, contacts -> {
               adapter.getContactList().clear();
               adapter.getContactList().addAll(contacts);
               adapter.notifyDataSetChanged();
           });
       } else {
           contactViewModel.getContacts().observe(this, contacts -> {
               List<Contact> filteredList = new ArrayList<>();
               for (Contact contact : contacts) {
                   if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                       filteredList.add(contact);
                   }
               }
               adapter.getContactList().clear();
               adapter.getContactList().addAll(filteredList);
               adapter.notifyDataSetChanged();
           });
       }
    }

}
