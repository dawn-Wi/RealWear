package com.example.realwear;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;


public class FileService extends Service
{
    private File imageStorageDir;
    private FirebaseDataSource firebaseDataSource;
    private final IBinder binder = new LocalBinder();

    public FileService()
    {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        imageStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    public File createFile(String subPath, String fileName)
    {
        return createFile(subPath + fileName);
    }

    public File createFile(String destination)
    {
        File file = new File(imageStorageDir, destination);
        try
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    public void saveFileToDatabase(File toSave, String destination, FileServiceCallback<Result> callback)
    {
        firebaseDataSource.uploadFile(toSave, destination, new FirebaseDataSource.DataSourceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }


    public void setFirebaseDataSource(FirebaseDataSource fds)
    {
        this.firebaseDataSource = fds;
    }

    public class LocalBinder extends Binder
    {
        FileService getService()
        {
            return FileService.this;
        }
    }

    public interface FileServiceCallback<T>
    {
        void onComplete(T result);
    }
}