package com.knowalr.bandcallnotify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;

/**
 * Created by andre on 24/07/2019.
 */

public class ServiceBackground extends Service {
    private Timer loopCheck;
    private static boolean active;

    public static boolean isActive(){
        return active;
    }

    public static void setActive(boolean activeParam){
        active = activeParam;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        String NOTIFICATION_CHANNEL_ID = "bandCallNotify_background";
        String channelName = "Background Service";
        NotificationChannel chan = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            chan.setLightColor(Color.BLUE);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Context contexto = getApplicationContext();

        loopCheck = new Timer();
        loopCheck.scheduleAtFixedRate(new TimeChecker(contexto),0,5000);
        ServiceBackground.setActive(true);

        return START_STICKY;    //Evita que o serviço seja desativado logo após a inicialização
    }

    @Override
    public void onDestroy(){
        loopCheck.cancel();
        ServiceBackground.setActive(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){

    }
}
