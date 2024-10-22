package com.example.myphotoalbum.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myphotoalbum.Models.MyImages;

@Database(entities = {MyImages.class}, version = 1)
public abstract class MyImagesDatabase extends RoomDatabase {
    private static MyImagesDatabase instance;
    public abstract MyImagesDAO myImagesDAO();

    public static synchronized MyImagesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyImagesDatabase.class, "my_images_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
