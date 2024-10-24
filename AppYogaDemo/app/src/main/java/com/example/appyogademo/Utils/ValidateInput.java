package com.example.appyogademo.Utils;

import android.text.TextUtils;
import android.widget.EditText;

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

}
