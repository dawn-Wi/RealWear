package com.gausslab.realwear.repository;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.FileService;
import com.gausslab.realwear.FirebaseDataSource;
import com.gausslab.realwear.model.AssignmentStatus;
import com.gausslab.realwear.model.MyTask;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.model.TaskStep;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository extends Repository {
    private static volatile TaskRepository INSTANCE= new TaskRepository();
    private final Map<Integer, MyTask> taskMap = new HashMap<>();
    public static TaskRepository getInstance(){return INSTANCE;}


    private final MutableLiveData<Boolean> currUserTaskListUpdated = new MutableLiveData<>(false);
    private FirebaseDataSource firebaseDataSource;
    private List<MyTask> myTaskList;
    private List<MyTask> currUserTaskList;

    public void loadMyTaskList(final String id, final TaskRepositoryCallback<Result> callback){
        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_once("tasks", "assignedUserId", id, new FirebaseDataSource.DataSourceCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                if(result instanceof Result.Success){
                    List<DocumentSnapshot> documents = ((Result.Success<List<DocumentSnapshot>>)result).getData();
                    getTasksFromDocumentSnapshots(documents, new RepositoryListenerCallback<Result>() {
                        @Override
                        public void onUpdate(Result result) {
                            myTaskList = ((Result.Success<List<MyTask>>)result).getData();
                            callback.onComplete(result);
                        }
                    });
                }
            }
        });
    }

    private void getTasksFromDocumentSnapshots(List<DocumentSnapshot> documents, RepositoryListenerCallback<Result> callback)
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("DEBUG", "Executor run getTasksFromDocumentSnapshots " + executor.toString());
                List<MyTask> toReturn = new ArrayList<>();
                for(DocumentSnapshot doc : documents)
                {
                    MyTask taskToAdd = doc.toObject(MyTask.class);
                    List<TaskStep> stepList = taskToAdd.getSteps();
                    for(TaskStep currStep : stepList)
                    {
                        if(currStep.getHasBaseImage())
                        {
                            //Base Image
                            fileService.getTaskStepImageDrawable(taskToAdd.getTaskId(), currStep.getStepNumber() + "_base", new FileService.FileServiceCallback<Result<Drawable>>()
                            {
                                @Override
                                public void onComplete(Result result)
                                {
                                    if(result instanceof Result.Success)
                                    {
                                        Drawable myDrawable = ((Result.Success<Drawable>) result).getData();
                                        currStep.getPinnedImage().setBaseImageDrawable(myDrawable);
                                    }
                                    else
                                        currStep.getPinnedImage().setBaseImageDrawable(fileService.getTempImageDrawable());
                                }
                            });
                        }
                        if(currStep.getHasPinnedImage())
                        {
                            //Pinned Image
                            fileService.getTaskStepImageDrawable(taskToAdd.getTaskId(), currStep.getStepNumber() + "_pinned", new FileService.FileServiceCallback<Result<Drawable>>()
                            {
                                @Override
                                public void onComplete(Result result)
                                {
                                    if(result instanceof Result.Success)
                                    {
                                        Drawable myDrawable = ((Result.Success<Drawable>) result).getData();
                                        currStep.getPinnedImage().setPinnedImageDrawable(myDrawable);
                                    }
                                    else
                                    {
                                    }
                                }
                            });
                        }
                    }
                    taskMap.put(Integer.parseInt(taskToAdd.getTaskId()), taskToAdd);
                    toReturn.add(taskToAdd);
                }
                callback.onUpdate(new Result.Success<List<MyTask>>(toReturn));
            }
        });

    }

    public List<MyTask> getCurrUserTaskList(String userId)
    {
        if(currUserTaskList == null)
        {
            loadCurrUserTaskList(userId);
            currUserTaskList = new ArrayList();
        }
        return currUserTaskList;
    }

    private void loadCurrUserTaskList(String currUserId)
    {
        getTwoConditionalTaskList("assignedUserId", currUserId, "assignmentStatus", AssignmentStatus.ASSIGNED.name(), new RepositoryListenerCallback<Result>()
        {
            @Override
            public void onUpdate(Result result)
            {
                if(result instanceof Result.Success)
                {
                    currUserTaskList = ((Result.Success<List<MyTask>>) result).getData();
                    currUserTaskListUpdated.postValue(true);
                }
            }
        });
    }

    private void getTwoConditionalTaskList(String equalParameter1, String equalParameter2, String equalParameter3, String equalParameter4, RepositoryListenerCallback<Result> callback)
    {
        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_whereEqualTo("tasks", equalParameter1, equalParameter2, equalParameter3, equalParameter4, new FirebaseDataSource.DataSourceListenerCallback<Result>()
        {
            @Override
            public void onUpdate(Result result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> documents = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    getTasksFromDocumentSnapshots(documents, callback);
                }
                else
                {
                    callback.onUpdate(result);
                }
            }
        });
    }

    public void updateTask(MyTask toUpdate, RepositoryCallback<Result> callback)
    {
        firebaseDataSource.submitDataToCollection_specifiedDocumentName("tasks", "task_" + toUpdate.getTaskId(), toUpdate, new FirebaseDataSource.DataSourceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    public MyTask getTaskFromId(int taskId)
    {
        return taskMap.get(taskId);
    }


    public LiveData<Boolean> isCurrUserTaskListUpdated() { return currUserTaskListUpdated; }

    public void setDateSource(FirebaseDataSource ds){this.firebaseDataSource = ds;}


    public interface TaskRepositoryCallback<T>{
        void onComplete(Result result);
    }
}
