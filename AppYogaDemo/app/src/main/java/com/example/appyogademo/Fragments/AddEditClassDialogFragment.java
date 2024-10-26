package com.example.appyogademo.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;

public class AddEditClassDialogFragment extends DialogFragment {
    private EditText inputClassName, inputClassDate, inputComments;
    private ImageView imageClass, btnChooseImage;
    private Spinner spinnerTeachers;
    private Button btnCancel, btnAdd;
    private YogaClass yogaClass;
    private OnSaveListener onSaveListener;

    // Interface to handle save actions
    public interface OnSaveListener {
        void onSave(YogaClass yogaClass);
    }

    public AddEditClassDialogFragment(@Nullable YogaClass yogaClass, OnSaveListener listener) {
        this.yogaClass = yogaClass;
        this.onSaveListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_edit_class, container, false);

        inputClassName = view.findViewById(R.id.inputClassName);
        inputClassDate = view.findViewById(R.id.inputClassDate);
        inputComments = view.findViewById(R.id.inputComments);
        spinnerTeachers = view.findViewById(R.id.spinnerTeachers);
        imageClass = view.findViewById(R.id.imageClass);
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnAdd = view.findViewById(R.id.btnAddEditClass);

        // Nếu là edit, điền thông tin của YogaClass vào các trường
        if (yogaClass != null) {
            inputClassName.setText(yogaClass.getClassName());
            inputClassDate.setText(yogaClass.getDate().toString());
            inputComments.setText(yogaClass.getComments());
            // Cài đặt hình ảnh nếu có
            // classImage.setImageBitmap(...); // Xử lý ảnh ở đây
        }

        // Sự kiện nút Cancel
        btnCancel.setOnClickListener(v -> dismiss());

        // Sự kiện nút Add/Save
        btnAdd.setOnClickListener(v -> {
            String className = inputClassName.getText().toString();
            String classDate = inputClassDate.getText().toString();
            String comments = inputComments.getText().toString();

            if (TextUtils.isEmpty(className) || TextUtils.isEmpty(classDate)) {
                inputClassName.setError("Required");
                inputClassDate.setError("Required");
                return;
            }

            // Nếu đang chỉnh sửa YogaClass, cập nhật các giá trị
            if (yogaClass == null) {
                yogaClass = new YogaClass();
            }
            yogaClass.setClassName(className);
            yogaClass.setComments(comments);
            // Chuyển đổi ngày từ chuỗi thành Date

            // Gọi callback để lưu lớp học
            if (onSaveListener != null) {
                onSaveListener.onSave(yogaClass);
            }

            dismiss(); // Đóng dialog
        });

        return view;
    }
}
