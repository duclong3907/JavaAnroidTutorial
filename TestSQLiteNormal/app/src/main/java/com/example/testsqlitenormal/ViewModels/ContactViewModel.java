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
    private final ContactRepository repository;
    private final LiveData<List<Contact>> contacts;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>(); // Từ khóa tìm kiếm
    private final LiveData<List<Contact>> filteredContacts; // Danh sách liên hệ đã lọc

    // Constructor nhận Context để khởi tạo Repository
    public ContactViewModel(Context context) {
        repository = new ContactRepository(context);
        contacts = repository.getAllContacts();

        // Quan sát từ khóa tìm kiếm và lọc danh sách
        filteredContacts = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return contacts; // Trả về tất cả liên hệ
            } else {
                return repository.getFilteredContacts(query); // Lọc theo từ khóa tìm kiếm
            }
        });

    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public LiveData<List<Contact>> getFilteredContacts(String text) {
        return filteredContacts; // Trả về danh sách liên hệ đã lọc
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query); // Cập nhật từ khóa tìm kiếm
    }

    public void insert(Contact contact) {
        repository.insert(contact);
    }

    public void update(Contact contact) {
        repository.update(contact);
    }

    public void delete(Contact contact) {
        repository.delete(contact);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ContactViewModel.class)) {
                return (T) new ContactViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
