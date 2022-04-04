package com.gausslab.realwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class SplashActivity extends AppCompatActivity {
    private MutableLiveData<Boolean> connectLoaded = new MutableLiveData<>(false);
    FileService fileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                FileService.LocalBinder binder = (FileService.LocalBinder) service;
                fileService = binder.getService();
                App.setFileService(fileService);
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent(this, FileService.class);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

//        Intent intent = new Intent(this, FileService.class);
//        isConnectLoaded().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLoaded) {
//                if(isLoaded){
//                    startService(intent);
//                    bindService(intent, connection, Context.BIND_AUTO_CREATE);
//                    setContentView(R.layout.activity_splash);
//                }
//            }
//        });


    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        Intent i = new Intent(this, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        startActivity(i);
//        isConnectLoaded().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLoaded) {
//                if (isLoaded) {
//                    startActivity(i);
//                }
//            }
//        });
    }

    public LiveData<Boolean> isConnectLoaded() {
        return connectLoaded;
    }

}