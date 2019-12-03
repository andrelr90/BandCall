package com.knowalr.bandcallnotify;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

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
}
