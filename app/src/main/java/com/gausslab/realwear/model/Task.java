package com.gausslab.realwear.model;

import static com.gausslab.realwear.App.equalHelper;

import com.gausslab.realwear.util.component.ViewComponent;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task
{
    private String taskId;
    private String creatorId;
    private String creatorUserDisplayName;
    private String approverId;
    private String assignedUserId;
    private String assignedUserDisplayName;
    private String title;
    private String description;
    private ProgressStatus progressStatus;
    private AssignmentStatus assignmentStatus;
    private AssignmentStatus previousAssignmentStatus;
    private List<TaskStep> steps;
    private Map<String, Timestamp> times;
    private int currStep;
    private String associatedDeviceId;
    private String associatedDeviceName;
    private String rejectedMessage;
    private List<ViewComponent> attachedViewComponents;

    public Task()
    {
        steps = new ArrayList<TaskStep>();
        times = new HashMap<String, Timestamp>();
        progressStatus = ProgressStatus.NOT_STARTED;
        assignmentStatus = AssignmentStatus.UNASSIGNED;
        attachedViewComponents = new ArrayList<>();
        currStep = -1;
    }

    @Exclude
    public void assignToUser(User assignedUser)
    {
        addTime(AssignmentStatus.ASSIGNED.name(), Timestamp.now());
        assignedUserId = assignedUser.getUserId();
        assignedUserDisplayName = assignedUser.getDisplayName();
        previousAssignmentStatus = assignmentStatus;
        assignmentStatus = AssignmentStatus.ASSIGNED;
        progressStatus = ProgressStatus.NOT_STARTED;
    }

    public void makeOpen()
    {
        addTime(AssignmentStatus.OPEN.name(), Timestamp.now());
        previousAssignmentStatus = assignmentStatus;
        assignmentStatus = AssignmentStatus.OPEN;
        progressStatus = ProgressStatus.NOT_STARTED;
        assignedUserDisplayName = null;
        assignedUserId = null;
    }

    public void clearAssignmentTimes()
    {
        Timestamp createdTime = times.get("CREATED");
        times.clear();
        times.put("CREATED", createdTime);
    }

    public void addTime(String key, Timestamp time)
    {
        times.put(key, time);
    }

    public void addStep(TaskStep toAdd)
    {
        steps.add(toAdd);
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
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

    public List<TaskStep> getSteps()
    {
        return steps;
    }

    public void setSteps(List<TaskStep> steps)
    {
        this.steps = steps;
    }

    public ProgressStatus getProgressStatus()
    {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus)
    {
        this.progressStatus = progressStatus;
    }

    public String getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getApproverId()
    {
        return approverId;
    }

    public void setApproverId(String approverId)
    {
        this.approverId = approverId;
    }

    public String getAssignedUserId()
    {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId)
    {
        this.assignedUserId = assignedUserId;
    }

    public int getCurrStep()
    {
        return currStep;
    }

    public void setCurrStep(int currStep)
    {
        this.currStep = currStep;
    }

    public Map<String, Timestamp> getTimes()
    {
        return times;
    }

    public void setTimes(Map<String, Timestamp> times)
    {
        this.times = times;
    }

    public String getAssignedUserDisplayName()
    {
        return assignedUserDisplayName;
    }

    public void setAssignedUserDisplayName(String assignedUserDisplayName)
    {
        this.assignedUserDisplayName = assignedUserDisplayName;
    }

    public String getAssociatedDeviceId()
    {
        return associatedDeviceId;
    }

    public void setAssociatedDeviceId(String associatedDeviceId)
    {
        this.associatedDeviceId = associatedDeviceId;
    }

    public String getAssociatedDeviceName()
    {
        return associatedDeviceName;
    }

    public void setAssociatedDeviceName(String associatedDeviceName)
    {
        this.associatedDeviceName = associatedDeviceName;
    }

    public AssignmentStatus getAssignmentStatus()
    {
        return assignmentStatus;
    }

    public void setAssignmentStatus(AssignmentStatus assignmentStatus)
    {
        this.assignmentStatus = assignmentStatus;
    }

    public String getRejectedMessage()
    {
        return rejectedMessage;
    }

    public void setRejectedMessage(String rejectedMessage)
    {
        this.rejectedMessage = rejectedMessage;
    }

    public AssignmentStatus getPreviousAssignmentStatus()
    {
        return previousAssignmentStatus;
    }

    public void setPreviousAssignmentStatus(AssignmentStatus previousAssignmentStatus)
    {
        this.previousAssignmentStatus = previousAssignmentStatus;
    }

    public TaskStep getStep(int stepNumber)
    {
        return steps.get(stepNumber - 1);
    }

    public void setAllStepsProgress(ProgressStatus progressStatus)
    {
        for(TaskStep step : steps)
        {
            step.setProgressStatus(progressStatus);
            //TODO: STEP TIMES
        }
    }

    public String getCreatorUserDisplayName()
    {
        return creatorUserDisplayName;
    }

    public void setCreatorUserDisplayName(String creatorUserDisplayName)
    {
        this.creatorUserDisplayName = creatorUserDisplayName;
    }

    public List<ViewComponent> getAttachedViewComponents()
    {
        return attachedViewComponents;
    }

    public void setAttachedViewComponents(List<ViewComponent> attachedViewComponents)
    {
        this.attachedViewComponents = attachedViewComponents;
    }

    public void appendAttachedViewComponents(List<ViewComponent> attachedViewComponents)
    {
        if(this.attachedViewComponents == null)
            this.attachedViewComponents = attachedViewComponents;
        else
            this.attachedViewComponents.addAll(attachedViewComponents);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Task))
            return false;
        Task compareTask = (Task) obj;
        if(!equalHelper(taskId, compareTask.getTaskId()))
            return false;
        if(!equalHelper(creatorId, compareTask.getCreatorId()))
            return false;
        if(!equalHelper(creatorUserDisplayName, compareTask.getCreatorUserDisplayName()))
            return false;
        if(!equalHelper(approverId, compareTask.getApproverId()))
            return false;
        if(!equalHelper(assignedUserId, compareTask.getAssignedUserId()))
            return false;
        if(!equalHelper(assignedUserDisplayName, compareTask.getAssignedUserDisplayName()))
            return false;
        if(!equalHelper(title, compareTask.getTitle()))
            return false;
        if(!equalHelper(description, compareTask.getDescription()))
            return false;
        if(!equalHelper(progressStatus, compareTask.getProgressStatus()))
            return false;
        if(!equalHelper(steps, compareTask.getSteps()))
            return false;
        if(!equalHelper(times, compareTask.getTimes()))
            return false;
        if(!equalHelper(assignmentStatus, compareTask.getAssignmentStatus()))
            return false;
        if(currStep != compareTask.getCurrStep())
            return false;
        if(!equalHelper(associatedDeviceId, compareTask.getAssociatedDeviceId()))
            return false;
        if(!equalHelper(assignedUserDisplayName, compareTask.getAssociatedDeviceName()))
            return false;
        return equalHelper(attachedViewComponents, compareTask.getAttachedViewComponents());
    }
}
