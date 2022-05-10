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
import com.gausslab.realwear.model.ReportStatus;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.repository.ReportRepository;
import com.gausslab.realwear.repository.Repository;
import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.component.ViewComponent;
import com.google.firebase.Timestamp;

import java.io.File;

public class MainViewModel extends ViewModel
{
    ReportRepository reportRepository = ReportRepository.getInstance();
    MutableLiveData<Boolean> submitSuccessful = new MutableLiveData<>(false);
    FirebaseDataSource firebaseDataSource;

    public void submitImage(File imageFile)
    {
        Report toSubmit = createNewReport(imageFile);
        reportRepository.submitReport(toSubmit, new Repository.RepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result)
            {
                Log.d("DEBUG: MainViewModel", "report submitted");
            }
        });
    }

    private Report createNewReport(File image)
    {
        Report toSubmit = new Report("21", "RealWear");
        toSubmit.setReportStatus(ReportStatus.SUBMITTED);
        toSubmit.setDetails("RealWear 특이사항");
        toSubmit.addTime(ReportStatus.SUBMITTED, Timestamp.now());
        Drawable d = Drawable.createFromPath(image.getAbsolutePath());
        toSubmit.addViewComponent(new ViewComponent(ViewComponent.VIEW_TYPE_IMAGE, new ComponentData("첨부 이미지",d)));
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
}
