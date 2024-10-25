package com.example.appyogademo.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;

import java.util.List;

public class YogaClassAdapter extends RecyclerView.Adapter<YogaClassAdapter.YogaClassViewHolder> {
    private List<YogaClass> yogaClassList;

    public YogaClassAdapter(List<YogaClass> yogaClassList) {
        this.yogaClassList = yogaClassList;
    }

    @NonNull
    @Override
    public YogaClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_item, parent, false);
        return new YogaClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaClassViewHolder holder, int position) {
        YogaClass yogaClass = yogaClassList.get(position);
        holder.className.setText(yogaClass.getClassName());
        // Bạn có thể thêm các thông tin khác của yogaClass vào đây
    }

    @Override
    public int getItemCount() {
        return yogaClassList.size();
    }

    static class YogaClassViewHolder extends RecyclerView.ViewHolder {
        TextView className;

        public YogaClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
        }
    }
}
