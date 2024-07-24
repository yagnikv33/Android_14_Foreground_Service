package com.example.foregroundservicedemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btn;
    boolean isPermissionGranted = false;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn_start_service);

        if (checkAndRequestPermissions()) {
            startMyForegroundService();
        }

//            btn.setOnClickListener(v -> {
//                // Schedule the work
//                WorkScheduler.scheduleWork(this);
//
//        BatteryOptimization.requestBatteryOptimizationExclusion(this);

//                // Start the foreground service if not running
//                Intent serviceIntent = new Intent(this, MyForegroundService.class);
//                startForegroundService(serviceIntent);
//            });

    }

    private boolean checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.FOREGROUND_SERVICE_LOCATION
                    },
                    REQUEST_LOCATION_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMyForegroundService();
            } else {
                // Handle the case when permission is denied
            }
        }
    }

    private void startMyForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            BatteryOptimization.requestBatteryOptimizationExclusion(this);
            Intent serviceIntent = new Intent(this, MyForegroundService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            startService(new Intent(this, MyForegroundService.class));
        }
        WorkScheduler.scheduleWork(this);
    }
}