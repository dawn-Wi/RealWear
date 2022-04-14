package com.gausslab.realwear;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gausslab.realwear.model.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class FirebaseDataSource {
    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

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
        StorageReference storageReference = firebaseStorage.getReference().child(destination);
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

    public void submitDataToCollection_specifiedDocumentName(String collectionName, String documentName, Object toSubmit, DataSourceCallback<Result> callback)
    {
        Log.d("DEBUG:DataSource", "submitDataToCollection_specifiedDocumentName: " + collectionName + " / " + documentName + " / " + toSubmit.toString());
        firebaseFirestore.collection(collectionName).document(documentName).set(toSubmit).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    callback.onComplete(new Result.Success<>("Success"));
                }
                else
                {
                    callback.onComplete(new Result.Error(task.getException()));
                }
            }
        });
    }

    public void getNewKey(KeyType type, DataSourceCallback<Result<String>> callback)
    {
        Log.d("DEBUG:DataSource", "getNewKey: " + type.name());
        DocumentReference docRef = null;
        if(type == KeyType.USER)
            docRef = firebaseFirestore.collection("idKeys").document("user");
        if(type == KeyType.REPORT)
            docRef = firebaseFirestore.collection("idKeys").document("report");
        if(type == KeyType.TASK)
            docRef = firebaseFirestore.collection("idKeys").document("task");
        if(type == KeyType.DEVICE)
            docRef = firebaseFirestore.collection("idKeys").document("device");

        if(docRef != null)
        {
            DocumentReference finalDocRef = docRef;
            firebaseFirestore.runTransaction(new Transaction.Function<String>()
            {
                @Nullable
                @Override
                public String apply(@NonNull Transaction transaction) throws FirebaseFirestoreException
                {
                    int currKey = transaction.get(finalDocRef).getDouble("key").intValue();
                    transaction.update(finalDocRef, "key", currKey + 1);
                    return "" + currKey;
                }
            }).addOnSuccessListener(new OnSuccessListener<String>()
            {
                @Override
                public void onSuccess(String key)
                {
                    callback.onComplete(new Result.Success<String>(key));
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    callback.onComplete(new Result.Error(e));
                }
            });
        }
    }

    public void getDocumentsFromCollection_whereEqualTo_once(String collectionName, String equalParameter1, String equalParameter2, DataSourceCallback<Result> callback)
    {
        Log.d("DEBUG:DataSource", "getDocumentsFromCollection_whereEqualTo_once: startTime = " + Timestamp.now().getSeconds());
        firebaseFirestore.collection(collectionName)
                .whereEqualTo(equalParameter1, equalParameter2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            List<DocumentSnapshot> documentsList = task.getResult().getDocuments();
                            Log.d("DEBUG:DataSource", "getDocumentsFromCollection_whereEqualTo_once: finishTime = " + Timestamp.now().getSeconds());
                            callback.onComplete(new Result.Success<List<DocumentSnapshot>>(documentsList));
                        }
                        else
                        {
                            callback.onComplete(new Result.Error(task.getException()));
                        }
                    }
                });
    }

    public void downloadFile(String downloadPath, File localFile, DataSourceCallback<Result> callback)
    {
        Log.d("DEBUG:DataSource", "downloadFile: " + downloadPath);
        StorageReference ref = firebaseStorage.getReference().child(downloadPath);
        ref.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    callback.onComplete(new Result.Success<File>(localFile));
                }
                else
                {
                    callback.onComplete(new Result.Error(task.getException()));
                }
            }
        });
    }

    public enum KeyType
    {
        USER,
        TASK,
        REPORT,
        DEVICE
    }

    public interface DataSourceCallback<T>
    {
        void onComplete(T result);
    }

}
