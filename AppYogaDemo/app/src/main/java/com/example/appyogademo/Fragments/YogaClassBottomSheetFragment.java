package com.example.appyogademo.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyogademo.Adapters.YogaClassAdapter;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;
import com.example.appyogademo.ViewModels.YogaClassViewModel;
import com.example.appyogademo.Views.YogaClassDetailActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class YogaClassBottomSheetFragment extends BottomSheetDialogFragment {
    private List<YogaClass> yogaClassList;
    private int courseId;
    private YogaClassViewModel yogaClassViewModel;

    public static YogaClassBottomSheetFragment newInstance(List<YogaClass> yogaClassList, int courseId) {
        YogaClassBottomSheetFragment fragment = new YogaClassBottomSheetFragment();
        fragment.yogaClassList = yogaClassList;
        Bundle args = new Bundle();
        args.putInt("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getInt("courseId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_classes, container, false);

        yogaClassViewModel = new YogaClassViewModel(getContext(), courseId);
        RecyclerView recyclerView = view.findViewById(R.id.rvYogaClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        YogaClassAdapter adapter = new YogaClassAdapter(yogaClassList, getContext());
        recyclerView.setAdapter(adapter);

        // Handle the click event on the floating action button
        view.findViewById(R.id.fabClasses).setOnClickListener(v -> {
            AddEditClassDialogFragment addEditClassDialogFragment = AddEditClassDialogFragment.newInstance(null, courseId, yogaClass -> {
                yogaClassList.add(yogaClass); // Add the newly created yoga class to the list
                adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
            });

            addEditClassDialogFragment.show(getParentFragmentManager(), "AddEditClassDialogFragment");
        });


//         Handle the click event on the item
        adapter.setOnItemClickListener(yogaClass -> {
            Intent intent = new Intent(getContext(), YogaClassDetailActivity.class);
            intent.putExtra("yogaClass", yogaClass);
            startActivity(intent);
        });

        // Handle the click event on the edit button
        // Handle the click event on the edit button
        adapter.setOnEditClickListener(yogaClass -> {
            AddEditClassDialogFragment addEditClassDialogFragment = AddEditClassDialogFragment.newInstance(yogaClass, courseId, updatedYogaClass -> {
                // Find the index of the updated yoga class in the list
                int index = yogaClassList.indexOf(yogaClass);
                if (index != -1) {
                    // Update the yoga class in the list
                    yogaClassList.set(index, updatedYogaClass);
                    // Notify the adapter that the item has changed
                    adapter.notifyItemChanged(index);
                }
            });

            addEditClassDialogFragment.show(getParentFragmentManager(), "AddEditClassDialogFragment");
        });

        //xu ly su kien click vao nut delete
        // Handle the click event on the delete button
        adapter.setOnDeleteClickListener(yogaClass -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Xóa khóa học")
                    .setMessage("Bạn có chắc chắn muốn xóa class này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        yogaClassViewModel.delete(yogaClass);
                        Toast.makeText(getContext(), "Đã xóa khóa học", Toast.LENGTH_SHORT).show();
                        yogaClassList.remove(yogaClass); // Remove
                        adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Adjust the height of the BottomSheet
        View bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.75);
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);  // Set to expanded state
    }
}
