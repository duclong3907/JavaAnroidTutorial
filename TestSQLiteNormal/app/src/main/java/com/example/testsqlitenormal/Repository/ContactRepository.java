package com.example.testsqlitenormal.Repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.testsqlitenormal.Database.DatabaseHelper;
import com.example.testsqlitenormal.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private final DatabaseHelper databaseHelper; // Trình quản lý cơ sở dữ liệu
    private final MutableLiveData<List<Contact>> contacts; // Dữ liệu liên tục chứa danh sách contact

    // Constructor để khởi tạo ContactRepository
    public ContactRepository(Context context) {
        databaseHelper = new DatabaseHelper(context); // Khởi tạo DatabaseHelper
        contacts = new MutableLiveData<>(); // Khởi tạo MutableLiveData
        loadContacts(); // Tải danh sách contact ngay khi khởi tạo
    }

    // Trả về LiveData chứa danh sách tất cả contact
    public LiveData<List<Contact>> getAllContacts() {
        return contacts; // Trả về danh sách contact hiện tại
    }

    // Lấy danh sách contact đã được lọc theo truy vấn
    public LiveData<List<Contact>> getFilteredContacts(String query) {
        MutableLiveData<List<Contact>> filteredContacts = new MutableLiveData<>(); // Khởi tạo LiveData cho danh sách đã lọc
        List<Contact> allContacts = databaseHelper.getAllContacts(); // Lấy danh sách tất cả contact
        List<Contact> result = new ArrayList<>(); // Danh sách lưu trữ kết quả đã lọc

        // Duyệt qua từng contact để tìm kiếm theo tên hoặc email
        for (Contact contact : allContacts) {
            // Kiểm tra nếu tên hoặc email chứa chuỗi truy vấn (không phân biệt chữ hoa/thường)
            if (contact.getName().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getEmail().toLowerCase().contains(query.toLowerCase())) {
                result.add(contact); // Thêm contact vào kết quả nếu tìm thấy
            }
        }
        filteredContacts.setValue(result); // Cập nhật giá trị cho LiveData
        return filteredContacts; // Trả về danh sách đã lọc
    }

    // Thêm một contact mới
    public void insert(Contact contact) {
        databaseHelper.insertContact(contact); // Gọi phương thức chèn contact vào cơ sở dữ liệu
        loadContacts(); // Tải lại danh sách contact sau khi chèn
    }

    // Cập nhật thông tin của một contact
    public void update(Contact contact) {
        databaseHelper.updateContact(contact); // Gọi phương thức cập nhật contact trong cơ sở dữ liệu
        loadContacts(); // Tải lại danh sách contact sau khi cập nhật
    }

    // Xóa một contact
    public void delete(Contact contact) {
        databaseHelper.deleteContact(contact); // Gọi phương thức xóa contact trong cơ sở dữ liệu
        loadContacts(); // Tải lại danh sách contact sau khi xóa
    }

    // Tải danh sách contact từ cơ sở dữ liệu
    private void loadContacts() {
        contacts.postValue(databaseHelper.getAllContacts()); // Cập nhật LiveData với danh sách contact mới
    }
}
