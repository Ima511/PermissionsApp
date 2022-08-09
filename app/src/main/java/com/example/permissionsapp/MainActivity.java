package com.example.permissionsapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<String[]> aPermissionResultLauncher;

    private boolean isCameraPermissionGranted = false;
    private boolean isLocationPermissionGranted = false;
    private boolean isReadStoragePermissionGranted= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionLauncher();
        requestPermission();
    }

    private void requestPermission(){

        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;

        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        isReadStoragePermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<String>();

        if (!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA);
        }

        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(!isReadStoragePermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!permissionRequest.isEmpty()){
            aPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }

    private void permissionLauncher(){

        aPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {

                if(result.get(Manifest.permission.CAMERA) != null){
                    isCameraPermissionGranted = result.get(Manifest.permission.CAMERA);
                }

                if(result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null){
                    isLocationPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                if(result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != null){
                    isReadStoragePermissionGranted = result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });

    }
}