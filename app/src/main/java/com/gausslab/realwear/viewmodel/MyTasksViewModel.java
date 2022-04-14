package com.gausslab.realwear.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gausslab.realwear.App;
import com.gausslab.realwear.model.MyTask;
import com.gausslab.realwear.model.ProgressStatus;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.repository.Repository;
import com.gausslab.realwear.repository.TaskRepository;

import java.util.List;

public class MyTasksViewModel extends ViewModel {
    private TaskRepository taskRepository = TaskRepository.getInstance();
    private MutableLiveData<Boolean> listLoaded = new MutableLiveData<>(false);

    private List<MyTask> myTaskList;

    public void startTask(MyTask mytask)
    {
        mytask.setProgressStatus(ProgressStatus.STARTED);
        updateTask(mytask);
    }

    public void loadMyTaskList(String id){
        taskRepository.loadMyTaskList(id, result->{
            if(result instanceof Result.Success){
                myTaskList = ((Result.Success<List<MyTask>>)result).getData();
                listLoaded.postValue(true);
            }
            else{
                listLoaded.postValue(false);
            }
        });
    }

    public void completeTask(MyTask mytask)
    {
        List<TaskStep> stepList = mytask.getSteps();
        for(TaskStep step : stepList)
        {
            step.setProgressStatus(ProgressStatus.PENDING_REVIEW);
        }
        mytask.setProgressStatus(ProgressStatus.PENDING_REVIEW);
        updateTask(mytask);
    }

    private void updateTask(MyTask mytask)
    {
        App.setIsWorking(true);
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

    public List<MyTask> getMyTaskList(){return myTaskList;}

    public LiveData<Boolean> isListLoaded(){return listLoaded;}

    public void setListLoaded(Boolean value){listLoaded.setValue(value);}
}
