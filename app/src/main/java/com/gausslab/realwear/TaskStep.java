package com.gausslab.realwear;


import android.graphics.drawable.Drawable;

import com.google.firebase.Timestamp;

public class TaskStep
{
    private String stepNumber;
    private String textContent;
    private ProgressStatus progressStatus;
    private Timestamp startTime;
    private Timestamp finishTime;
    private ImagePinHolder pinnedImage;
    private String downloadUrl;
    private boolean hasBaseImage;
    private boolean hasPinnedImage;

    public TaskStep()
    {
        progressStatus = ProgressStatus.NOT_STARTED;
        startTime = null;
        finishTime = null;
        pinnedImage = new ImagePinHolder();
    }

    public TaskStep(String stepNumber)
    {
        this();
        this.stepNumber = stepNumber;
    }

    public TaskStep(String stepNumber, String content)
    {
        this();
        this.textContent = content;
        this.stepNumber = stepNumber;
    }

    public TaskStep(String stepNumber, String content, Drawable image)
    {
        this();
        this.textContent = content;
        this.pinnedImage.setBaseImageDrawable(image);
        this.stepNumber = stepNumber;
    }

//    @Override
//    public boolean equals(Object obj)
//    {
//        if(!(obj instanceof TaskStep))
//            return false;
//        TaskStep compareStep = (TaskStep) obj;
//        if(!equalHelper(stepNumber, compareStep.getStepNumber()))
//            return false;
//        if(!equalHelper(textContent, compareStep.getTextContent()))
//            return false;
//        if(!equalHelper(progressStatus, compareStep.getProgressStatus()))
//            return false;
//        if(!equalHelper(startTime, compareStep.getStartTime()))
//            return false;
//        if(!equalHelper(finishTime, compareStep.getFinishTime()))
//            return false;
//        if(!equalHelper(pinnedImage, compareStep.getPinnedImage()))
//            return false;
//        return equalHelper(downloadUrl, compareStep.getDownloadUrl());
//    }

    public String getTextContent()
    {
        return textContent;
    }

    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }

    public ProgressStatus getProgressStatus()
    {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus)
    {
        this.progressStatus = progressStatus;
    }

    public Timestamp getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Timestamp startTime)
    {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime)
    {
        this.finishTime = finishTime;
    }

    public String getStepNumber()
    {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber)
    {
        this.stepNumber = stepNumber;
    }

    public ImagePinHolder getPinnedImage()
    {
        return pinnedImage;
    }

    public void setPinnedImage(ImagePinHolder pinnedImage)
    {
        this.pinnedImage = pinnedImage;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public boolean getHasBaseImage()
    {
        return hasBaseImage;
    }

    public void setHasBaseImage(boolean hasBaseImage)
    {
        this.hasBaseImage = hasBaseImage;
    }

    public boolean getHasPinnedImage()
    {
        return hasPinnedImage;
    }

    public void setHasPinnedImage(boolean hasPinnedImage)
    {
        this.hasPinnedImage = hasPinnedImage;
    }
}