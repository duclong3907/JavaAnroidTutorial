package com.example.myphotoalbum.UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoalbum.Adapters.MyImagesAdapter;
import com.example.myphotoalbum.R;
import com.example.myphotoalbum.ViewModels.MyImagesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private MyImagesViewModel myImagesViewModel;
    private RecyclerView rv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);

        rv.setLayoutManager(new LinearLayoutManager(this));

        MyImagesAdapter adapter = new MyImagesAdapter();
        rv.setAdapter(adapter);

        myImagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MyImagesViewModel.class);
        myImagesViewModel.getAllImages().observe(this, myImages -> {
            adapter.setImagesList(myImages);
        });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

    }
}