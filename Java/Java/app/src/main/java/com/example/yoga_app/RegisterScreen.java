// RegisterScreen.java
package com.example.yoga_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_app.databinding.ActivityRegisterScreenBinding;

public class RegisterScreen extends AppCompatActivity {

    private static final String TAG = "RegisterScreen";
    ActivityRegisterScreenBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelper(this);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.fullName.getText().toString();
                String phoneNumber = binding.phoneNumber.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String role = "Admin";

                if (fullName.equals("") || phoneNumber.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(RegisterScreen.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (db.checkEmail(email)) {
                            Toast.makeText(RegisterScreen.this, "Email already exists", Toast.LENGTH_SHORT).show();
                        } else if (db.checkPhone(phoneNumber)) {
                            Toast.makeText(RegisterScreen.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean insert = db.insert(fullName, phoneNumber, email, null, null, null, password, role);
                            if (insert) {
                                Toast.makeText(RegisterScreen.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                binding.fullName.setText("");
                                binding.phoneNumber.setText("");
                                binding.email.setText("");
                                binding.password.setText("");
                                startActivity(intent); // Start the LoginScreen activity
                            } else {
                                Toast.makeText(RegisterScreen.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error during signup", e);
                        System.out.println("An error occurred: " + e.getMessage());
                        Toast.makeText(RegisterScreen.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}