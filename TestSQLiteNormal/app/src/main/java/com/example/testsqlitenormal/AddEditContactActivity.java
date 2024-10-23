package com.example.testsqlitenormal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsqlitenormal.Models.Contact;

public class AddEditContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private Button buttonSave;
    private Contact contact;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);

        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            editTextName.setText(contact.getName());
            editTextEmail.setText(contact.getEmail());
            isEditMode = true;
        }

        buttonSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();

        if (isEditMode) {
            contact.setName(name);
            contact.setEmail(email);
            Intent intent = new Intent();
            intent.putExtra("contact", contact);
            setResult(RESULT_OK, intent);
        } else {
            Contact newContact = new Contact(name, email, 0);
            Intent intent = new Intent();
            intent.putExtra("newContact", newContact);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
