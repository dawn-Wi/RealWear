package com.gausslab.realwear.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gausslab.realwear.model.FragmentType;
import com.gausslab.realwear.model.Task;
import com.gausslab.realwear.model.ProgressStatus;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.repository.TaskRepository;

public class MyTaskDetailsViewModel extends ViewModel
{
    private final TaskRepository taskRepository = TaskRepository.getInstance();
    private final MutableLiveData<ProgressStatus> taskProgress = new MutableLiveData<>();
    private final MutableLiveData<ProgressStatus> stepProgress = new MutableLiveData<>();
    private final MutableLiveData<FragmentType> fragmentType = new MutableLiveData<>(FragmentType.EMPTY);

    private Task currTask;
    private TaskStep currStep;

    public Task getCurrTask()
    {
        return currTask;
    }

    public void setCurrTask(int taskId)
    {
        setCurrTask(taskRepository.getTaskFromId(taskId));
    }

    public void setCurrTask(Task currTask)
    {
        this.currTask = currTask;
        taskProgress.setValue(currTask.getProgressStatus());
    }

    public TaskStep getCurrStep()
    {
        return currStep;
    }

    public void setCurrStep(int stepNumber)
    {
        setCurrStep(currTask.getStep(stepNumber));
    }

    public void setCurrStep(TaskStep currStep)
    {
        this.currStep = currStep;
        stepProgress.setValue(currStep.getProgressStatus());
    }

    public LiveData<ProgressStatus> getTaskProgress()
    {
        return taskProgress;
    }

    public void setTaskProgress(ProgressStatus status)
    {
        taskProgress.setValue(status);
    }

    public LiveData<ProgressStatus> getStepProgress() { return stepProgress; }

    public void setStepProgress(ProgressStatus status)
    {
        stepProgress.setValue(status);
    }

    public LiveData<FragmentType> getFragmentType() { return fragmentType; }

    public void setFragmentType(FragmentType ft)
    {
        fragmentType.setValue(ft);
    }

}
