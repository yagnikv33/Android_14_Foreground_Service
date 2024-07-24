package com.example.foregroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RestartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MyForegroundService.class);
        context.startForegroundService(serviceIntent);
    }
}