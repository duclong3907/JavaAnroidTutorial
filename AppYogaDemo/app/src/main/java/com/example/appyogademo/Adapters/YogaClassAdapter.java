package com.example.appyogademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.TimeUtils;
import com.example.appyogademo.databinding.ClassItemBinding;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class YogaClassAdapter extends RecyclerView.Adapter<YogaClassAdapter.YogaClassViewHolder> {
    private List<YogaClass> yogaClassList;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;
    private OnItemClickListener itemClickListener;
    private final Context context;

    public YogaClassAdapter(List<YogaClass> yogaClassList, Context context) {
        this.yogaClassList = yogaClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public YogaClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ClassItemBinding binding = ClassItemBinding.inflate(inflater, parent, false);
        return new YogaClassViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaClassViewHolder holder, int position) {
        YogaClass yogaClass = yogaClassList.get(position);
        holder.binding.className.setText(yogaClass.getClassName());

        // Set image using Picasso
        Picasso.get()
                .load(yogaClass.getImage())
                .placeholder(R.drawable.yoga_class) // Placeholder image
                .error(R.drawable.yoga_class) // Error image
                .into(holder.binding.imageClass);
        // Set other views using holder.binding

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(yogaClass);
            }
        });

        // Handle delete button click
        holder.binding.btnDeleteClass.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(yogaClass);
            }
        });

        // Handle edit button click
        holder.binding.btnEditClass.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(yogaClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yogaClassList.size();
    }

    // Thiết lập listener cho item click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    // Thiết lập listener cho nút chỉnh sửa
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    // Thiết lập listener cho nút xóa
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    static class YogaClassViewHolder extends RecyclerView.ViewHolder {
        private final ClassItemBinding binding;

        public YogaClassViewHolder(ClassItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Interface for edit button click
    public interface OnEditClickListener {
        void onEditClick(YogaClass yogaClass);
    }

    // Interface for delete button click
    public interface OnDeleteClickListener {
        void onDeleteClick(YogaClass yogaClass);
    }

    // Interface for item click
    public interface OnItemClickListener {
        void onItemClick(YogaClass yogaClass);
    }


}