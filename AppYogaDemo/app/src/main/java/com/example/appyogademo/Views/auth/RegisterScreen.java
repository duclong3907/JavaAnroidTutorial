package com.example.appyogademo.Views.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appyogademo.Models.User;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.ValidateInput;
import com.example.appyogademo.ViewModels.AuthViewModel;
import com.example.appyogademo.databinding.ActivityRegisterScreenBinding;

import java.util.UUID;

public class RegisterScreen extends AppCompatActivity {
    ActivityRegisterScreenBinding binding; // Khai báo biến binding dùng để ánh xạ các View từ layout
    AuthViewModel authViewModel; // Khai báo biến authViewModel để thực hiện các thao tác liên quan đến xác thực


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater()); // Ánh xạ View từ layout

        setContentView(binding.getRoot());

        authViewModel = new AuthViewModel(this); // Khởi tạo authViewModel

       binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String fullName = binding.fullName.getText().toString();
               String phoneNumber = binding.phoneNumber.getText().toString();
               String email = binding.email.getText().toString();
               String password = binding.password.getText().toString();
               String role = "Admin";
               String guid = UUID.randomUUID().toString();
               User user = new User(guid, fullName, phoneNumber, email, null, null, null, password, role);

               try {
                   boolean result = authViewModel.register(user);
                   if (result) {
                       Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                       binding.fullName.setText("");
                       binding.phoneNumber.setText("");
                       binding.email.setText("");
                       binding.password.setText("");
                       startActivity(intent);
                   }
                } catch (Exception e) {
                     Log.e("RegisterScreen", e.getMessage());
                     Toast.makeText(RegisterScreen.this, "Signup Failed", Toast.LENGTH_SHORT).show();
               }
           }
       });
       binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
               startActivity(intent);
           }
       });
    }
}