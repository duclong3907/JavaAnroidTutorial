package com.example.appyogademo.Views.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appyogademo.R;
import com.example.appyogademo.ViewModels.AuthViewModel;
import com.example.appyogademo.Views.MainActivity;
import com.example.appyogademo.databinding.ActivityLoginScreenBinding;
import com.example.appyogademo.databinding.ActivityRegisterScreenBinding;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;
    AuthViewModel authViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new AuthViewModel(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                boolean result = authViewModel.login(email, password);
                if(result){
                    Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }
}