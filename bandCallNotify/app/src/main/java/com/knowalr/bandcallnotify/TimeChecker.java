package com.knowalr.bandcallnotify;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.TimerTask;

/**
 * Created by andre on 24/07/2019.
 */

public class TimeChecker extends TimerTask{
    private Context contexto;
    private AudioManager am;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;

    TimeChecker(Context contexto){
        this.contexto = contexto;
        am = (AudioManager) contexto.getSystemService(Context.AUDIO_SERVICE);

        builder = new NotificationCompat.Builder(contexto, "bandCallNotify")
                .setContentTitle("Call")
                .setContentText("You are receiving an app call.")
                .setSmallIcon(R.drawable.ic_android)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setShowWhen(true);

        notificationManager = NotificationManagerCompat.from(contexto);
    }

    @Override
    public void run(){
        int mode = am.getMode();
        if(AudioManager.MODE_RINGTONE == mode){
            builder.setWhen(System.currentTimeMillis());    //Atualiza o "x minutes ago".
            notificationManager.notify(1538, builder.build());
        }
        //Check other options: https://stackoverflow.com/questions/49302694/how-do-i-detect-skype-telegram-whatsapp-calls-when-my-messenger-app-is-in-a-call?rq=1
    }
}
