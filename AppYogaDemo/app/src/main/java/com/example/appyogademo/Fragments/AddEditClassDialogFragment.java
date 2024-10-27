package com.example.appyogademo.Fragments;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.appyogademo.Models.User;
import com.example.appyogademo.Models.YogaClass;
import com.example.appyogademo.R;
import com.example.appyogademo.Utils.ValidateInput;
import com.example.appyogademo.ViewModels.UserViewModel;
import com.example.appyogademo.ViewModels.YogaClassViewModel;
import com.example.appyogademo.databinding.DialogAddEditClassBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditClassDialogFragment extends DialogFragment {
    private YogaClass yogaClass;
    private OnSaveListener onSaveListener;
    private Uri imageUri;
    private List<User> teacherList;
    private User selectedTeacher;
    private UserViewModel userViewModel;
    private YogaClassViewModel yogaClassViewModel;
    private int courseId;

    DialogAddEditClassBinding binding;

    public interface OnSaveListener {
        void onSave(YogaClass yogaClass);
    }

    public static AddEditClassDialogFragment newInstance(@Nullable YogaClass yogaClass, int courseId, OnSaveListener listener) {
        AddEditClassDialogFragment fragment = new AddEditClassDialogFragment();
        fragment.yogaClass = yogaClass;
        fragment.onSaveListener = listener;
        fragment.courseId = courseId; // Set courseId
        return fragment;
    }

    private ActivityResultLauncher<Intent> galleryLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.dialog_add_edit_class, container, false);

        binding = DialogAddEditClassBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this, new UserViewModel.Factory(requireContext())).get(UserViewModel.class);

        userViewModel.getAllTeachers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                teacherList = users;
                setupTeacherSpinner();
                if (yogaClass != null) {
                    populateFields();
                }
            }
        });

        yogaClassViewModel = new YogaClassViewModel(requireContext(), courseId);

        // Set up date picker for inputClassDate
        binding.inputClassDate.setOnClickListener(v -> showDatePicker());

        // Set up gallery launcher
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            binding.imageClass.setImageURI(imageUri);
                            binding.inputUrlImage.setVisibility(View.GONE);
                        }
                    }
                });

        // Show popup menu on btnChooseImage click
        binding.btnChooseImage.setOnClickListener(v -> showImageOptionsMenu());

        // URL input listener to display image when URL is entered
        binding.inputUrlImage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String url = s.toString().trim();
                if (!TextUtils.isEmpty(url)) {
                    loadImageFromUrl(url);
                }
            }
        });

        // Cancel button event
        binding.btnCancel.setOnClickListener(v -> dismiss());

        // Add/Save button event
        binding.btnAddEditClass.setOnClickListener(v -> {
            String className = binding.inputClassName.getText().toString();
            String classDate = binding.inputClassDate.getText().toString();
            String comments = binding.inputComments.getText().toString();

            if (!validateInputs()) {
                return;
            }

            // If editing an existing YogaClass, update values
            if (yogaClass == null) {
                String image = imageUri != null ? imageUri.toString() : binding.inputUrlImage.getText().toString();
                yogaClass = new YogaClass(0, className, parseDate(classDate), comments, image, selectedTeacher.getId(), courseId);
                Boolean result = yogaClassViewModel.insert(yogaClass);
                if(result) {
                    onSaveListener.onSave(yogaClass);
                    dismiss();
                }
            } else {
                yogaClass.setClassName(className);
                yogaClass.setComments(comments);
                yogaClass.setDate(parseDate(classDate));
                yogaClass.setTeacherId(selectedTeacher.getId());

                // Save image URI or URL
                if (imageUri != null) {
                    yogaClass.setImage(imageUri.toString());
                } else if (!TextUtils.isEmpty(binding.inputUrlImage.getText().toString())) {
                    yogaClass.setImage(binding.inputUrlImage.getText().toString());
                }
                Boolean result = yogaClassViewModel.update(yogaClass);
                if(result) {
                    onSaveListener.onSave(yogaClass);
                    dismiss();
                }
            }
        });

        return binding.getRoot();
    }

    private boolean validateInputs() {
        boolean isValid = true;
        clearAllErrors();

        if(imageUri == null && TextUtils.isEmpty(binding.inputUrlImage.getText().toString())) {
            binding.errorUrlImage.setText("Please choose an image");
            binding.errorUrlImage.setVisibility(View.VISIBLE);
            isValid = false;
        }

        if (ValidateInput.isEmpty(binding.inputClassName)) {
            binding.errorClassName.setText("Class Name cannot be empty");
            binding.errorClassName.setVisibility(View.VISIBLE);
            isValid = false;
        }

        if (ValidateInput.isEmpty(binding.inputClassDate)) {
            binding.errorClassDate.setText("Class Date cannot be empty");
            binding.errorClassDate.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Kiểm tra thời gian
        if (ValidateInput.isEmpty(binding.inputComments)) {
            binding.errorComments.setText("Comments cannot be empty");
            binding.errorComments.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid;
    }

    private void clearAllErrors() {
        binding.errorClassName.setVisibility(View.GONE);
        binding.errorClassDate.setVisibility(View.GONE);
        binding.errorComments.setVisibility(View.GONE);
        binding.errorUrlImage.setVisibility(View.GONE);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it to the EditText
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    binding.inputClassDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showImageOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.btnChooseImage);
        popupMenu.getMenuInflater().inflate(R.menu.menu_image_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_upload_image) {
                openGallery();
                return true;
            } else if (item.getItemId() == R.id.menu_enter_url_image) {
                binding.inputUrlImage.setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void loadImageFromUrl(String url) {
        Picasso.get()
                .load(url)
                .error(R.drawable.yoga_class) // Use an error image drawable if loading fails
                .placeholder(R.drawable.yoga_class) // Placeholder while loading
                .into(binding.imageClass, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Image loaded successfully, hide input if needed
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupTeacherSpinner() {
        List<String> teacherNames = new ArrayList<>();
        for (User teacher : teacherList) {
            teacherNames.add(teacher.getFullName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, teacherNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTeachers.setAdapter(adapter);

        binding.spinnerTeachers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeacher = teacherList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeacher = null;
            }
        });
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void populateFields() {
        binding.inputClassName.setText(yogaClass.getClassName());
        binding.inputClassDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(yogaClass.getDate()));
        binding.inputComments.setText(yogaClass.getComments());
        if (yogaClass.getImage() != null) {
            if (yogaClass.getImage().startsWith("content://")) {
                imageUri = Uri.parse(yogaClass.getImage());
                binding.imageClass.setImageURI(imageUri);
                binding.inputUrlImage.setVisibility(View.GONE);
            } else {
                binding.inputUrlImage.setText(yogaClass.getImage());
                loadImageFromUrl(yogaClass.getImage());
            }
        }
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId() == yogaClass.getTeacherId()) {
                binding.spinnerTeachers.setSelection(i);
                break;
            }
        }
    }
}