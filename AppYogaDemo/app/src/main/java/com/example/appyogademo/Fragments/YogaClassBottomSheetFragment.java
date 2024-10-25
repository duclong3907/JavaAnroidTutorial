package com.example.appyogademo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appyogademo.Adapters.YogaClassAdapter;
import com.example.appyogademo.Views.AddEditCourseActivity;
import com.example.appyogademo.Views.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;

import java.util.List;

public class YogaClassBottomSheetFragment extends BottomSheetDialogFragment {
    private List<YogaClass> yogaClassList;

    public YogaClassBottomSheetFragment(List<YogaClass> yogaClassList) {
        this.yogaClassList = yogaClassList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_classes, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvYogaClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        YogaClassAdapter adapter = new YogaClassAdapter(yogaClassList);
        recyclerView.setAdapter(adapter);


        //xu ly su kien kien nut them
        view.findViewById(R.id.fabClasses).setOnClickListener(v -> {
            AddEditClassDialogFragment addEditClassDialogFragment = new AddEditClassDialogFragment(null, yogaClass -> {
                // Handle the save action here
            });
            addEditClassDialogFragment.show(getParentFragmentManager(), "AddEditClassDialogFragment");
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Lấy ra BottomSheet và điều chỉnh chiều cao
        View bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.75);
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);  // Đặt trạng thái mở rộng
    }
}
