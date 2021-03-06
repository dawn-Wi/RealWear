package com.gausslab.realwear.repository;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.FileService;
import com.gausslab.realwear.FirebaseDataSource;
import com.gausslab.realwear.model.AssignmentStatus;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.model.Task;
import com.gausslab.realwear.model.TaskStep;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository extends Repository
{
    private static volatile TaskRepository INSTANCE = new TaskRepository();
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final MutableLiveData<Boolean> currUserTaskListUpdated = new MutableLiveData<>(false);
    private FirebaseDataSource firebaseDataSource;
    private List<Task> currUserTaskList;

    public static TaskRepository getInstance() {return INSTANCE;}

    public void loadMyTaskList(final String id)
    {
        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_whereNotEqualTo("tasks", "assignedUserId", id, "assignmentStatus", AssignmentStatus.CLOSED.toString(), new FirebaseDataSource.DataSourceListenerCallback<Result<List<DocumentSnapshot>>>()
        {
            @Override
            public void onUpdate(Result<List<DocumentSnapshot>> result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> documents = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    getTasksFromDocumentSnapshots(documents, new RepositoryListenerCallback<Result>()
                    {
                        @Override
                        public void onUpdate(Result result)
                        {
                            currUserTaskList = ((Result.Success<List<Task>>) result).getData();
                            currUserTaskListUpdated.postValue(true);
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
                List<Task> toReturn = new ArrayList<>();
                for(DocumentSnapshot doc : documents)
                {
                    Task taskToAdd = doc.toObject(Task.class);
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
                callback.onUpdate(new Result.Success<List<Task>>(toReturn));
            }
        });

    }

//    public void loadTaskCreatorName(final String id, final TaskRepositoryCallback<Result> callback){
//        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_once("users", "userId", id, new FirebaseDataSource.DataSourceCallback<Result>() {
//            @Override
//            public void onComplete(Result result) {
//                if(result instanceof Result.Success){
//                    List<DocumentSnapshot> documents = ((Result.Success<List<DocumentSnapshot>>)result).getData();
//                    getNameFromDocumentSnapshots(documents, new RepositoryListenerCallback<Result>() {
//                        @Override
//                        public void onUpdate(Result result) {
//                            nameList = ((Result.Success<List<String>>)result).getData();
//                            callback.onComplete(result);
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    private void getNameFromDocumentSnapshots(List<DocumentSnapshot> documents, RepositoryListenerCallback<Result> callback)
//    {
//        executor.execute(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Log.d("DEBUG", "Executor run getTasksFromDocumentSnapshots " + executor.toString());
//                List<String> toReturn = new ArrayList<>();
//                for(DocumentSnapshot doc : documents)
//                {
//                    MyTask nameToAdd = doc.toObject(MyTask.class);
//                   for(int i=0;i<documents.size();i++){
//                       String toAdd = new String((documents.get(i).getString("displayName")));
//                       toReturn.add(toAdd);
//                   }
//                    toReturn.add(String.valueOf(nameToAdd));
//                }
//                callback.onUpdate(new Result.Success<List<String>>(toReturn));
//            }
//        });
//
//    }

    public List<Task> getCurrUserTaskList(String userId)
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
                    currUserTaskList = ((Result.Success<List<Task>>) result).getData();
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

    public void updateTask(Task toUpdate, RepositoryCallback<Result> callback)
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

    public Task getTaskFromId(int taskId)
    {
        return taskMap.get(taskId);
    }


    public LiveData<Boolean> isCurrUserTaskListUpdated() { return currUserTaskListUpdated; }

    public void setDateSource(FirebaseDataSource ds) {this.firebaseDataSource = ds;}


    public interface TaskRepositoryCallback<T>
    {
        void onComplete(Result result);
    }
}
