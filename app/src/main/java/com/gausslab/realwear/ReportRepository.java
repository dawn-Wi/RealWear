package com.gausslab.realwear;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.model.ImagePinHolder;
import com.gausslab.realwear.model.Report;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles local caching of data related to Reports
 * Handles threading of calls that require network interaction (Excluding Firebase methods as Firebase creates its own threads)
 */
public class ReportRepository extends Repository
{
    //region Singleton pattern
    private static final ReportRepository INSTANCE = new ReportRepository();
    private final MutableLiveData<Boolean> reportListUpdated = new MutableLiveData<>(false);
    //endregion
    private final Map<String, Drawable> reportDrawableMap = new HashMap<>();
    private final Map<String, Report> reportMap = new HashMap<>();
    private List<Report> reportList;

    public static ReportRepository getInstance()
    {
        return INSTANCE;
    }

    public void submitReport(Report toSubmit, RepositoryCallback<Result> callback)
    {
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
                        callback.onComplete(new Result.Success<String>(newKey));
                        List<ViewComponent> attachedViewComponents = toSubmit.getAttachedViewComponents();
                        for(int i = 0; i < attachedViewComponents.size(); i++)
                        {
                            ViewComponent component = attachedViewComponents.get(i);
                            if(component.getViewType() == ViewComponent.VIEW_TYPE_IMAGE)
                            {
                                saveAndUploadImagePinHolderDrawables(newKey, String.valueOf(i), component.getData().getPinHolder(), new RepositoryCallback<Result>()
                                {
                                    @Override
                                    public void onComplete(Result result)
                                    {
                                    }
                                });
                            }
                        }
                    }
                    else
                    {
                        callback.onComplete(result2);
                    }
                });
            }
            else
                callback.onComplete(result);
        });
    }

    public List<Report> getReportList()
    {
        if(reportList == null)
        {
            loadReportList();
            reportList = new ArrayList<>();
        }
        return reportList;
    }

    public void getReportDrawable(String reportId, String attachmentNumber, DrawableType type, RepositoryCallback<Result<Drawable>> callback)
    {
        String combined = "";
        if(type == DrawableType.BASE)
            combined = reportId + "_" + attachmentNumber + "_" + "base";
        else
            combined = reportId + "_" + attachmentNumber + "_" + "pinned";
        if(reportDrawableMap.containsKey(combined))
            callback.onComplete(new Result.Success<Drawable>(reportDrawableMap.get(reportId)));
        else
            loadReportDrawable(reportId, callback);
    }

    public LiveData<Boolean> isReportListUpdated()
    {
        return reportListUpdated;
    }

    private void loadReportDrawable(String reportId, RepositoryCallback<Result<Drawable>> callback)
    {
        callback.onComplete(new Result.Loading(reportId + " drawable loading"));
        fileService.getReportImageDrawable(reportId, new FileService.FileServiceCallback<Result<Drawable>>()
        {
            @Override
            public void onComplete(Result<Drawable> result)
            {
                if(result instanceof Result.Success)
                {
                    reportDrawableMap.put(reportId, ((Result.Success<Drawable>) result).getData());
                }
                callback.onComplete(result);
            }
        });
    }

    public void loadReportDrawables(String reportId, RepositoryCallback<Result<String>> callback)
    {
        Report report = reportMap.get(reportId);
        if(report == null)
            return;
        List<ViewComponent> viewComponents = report.getAttachedViewComponents();
        final int[] loadingCount = {0};
        for(int i = 0; i < viewComponents.size(); i++)
        {
            ViewComponent component = viewComponents.get(i);
            if(component.getViewType() == ViewComponent.VIEW_TYPE_IMAGE)
            {
                loadingCount[0] += 2;
                String combined = reportId + "_" + i + "_base";
                loadReportDrawable(combined, new RepositoryCallback<Result<Drawable>>()
                {
                    @Override
                    public void onComplete(Result<Drawable> result)
                    {
                        if(result instanceof Result.Success)
                        {
                            Drawable d = ((Result.Success<Drawable>) result).getData();
                            component.getData().getPinHolder().setBaseImageDrawable(d);
                        }
                        loadingCount[0]--;
                        if(loadingCount[0] == 0)
                            callback.onComplete(new Result.Success<String>("Finished Loading"));
                    }
                });
                combined = reportId + "_" + i + "_pinned";
                loadReportDrawable(combined, new RepositoryCallback<Result<Drawable>>()
                {
                    @Override
                    public void onComplete(Result<Drawable> result)
                    {
                        if(result instanceof Result.Success)
                        {
                            Drawable d = ((Result.Success<Drawable>) result).getData();
                            component.getData().getPinHolder().setPinnedImageDrawable(d);
                        }
                        loadingCount[0]--;
                        if(loadingCount[0] == 0)
                            callback.onComplete(new Result.Success<>("Finished Loading"));
                    }
                });
            }
        }
    }

    public void logout()
    {

    }

    private void loadReportList()
    {
        firebaseDataSource.getDocumentsFromCollection("reports", new FirebaseDataSource.DataSourceListenerCallback<Result>()
        {
            @Override
            public void onUpdate(Result result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> documents = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    List<Report> updatedList = new ArrayList<>();
                    for(DocumentSnapshot doc : documents)
                    {
                        Report currReport = doc.toObject(Report.class);
                        reportMap.put(currReport.getReportId(), currReport);
                        List<ViewComponent> attachedViewComponents = currReport.getAttachedViewComponents();
                        for(int i = 0; i < attachedViewComponents.size(); i++)
                        {
                            ViewComponent component = attachedViewComponents.get(i);
                            if(component.getViewType() == ViewComponent.VIEW_TYPE_IMAGE)
                            {
                                component.getData().getPinHolder().setBaseImageDrawable(fileService.getImageLoadingDrawable());
                            }
                        }
                        updatedList.add(currReport);
                    }
                    reportList = updatedList;
                    reportListUpdated.setValue(true);
                }
            }
        });
    }

    private void saveAndUploadImagePinHolderDrawables(String reportId, String attachmentNumber, ImagePinHolder imagePinHolder, RepositoryCallback<Result> callback)
    {
        String combined = reportId + "_" + attachmentNumber;
        reportDrawableMap.put(combined, imagePinHolder.getImageDrawable());
        if(imagePinHolder.getBaseImageDrawable() != null)
        {
            reportDrawableMap.put(combined + "_base", imagePinHolder.getBaseImageDrawable());
            saveReportDrawableToDisk(combined + "_base", imagePinHolder.getBaseImageDrawable(), new RepositoryCallback<Result>()
            {
                @Override
                public void onComplete(Result result)
                {
                    if(result instanceof Result.Success)
                    {
                        File f = ((Result.Success<File>) result).getData();
                        uploadReportImage(App.getReportImagePath(combined + "_base"), f, new RepositoryCallback<Result>()
                        {
                            @Override
                            public void onComplete(Result result)
                            {
                                Log.d("DEBUG", combined + " base image uploaded");
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
        if(imagePinHolder.getPinnedImageDrawable() != null)
        {
            reportDrawableMap.put(combined + "_pinned", imagePinHolder.getPinnedImageDrawable());
            saveReportDrawableToDisk(combined + "_pinned", imagePinHolder.getPinnedImageDrawable(), new RepositoryCallback<Result>()
            {
                @Override
                public void onComplete(Result result)
                {
                    if(result instanceof Result.Success)
                    {
                        File f = ((Result.Success<File>) result).getData();
                        uploadReportImage(App.getReportImagePath(combined + "_pinned"), f, new RepositoryCallback<Result>()
                        {
                            @Override
                            public void onComplete(Result result)
                            {
                                Log.d("DEBUG", combined + " pinned image uploaded");
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

    private void uploadReportImage(String destination, File file, RepositoryCallback<Result> callback)
    {
        fileService.uploadFileToDatabase(file, destination, new FileService.FileServiceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    private void saveReportDrawableToDisk(String reportId, Drawable drawable, RepositoryCallback<Result> callback)
    {
        fileService.saveDrawableToDisk(App.getReportImagePath(reportId), drawable, new FileService.FileServiceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    public enum DrawableType
    {
        BASE,
        PINNED
    }
}
