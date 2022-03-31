package com.example.realwear;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FirebaseDataSource {
    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public void uploadFile(String localPath, String destinationDirectory, String fileName, DataSourceCallback<Result> callback)
    {
        uploadFile(localPath, destinationDirectory + "/" + fileName, callback);
    }

    public void uploadFile(String localPath, String destination, DataSourceCallback<Result> callback)
    {
        uploadFile(new File(localPath), destination, callback);
    }

    public void uploadFile(File toUpload, String destination, DataSourceCallback<Result> callback)
    {
        Uri localFile = Uri.fromFile(toUpload);
        StorageReference storageReference = firebaseStorage.getReference().child("test/test.jpg");
        UploadTask uploadTask = storageReference.putFile(localFile);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            Uri result = task.getResult();
                            callback.onComplete(new Result.Success<Uri>(result));
                        }
                        else
                        {
                            callback.onComplete(new Result.Error(task.getException()));

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                callback.onComplete(new Result.Error(e));
                Log.d("DEBUG", "DataSource: storeImage() failed!");
            }
        });
    }

    public interface DataSourceCallback<T>
    {
        void onComplete(T result);
    }

}
