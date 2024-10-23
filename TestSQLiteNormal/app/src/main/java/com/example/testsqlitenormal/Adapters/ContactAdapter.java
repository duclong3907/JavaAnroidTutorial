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
    private List<Contact> contactList;
    private final Context context;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;
    // Biến để lưu listener
    private OnItemClickListener itemClickListener;

    public ContactAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    // Phương thức để thiết lập listener cho item click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());

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

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnEditClickListener {
        void onEditClick(Contact contact);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Contact contact);
    }

    // Thêm interface cho sự kiện click item
    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

}
