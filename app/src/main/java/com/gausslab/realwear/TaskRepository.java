package com.gausslab.realwear;

public class TaskRepository {
    private static volatile TaskRepository INSTANCE= new TaskRepository();
    public static TaskRepository getInstance(){return INSTANCE;}
    private FirebaseDataSource firebaseDataSource;

    public void loadMyTaskList(final String id, final TaskRepositoryCallback callback){
        firebaseDataSource.loadMyTaskList(id,result ->{
            if(result instanceof Result.Success){
                callback.onComplete(result);
            }
        });
    }



    public void setDateSource(FirebaseDataSource ds){this.firebaseDataSource = ds;}

    public interface TaskRepositoryCallback<T>{
        void onComplete(Result result);
    }
}
