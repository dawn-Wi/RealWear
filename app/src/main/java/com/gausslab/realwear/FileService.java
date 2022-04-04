package com.gausslab.realwear;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FileService extends Service
{
    private final IBinder binder = new LocalBinder();
    private File imageStorageDir;
    private FirebaseDataSource firebaseDataSource;

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

    public void uploadFileToDatabase(File toSave, String destination, FileServiceCallback<Result> callback)
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

    public void saveDrawableToDisk(String filePath, String fileName, Drawable toSave, FileServiceCallback<Result> callback)
    {
        saveDrawableToDisk(filePath + fileName, toSave, callback);
    }

    public void saveDrawableToDisk(String destination, Drawable toSave, FileServiceCallback<Result> callback)
    {
        Bitmap bitmap = ((BitmapDrawable) toSave).getBitmap();
        saveBitmapToDisk(destination, bitmap, callback);
    }

    public void saveBitmapToMediaStore(String displayName, Bitmap toSave, FileServiceCallback<Result> callback)
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
        values.put(MediaStore.Images.Media.TITLE, displayName + " image");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        try
        {
            ContentResolver cr = getContentResolver();
            Uri uri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            OutputStream outputStream = cr.openOutputStream(uri);
            toSave.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            callback.onComplete(new Result.Success<String>("Successfully Added Image"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            callback.onComplete(new Result.Error(e));
        }
    }

    public void saveBitmapToDisk(String destination, Bitmap toSave, FileServiceCallback<Result> callback)
    {
        File localFile = createFile(destination);
        try
        {
            FileOutputStream outStream = new FileOutputStream(localFile);
            toSave.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            callback.onComplete(new Result.Success<File>(localFile));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            callback.onComplete(new Result.Error(e));
        }
    }

    public void setFirebaseDataSource(FirebaseDataSource fds)
    {
        this.firebaseDataSource = fds;
    }

    public interface FileServiceCallback<T>
    {
        void onComplete(T result);
    }

    public class LocalBinder extends Binder
    {
        FileService getService()
        {
            return FileService.this;
        }
    }
}