package com.gausslab.realwear;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyTasksViewModel extends ViewModel {
    private TaskRepository taskRepository = TaskRepository.getInstance();
    private MutableLiveData<Boolean> listLoaded = new MutableLiveData<>(false);

    private List<MyTask> myTaskList;

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

    public List<MyTask> getMyTaskList(){return myTaskList;}

    public LiveData<Boolean> isListLoaded(){return listLoaded;}

    public void setListLoaded(Boolean value){listLoaded.setValue(value);}
}
