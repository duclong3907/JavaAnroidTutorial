package com.example.appyogademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appyogademo.Models.Course;
import com.example.appyogademo.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> CourseList; // Danh sách các Course
    private final Context context; // Context của ứng dụng
    private OnEditClickListener editClickListener; // Listener cho sự kiện chỉnh sửa
    private OnDeleteClickListener deleteClickListener; // Listener cho sự kiện xóa
    private OnItemClickListener itemClickListener; // Listener cho sự kiện nhấn vào item

    public CourseAdapter(List<Course> CourseList, Context context) {
        this.CourseList = CourseList;
        this.context = context;
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ViewHolder mới từ layout Course_list_item
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    // Thiết lập listener cho item click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    //thiết lập dữ liệu cho ViewHolder và xử lý sự kiện click
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course Course = CourseList.get(position); // Lấy Course tại vị trí hiện tại
        holder.courseName.setText(Course.getCourseName());
        holder.classType.setText(Course.getClassType());

        // Xử lý click vào item
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(Course);
            }
        });


        // Xử lý sự kiện nhấn nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(Course);
            }
        });

        // Xử lý sự kiện nhấn nút chỉnh sửa
        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(Course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CourseList.size();
    }

    // Thiết lập listener cho nút chỉnh sửa
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    // Thiết lập listener cho nút xóa
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public List<Course> getCourseList() {
        return CourseList;
    }

    // Lớp ViewHolder để lưu trữ các View
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView classType;
        ImageButton btnEdit;
        ImageButton btnDelete;

        // Constructor để khởi tạo các View
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName); // Lấy View tên
            classType = itemView.findViewById(R.id.classType); // Lấy View email
            btnEdit = itemView.findViewById(R.id.btnEditCourse); // Lấy nút chỉnh sửa
            btnDelete = itemView.findViewById(R.id.btnDeleteCourse); // Lấy nút xóa
        }
    }

    // Thêm interface cho sự kiện click nút chỉnh sửa
    public interface OnEditClickListener {
        void onEditClick(Course Course);
    }

    // Thêm interface cho sự kiện click nút xóa
    public interface OnDeleteClickListener {
        void onDeleteClick(Course Course);
    }

    // Thêm interface cho sự kiện click item
    public interface OnItemClickListener {
        void onItemClick(Course Course);
    }

}
