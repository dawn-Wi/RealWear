package com.gausslab.realwear;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTask {
    private String taskId;
    private String title;
    private String creatorId;
    private ProgressStatus progressStatus;
    private Map<String, Timestamp> times;
    private List<TaskStep> steps;

    public MyTask()
    {
        steps = new ArrayList<TaskStep>();
        times = new HashMap<String, Timestamp>();
        progressStatus = ProgressStatus.NOT_STARTED;
//        assignmentStatus = AssignmentStatus.UNASSIGNED;
//        currStep = -1;
    }

//    public MyTask(String title , String creatorId, String progressStatus) {
//        this.title = title;
//        this.creatorId = creatorId;
//        times = new HashMap<String, Timestamp>();
//        this.progressStatus = ProgressStatus.NOT_STARTED;
//    }
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

}
