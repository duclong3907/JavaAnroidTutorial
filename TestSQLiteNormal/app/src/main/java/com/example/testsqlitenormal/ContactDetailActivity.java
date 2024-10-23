package com.example.testsqlitenormal;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsqlitenormal.Models.Contact;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewEmail;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("contact");

        // Hiển thị thông tin
        if (contact != null) {
            textViewName.setText(contact.getName());
            textViewEmail.setText(contact.getEmail());
        }
    }
}
