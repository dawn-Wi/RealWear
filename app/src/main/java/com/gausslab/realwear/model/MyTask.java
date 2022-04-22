package com.gausslab.realwear.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTask {
    private String description;
    private String taskId;
    private String title;
    private String creatorId;
    private String creatorName;
    private ProgressStatus progressStatus;
    private Map<String, Timestamp> times;
    private List<TaskStep> steps;
    private String associatedDeviceId;
    private String rejectedMessage;
    private String associatedDeviceName;

    public MyTask()
    {
        steps = new ArrayList<TaskStep>();
        times = new HashMap<String, Timestamp>();
        progressStatus = ProgressStatus.NOT_STARTED;
    }
    public void addTime(String key, Timestamp time)
    {
        times.put(key, time);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ProgressStatus getProgressStatus()
    {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus)
    {
        this.progressStatus = progressStatus;
        addTime(progressStatus.name(), Timestamp.now());
    }

    public String getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorName()
    {
        return creatorName;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }


    public List<TaskStep> getSteps()
    {
        return steps;
    }

    public TaskStep getStep(int stepNumber)
    {
        return steps.get(stepNumber - 1);
    }

    public String getTaskId()
    {
        return taskId;
    }

    public String getAssociatedDeviceId()
    {
        return associatedDeviceId;
    }

    public String getRejectedMessage()
    {
        return rejectedMessage;
    }
    public void setRejectedMessage(String rejectedMessage)
    {
        this.rejectedMessage = rejectedMessage;
    }


    public Map<String, Timestamp> getTimes()
    {
        return times;
    }
    public void setTimes(Map<String, Timestamp> times)
    {
        this.times = times;
    }

    public String getAssociatedDeviceName()
    {
        return associatedDeviceName;
    }

    public void setAssociatedDeviceName(String associatedDeviceName)
    {
        this.associatedDeviceName = associatedDeviceName;
    }

}
