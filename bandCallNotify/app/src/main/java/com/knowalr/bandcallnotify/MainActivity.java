package com.knowalr.bandcallnotify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by andre on 24/07/2019.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        createNotificationChannel();

        Button botao = findViewById(R.id.button);
        if(!ServiceBackground.isActive()){
            botao.setText("Iniciar serviço");
        } else {
            botao.setText(" Parar serviço ");
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacao smart band";
            String description = "canal de notificação de smart bands.";
            String CHANNEL_ID = "bandCallNotify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void buttonAction(View view){
        Button botao = findViewById(R.id.button);
        if(ServiceBackground.isActive()){
            botao.setText("Iniciar serviço");
            stopService();
        } else{
            botao.setText(" Parar serviço ");
            startService();
        }
    }

    private void startService(){
        Intent i = new Intent(getApplicationContext(), ServiceBackground.class);
        getApplication().startService(i);
        Toast.makeText(getApplicationContext(), "Serviço iniciado.", Toast.LENGTH_SHORT).show();
    }

    private void stopService() {
        Toast.makeText(getApplicationContext(), "Serviço interrompido.", Toast.LENGTH_SHORT).show();
        getApplication().stopService(new Intent(getApplicationContext(), ServiceBackground.class));
    }

}
