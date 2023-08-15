package com.example.notify;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            showNotification(context, "Device Boot Completed", "Device has finished booting.");
        }
    }

    private void showNotification(Context context, String title, String message) {
        Log.d("Boot", "Completed ");
        NotificationUtils.showNotification(context, "Boot Completed", "Device has finished booting.");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

