package com.example.realwear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileService fileService = App.getFileService();
        FirebaseDataSource firebaseDataSource=new FirebaseDataSource();
        fileService.setFirebaseDataSource(firebaseDataSource);
    }
}