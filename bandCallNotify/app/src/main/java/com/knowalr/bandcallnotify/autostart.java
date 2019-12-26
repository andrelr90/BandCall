package com.knowalr.bandcallnotify;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class autostart extends BroadcastReceiver {
    public void onReceive(Context context, Intent arg1)
    {
        Intent intent = new Intent(context,ServiceBackground.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(context, "BandCallNotify foi iniciado automaticamente.", Toast.LENGTH_SHORT).show();
            Log.i("Autostart", "started");
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

    }
}
