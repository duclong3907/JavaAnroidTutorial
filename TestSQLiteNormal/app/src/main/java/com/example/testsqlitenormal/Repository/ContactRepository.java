package com.example.testsqlitenormal.Repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.testsqlitenormal.Database.DatabaseHelper;
import com.example.testsqlitenormal.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<List<Contact>> contacts;

    public ContactRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
        contacts = new MutableLiveData<>();
        loadContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contacts;
    }

    public LiveData<List<Contact>> getFilteredContacts(String query) {
        MutableLiveData<List<Contact>> filteredContacts = new MutableLiveData<>();
        List<Contact> allContacts = databaseHelper.getAllContacts();
        List<Contact> result = new ArrayList<>();

        for (Contact contact : allContacts) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getEmail().toLowerCase().contains(query.toLowerCase())) {
                result.add(contact);
            }
        }
        filteredContacts.setValue(result);
        return filteredContacts;
    }


    public void insert(Contact contact) {
        databaseHelper.insertContact(contact);
        loadContacts();
    }

    public void update(Contact contact) {
        databaseHelper.updateContact(contact);
        loadContacts();
    }

    public void delete(Contact contact) {
        databaseHelper.deleteContact(contact);
        loadContacts();
    }


    private void loadContacts() {
        contacts.postValue(databaseHelper.getAllContacts());
    }
}
