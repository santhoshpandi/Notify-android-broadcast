package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    ImageButton implicit,explicit;
    public static final String CUSTOM_ACTION = "com.example.CUSTOM_ACTION";
    public static final String CUSTOM_CAMERA="com.example.notify.OPEN_CAMERA";
    private static final int CAMERA_PERMISSION_CODE = 1001;
    VideoView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        implicit=findViewById(R.id.imageButton);
        explicit=findViewById(R.id.imageButton2);
        getSupportActionBar().hide();

        v = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.rain);
        v.setVideoURI(uri);
        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        v.start();

        IntentFilter filter = new IntentFilter(CUSTOM_ACTION);
        registerReceiver(new CustomBroadcastReceiver(), filter);
        IntentFilter filter1 = new IntentFilter(CUSTOM_CAMERA);
        registerReceiver(new CustomBroadcastReceiver(), filter1);
        implicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(CUSTOM_ACTION);
                intent.putExtra("message","hi santhosh");
                Log.d("pandi sent", "sent");
                sendBroadcast(intent);
            }
        });

        explicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent();
                openCameraIntent.setAction(MainActivity.CUSTOM_CAMERA);
                sendBroadcast(openCameraIntent);

            }
        });


    }
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_CODE);
        } else {
            // Permission already granted, proceed with camera related actions
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start camera activity
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(cameraIntent);
                }
            } else {
                // Permission denied, show a message or handle accordingly
            }
        }
    }

}