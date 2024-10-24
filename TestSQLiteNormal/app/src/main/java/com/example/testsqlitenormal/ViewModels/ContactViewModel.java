package com.example.testsqlitenormal.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.Repository.ContactRepository;
import java.util.List;

public class ContactViewModel extends ViewModel {
    private final ContactRepository repository; // Repository quản lý dữ liệu liên hệ
    private final LiveData<List<Contact>> contacts; // Danh sách tất cả liên hệ
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>(); // Từ khóa tìm kiếm
    private final LiveData<List<Contact>> filteredContacts; // Danh sách liên hệ đã lọc

    // Constructor nhận Context để khởi tạo Repository
    public ContactViewModel(Context context) {
        repository = new ContactRepository(context); // Khởi tạo ContactRepository
        contacts = repository.getAllContacts(); // Lấy danh sách tất cả liên hệ từ repository

        // Quan sát từ khóa tìm kiếm và lọc danh sách liên hệ
        filteredContacts = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return contacts; // Nếu từ khóa tìm kiếm rỗng, trả về tất cả liên hệ
            } else {
                return repository.getFilteredContacts(query); // Lọc liên hệ theo từ khóa tìm kiếm
            }
        });
    }

    // Trả về LiveData chứa danh sách tất cả liên hệ
    public LiveData<List<Contact>> getContacts() {
        return contacts; // Trả về danh sách liên hệ
    }

    // Trả về danh sách liên hệ đã lọc
    public LiveData<List<Contact>> getFilteredContacts(String text) {
        return filteredContacts; // Trả về danh sách đã lọc theo từ khóa
    }

    // Cập nhật từ khóa tìm kiếm
    public void setSearchQuery(String query) {
        searchQuery.setValue(query); // Cập nhật giá trị của từ khóa tìm kiếm
    }

    // Thêm một contact mới
    public void insert(Contact contact) {
        repository.insert(contact); // Gọi phương thức insert từ repository
    }

    // Cập nhật thông tin của một contact
    public void update(Contact contact) {
        repository.update(contact); // Gọi phương thức update từ repository
    }

    // Xóa một contact
    public void delete(Contact contact) {
        repository.delete(contact); // Gọi phương thức delete từ repository
    }

    // Factory để tạo ContactViewModel với context
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Context context; // Context để khởi tạo ViewModel

        // Constructor nhận Context
        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            // Kiểm tra nếu modelClass là ContactViewModel
            if (modelClass.isAssignableFrom(ContactViewModel.class)) {
                return (T) new ContactViewModel(context); // Tạo và trả về ContactViewModel
            }
            throw new IllegalArgumentException("Unknown ViewModel class"); // Thông báo nếu không đúng loại ViewModel
        }
    }
}
