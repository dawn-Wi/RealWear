package com.gausslab.realwear;

import java.util.concurrent.Executor;

public class Repository
{
    protected Executor executor;
    protected FileService fileService;
    protected FirebaseDataSource firebaseDataSource;

    public void setExecutor(Executor exec)
    {
        this.executor = exec;
    }

    public void setDataSource(FirebaseDataSource ds)
    {
        this.firebaseDataSource = ds;
    }

    public void setFileService(FileService fs) { this.fileService = fs; }

    public interface RepositoryListenerCallback<T>
    {
        void onUpdate(T result);
    }

    public interface RepositoryCallback<T>
    {
        void onComplete(T result);
    }
}
