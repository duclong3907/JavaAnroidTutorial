package com.example.myphotoalbum.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myphotoalbum.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddImageActivity extends AppCompatActivity {

    private ImageView imageViewAddImage;
    private EditText editTextAddTitle, editTextAddDescription;
    private Button buttonSave;

    ActivityResultLauncher<Intent> activityResultLauncherForSelectImage;
    private Bitmap selectedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Image");
        setContentView(R.layout.activity_add_image);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddDescription = findViewById(R.id.editTextAddDescription);
        buttonSave = findViewById(R.id.buttonSave);

        //register
        registerActivityForSelectImage();

        imageViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String permission;

               if(Build.VERSION.SDK_INT >= 33){
                   permission = Manifest.permission.READ_MEDIA_IMAGES;
               } else {
                   permission = Manifest.permission.READ_EXTERNAL_STORAGE;
               }

                if (ContextCompat.checkSelfPermission(AddImageActivity.this
                        , permission) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(AddImageActivity.this
                            ,new String[] {permission},1);
                }
                else
                {
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //before API 30 -> startActivityForResult(imageIntent,2);
                    //ActivityResultLauncher
                    activityResultLauncherForSelectImage.launch(imageIntent);
               }

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage == null){
                    Toast.makeText(AddImageActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                } else {
                    String title = editTextAddTitle.getText().toString();
                    String description = editTextAddDescription.getText().toString();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    scaledImage = makeSmall(selectedImage, 300);
                    scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    byte[] image = outputStream.toByteArray();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //before API 30 -> startActivityForResult(imageIntent,2);
            //ActivityResultLauncher
            activityResultLauncherForSelectImage.launch(imageIntent);
        }
    }

    public void registerActivityForSelectImage(){
        activityResultLauncherForSelectImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if(resultCode == RESULT_OK && data != null){
                            try {
                                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                                imageViewAddImage.setImageBitmap(selectedImage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );
    }

    public Bitmap makeSmall(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float ratio = (float) width / (float) height;
        if(ratio > 1){
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

}