package com.example.appyogademo.Utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {
    // Kiểm tra xem EditText có bị để trống không
    public static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return TextUtils.isEmpty(text);
    }

    // Kiểm tra xem giá trị trong EditText có phải là số không
    public static boolean isNumber(EditText editText) {
        String text = editText.getText().toString().trim();
        try {
            Integer.parseInt(text); // Thử chuyển đổi thành số nguyên
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra xem giá trị có phải là số dương không
    public static boolean isPositiveNumber(EditText editText) {
        String text = editText.getText().toString().trim();
        try {
            int number = Integer.parseInt(text);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra xem giá trị trong EditText có phải là số thập phân không (ví dụ cho giá khóa học)
    public static boolean isPositiveDouble(EditText editText) {
        String text = editText.getText().toString().trim();
        try {
            double number = Double.parseDouble(text);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Hàm kiểm tra xem giá trị trong EditText có nằm trong khoảng từ 0 đến 6 không
    public static boolean isValidDayOfWeek(EditText editText) {
        String text = editText.getText().toString().trim();
        try {
            int dayOfWeek = Integer.parseInt(text);
            return dayOfWeek >= 0 && dayOfWeek <= 6; // Trả về true nếu nằm trong khoảng 0-6
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //kiểm tra email
    public static boolean isEmail(EditText editText) {
        String text = editText.getText().toString().trim();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    //kiểm tra số điện thoại
    public static boolean isPhoneNumber(EditText editText) {
        String text = editText.getText().toString().trim();
        return android.util.Patterns.PHONE.matcher(text).matches();
    }

    //kiểm tra full name
    public static String validateFullName(String fullName) {
        if (fullName.isEmpty()) {
            return "Full name cannot be empty";
        }
        Pattern fullNamePattern = Pattern.compile("^[\\p{L}\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = fullNamePattern.matcher(fullName);
        if (!matcher.find()) {
            return "Full name can only contain letters and spaces";
        }
        return null;
    }

    //kiểm tra password
    public static String validatePassword(String password) {
        if (password.isEmpty()) {
            return "Password cannot be empty";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("\\d");

        if (!specialCharPattern.matcher(password).find()) {
            return "Password must contain at least one special character";
        }
        if (!upperCasePattern.matcher(password).find()) {
            return "Password must contain at least one uppercase letter";
        }
        if (!lowerCasePattern.matcher(password).find()) {
            return "Password must contain at least one lowercase letter";
        }
        if (!digitPattern.matcher(password).find()) {
            return "Password must contain at least one digit";
        }
        return null;
    }

}
