package com.example.notify;
import android.app.Activity;
import android.Manifest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CustomBroadcastReceiver extends BroadcastReceiver {

    private static final int CAMERA_PERMISSION_CODE = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.CUSTOM_ACTION)) {
            Log.d("Pandi received", "Received");
            String receivedMessage = intent.getStringExtra("message");
            Toast.makeText(context, receivedMessage + " Custom Intent Received", Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction().equals(MainActivity.CUSTOM_CAMERA)) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start camera activity
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(cameraIntent);
                }
            } else {
                // Permission not granted, request permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }

        }
    }
}

