package com.knowalr.bandcallnotify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.TimerTask;

/**
 * Created by andre on 24/07/2019.
 */

public class TimeChecker extends TimerTask{
    private Context contexto;
    private AudioManager am;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;
    public static int skip;

    TimeChecker(Context contexto){
        this.contexto = contexto;
        am = (AudioManager) contexto.getSystemService(Context.AUDIO_SERVICE);
        skip = 0;

        builder = new NotificationCompat.Builder(contexto, "bandCallNotify")
                .setContentTitle("Call")
                .setContentText("You are receiving an app call.")
                .setSmallIcon(R.drawable.icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setShowWhen(true)
                .setDeleteIntent(createOnDismissedIntent(contexto));

        notificationManager = NotificationManagerCompat.from(contexto);
    }

    private PendingIntent createOnDismissedIntent(Context context) {
        //Based on: https://stackoverflow.com/questions/14671453/catch-on-swipe-to-dismiss-event/20670984#20670984

        Intent intent = new Intent(context, NotificationDismissedReceiver.class);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context.getApplicationContext(),
                        1576, intent, 0);
        return pendingIntent;
    }

    @Override
    public void run(){
        int mode = am.getMode();
        if(AudioManager.MODE_RINGTONE == mode && skip == 0){
            builder.setWhen(System.currentTimeMillis());    //Atualiza o "x minutes ago".
            notificationManager.notify(1538, builder.build());
        }
        //Check other options: https://stackoverflow.com/questions/49302694/how-do-i-detect-skype-telegram-whatsapp-calls-when-my-messenger-app-is-in-a-call?rq=1

        if(skip>0) {
            skip--;
        }
    }
}
