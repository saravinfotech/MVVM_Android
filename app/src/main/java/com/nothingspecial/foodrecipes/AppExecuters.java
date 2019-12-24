package com.nothingspecial.foodrecipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecuters {
    private static AppExecuters instance;
    private ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);
    private AppExecuters(){

    }

    public static AppExecuters getInstance(){
        if(instance == null){
            instance = new AppExecuters();
        }
        return instance;
    }

    public ScheduledExecutorService getNetworkIO(){
        return networkIO;
    }
}
