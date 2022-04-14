package com.gausslab.realwear.viewmodel;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gausslab.realwear.App;
import com.gausslab.realwear.FileService;
import com.gausslab.realwear.FirebaseDataSource;
import com.gausslab.realwear.model.ImagePinHolder;
import com.gausslab.realwear.model.Report;
import com.gausslab.realwear.model.Result;
import com.google.firebase.Timestamp;

import java.io.File;

public class MainViewModel extends ViewModel
{
    MutableLiveData<Boolean> submitSuccessful = new MutableLiveData<>(false);
    FirebaseDataSource firebaseDataSource;

    public void submitImage(File imageFile)
    {
        Report toSubmit = createNewReport(imageFile);
        firebaseDataSource.getNewKey(FirebaseDataSource.KeyType.REPORT, result ->
        {
            if(result instanceof Result.Success)
            {
                String newKey = ((Result.Success<String>) result).getData();
                toSubmit.setReportId(newKey);
                firebaseDataSource.submitDataToCollection_specifiedDocumentName("reports", "report_" + newKey, toSubmit, result2 ->
                {
                    if(result2 instanceof Result.Success)
                    {
                        if(toSubmit.getHasImage())
                        {
                            saveAndUploadImagePinHolderDrawables(newKey, toSubmit.getImagePinHolder(), new Callback<Result>()
                            {
                                @Override
                                public void onComplete(Result result)
                                {
                                    submitSuccessful.setValue(true);
                                }
                            });
                        }
                    }
                    else
                    {
                        submitSuccessful.setValue(false);
                    }
                });
            }
        });
    }

    private void saveAndUploadImagePinHolderDrawables(String reportId, ImagePinHolder imagePinHolder, Callback<Result> callback)
    {
        if(imagePinHolder.getBaseImageDrawable() != null)
        {
            saveReportDrawableToDisk(reportId + "_base", imagePinHolder.getBaseImageDrawable(), new Callback<Result>()
            {
                @Override
                public void onComplete(Result result)
                {
                    if(result instanceof Result.Success)
                    {
                        File f = ((Result.Success<File>) result).getData();

                        uploadReportImage(App.getReportImagePath(reportId + "_base"), f, new Callback<Result>()
                        {
                            @Override
                            public void onComplete(Result result)
                            {
                                Log.d("DEBUG", reportId + " base image uploaded");
                                callback.onComplete(result);
                            }
                        });
                    }
                    else
                    {
                        callback.onComplete(result);
                    }
                }
            });
        }
    }

    private void uploadReportImage(String destination, File file, Callback<Result> callback)
    {
        FileService fileService = App.getFileService();
        fileService.uploadFileToDatabase(file, destination, new FileService.FileServiceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    private void saveReportDrawableToDisk(String reportId, Drawable drawable, Callback<Result> callback)
    {
        FileService fileService = App.getFileService();
        fileService.saveDrawableToDisk(App.getReportImagePath(reportId), drawable, new FileService.FileServiceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    private Report createNewReport(File image)
    {
        Report toSubmit = new Report("0", "RealWear");
        toSubmit.setDetails("RealWear");
        toSubmit.setSubmitTime(Timestamp.now());
        toSubmit.setImagePinHolder(new ImagePinHolder(Drawable.createFromPath(image.getAbsolutePath())));
        toSubmit.setHasImage(true);
        return toSubmit;
    }

    public LiveData<Boolean> isSubmitSuccessful()
    {
        return submitSuccessful;
    }

    public void setFirebaseDataSource(FirebaseDataSource fds)
    {
        firebaseDataSource = fds;
    }

    public interface Callback<T>
    {
        void onComplete(T result);
    }
}
