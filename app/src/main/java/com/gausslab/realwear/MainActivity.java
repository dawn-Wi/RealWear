package com.gausslab.realwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileService fileService = App.getFileService();
        FirebaseDataSource firebaseDataSource=new FirebaseDataSource();
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setFirebaseDataSource(firebaseDataSource);
        fileService.setFirebaseDataSource(firebaseDataSource);
    }
}