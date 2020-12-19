package com.knowalr.bandcallnotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i("a", "Skip = 5");
        TimeChecker.skip = 5;
    }
}
