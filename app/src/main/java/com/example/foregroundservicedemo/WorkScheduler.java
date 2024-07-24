package com.example.foregroundservicedemo;

import android.content.Context;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class WorkScheduler {
    public static void scheduleWork(Context context) {
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 5, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }
}

