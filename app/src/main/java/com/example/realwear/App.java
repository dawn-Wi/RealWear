package com.example.realwear;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class App extends Application {
    private static FileService fileService;

    public static String getFileProvider() {
        return "com.gausslab.fileprovider";
    }


    public static FileService getFileService() {
        return fileService;
    }

    public static void setFileService(FileService fs) {
        fileService = fs;
    }


}
