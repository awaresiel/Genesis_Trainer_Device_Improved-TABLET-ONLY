package com.example.genesis_trainer_device_improved.ROOM.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ROOMRepo {

    private static final Object LOCK = new Object();
    private static ROOMRepo INSTANCE;
    private final ExecutorService databaseIO;
    private final ExecutorService diskIO;
//    private final ExecutorService networkIO;

//    private ROOMRepo(ExecutorService databaseIO,ExecutorService diskIO,ExecutorService networkIO){
    private ROOMRepo(ExecutorService databaseIO, ExecutorService diskIO){
        this.databaseIO=databaseIO;
        this.diskIO=diskIO;

    }

    public static ROOMRepo getInstance()
    {
        if (INSTANCE == null)
        {
            synchronized (LOCK)
            {
                INSTANCE = new ROOMRepo(Executors.newFixedThreadPool(5), Executors.newSingleThreadExecutor());
            }
        }
        return INSTANCE;
    }


    public ExecutorService getDatabaseIO()
    {
        return databaseIO;
    }

    public ExecutorService getDiskIO()
    {
        return diskIO;
    }
}
