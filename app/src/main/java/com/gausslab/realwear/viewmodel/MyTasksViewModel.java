package com.gausslab.realwear.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gausslab.realwear.App;
import com.gausslab.realwear.model.ProgressStatus;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.model.Task;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.repository.Repository;
import com.gausslab.realwear.repository.TaskRepository;
import com.gausslab.realwear.repository.UserRepository;
import com.google.firebase.Timestamp;

import java.util.List;

public class MyTasksViewModel extends ViewModel
{
    private final UserRepository userRepository = UserRepository.getInstance();
    private final TaskRepository taskRepository = TaskRepository.getInstance();
    private final LiveData<Boolean> currUserTasksUpdated = taskRepository.isCurrUserTaskListUpdated();

    public void startTask(Task mytask)
    {
        mytask.setProgressStatus(ProgressStatus.STARTED);
        updateTask(mytask);
    }

    public void loadMyTaskList(String id)
    {
        taskRepository.loadMyTaskList(id);
    }

    public void completeTask(Task mytask)
    {
        List<TaskStep> stepList = mytask.getSteps();
        for(TaskStep step : stepList)
        {
            step.setProgressStatus(ProgressStatus.PENDING_REVIEW);
        }
        mytask.setProgressStatus(ProgressStatus.PENDING_REVIEW);
        updateTask(mytask);
    }

    private void updateTask(Task mytask)
    {
        App.setIsWorking(true);
        mytask.addTime(mytask.getProgressStatus().toString(), Timestamp.now());
        taskRepository.updateTask(mytask, new Repository.RepositoryCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                App.setIsWorking(false);
                Log.d("DEBUG", "Updated!");
            }
        });
    }

    public List<Task> getMyTaskList() {return taskRepository.getCurrUserTaskList("21");}

    public LiveData<Boolean> isListLoaded() {return taskRepository.isCurrUserTaskListUpdated();}

    public List<Task> getCurrUserTasks() { return taskRepository.getCurrUserTaskList("21"); }

    public LiveData<Boolean> isCurrUserTasksUpdated() { return currUserTasksUpdated; }
}
