package com.gausslab.realwear.model;

import com.gausslab.realwear.App;
import com.gausslab.realwear.ViewComponent;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Report
{
    private String status;
    private String reportId;
    private String userId;
    private String userDisplayName;
    private Timestamp submitTime;
    private String details;
    private boolean hasImage = false;
    private boolean imagesLoaded = false;
    private ImagePinHolder imagePinHolder;
    private List<ViewComponent> attachedViewComponents;

    public Report()
    {
        imagesLoaded = false;
    }

    public Report(String userId, String userDisplayName)
    {
        this();
        this.userId = userId;
        this.userDisplayName = userDisplayName;
    }

    public Report(String userId, String userDisplayName, String details, Timestamp submitTime)
    {
        this();
        this.userId = userId;
        this.userDisplayName = userDisplayName;
        this.details = details;
        this.submitTime = submitTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserDisplayName()
    {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName)
    {
        this.userDisplayName = userDisplayName;
    }

    public Timestamp getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime)
    {
        this.submitTime = submitTime;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public boolean getHasImage()
    {
        return hasImage;
    }

    public void setHasImage(boolean hasImage)
    {
        this.hasImage = hasImage;
    }

    public ImagePinHolder getImagePinHolder() { return imagePinHolder; }

    public void setImagePinHolder(ImagePinHolder imagePinHolder) { this.imagePinHolder = imagePinHolder; }

    @Exclude
    public boolean isImagesLoaded() { return imagesLoaded; }

    @Exclude
    public void setImagesLoaded(boolean loaded) { imagesLoaded = loaded; }

    public List<ViewComponent> getAttachedViewComponents()
    {
        return attachedViewComponents;
    }

    public void setAttachedViewComponents(List<ViewComponent> attachedViewComponents)
    {
        this.attachedViewComponents = attachedViewComponents;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Report))
            return false;
        Report compareTask = (Report) obj;
        if(!App.equalHelper(reportId, compareTask.getReportId()))
            return false;
        if(!App.equalHelper(userId, compareTask.getUserId()))
            return false;
        if(!App.equalHelper(userDisplayName, compareTask.getUserDisplayName()))
            return false;
        if(!App.equalHelper(submitTime, compareTask.getSubmitTime()))
            return false;
        if(!App.equalHelper(details, compareTask.getDetails()))
            return false;
        return App.equalHelper(hasImage, compareTask.getHasImage());
    }
}
