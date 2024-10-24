package com.example.testsqlitenormal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testsqlitenormal.Models.Contact;
import com.example.testsqlitenormal.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactList; // Danh sách các contact
    private final Context context; // Context của ứng dụng
    private OnEditClickListener editClickListener; // Listener cho sự kiện chỉnh sửa
    private OnDeleteClickListener deleteClickListener; // Listener cho sự kiện xóa
    private OnItemClickListener itemClickListener; // Listener cho sự kiện nhấn vào item

    public ContactAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ViewHolder mới từ layout contact_list_item
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    // Thiết lập listener cho item click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    //thiết lập dữ liệu cho ViewHolder và xử lý sự kiện click
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position); // Lấy contact tại vị trí hiện tại
        holder.name.setText(contact.getName()); // Hiển thị tên contact
        holder.email.setText(contact.getEmail()); // Hiển thị email contact

        // Xử lý click vào item
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(contact);
            }
        });


        // Xử lý sự kiện nhấn nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(contact);
            }
        });

        // Xử lý sự kiện nhấn nút chỉnh sửa
        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Thiết lập listener cho nút chỉnh sửa
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    // Thiết lập listener cho nút xóa
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    // Lớp ViewHolder để lưu trữ các View
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        ImageButton btnEdit;
        ImageButton btnDelete;

        // Constructor để khởi tạo các View
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name); // Lấy View tên
            email = itemView.findViewById(R.id.email); // Lấy View email
            btnEdit = itemView.findViewById(R.id.btnEdit); // Lấy nút chỉnh sửa
            btnDelete = itemView.findViewById(R.id.btnDelete); // Lấy nút xóa
        }
    }

    // Thêm interface cho sự kiện click nút chỉnh sửa
    public interface OnEditClickListener {
        void onEditClick(Contact contact);
    }

    // Thêm interface cho sự kiện click nút xóa
    public interface OnDeleteClickListener {
        void onDeleteClick(Contact contact);
    }

    // Thêm interface cho sự kiện click item
    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

}
