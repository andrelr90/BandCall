package com.knowalr.bandcallnotify;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by andre on 24/07/2019.
 */

public class MainActivity extends AppCompatActivity {
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        createNotificationChannel();

//        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) && !Settings.canDrawOverlays(getApplicationContext())) {
//            RequestPermission();
//        }

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



//    private void RequestPermission() {
//        // Check if Android M or higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Show alert dialog to the user saying a separate permission is needed
//            // Launch the settings activity if the user prefers
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + getApplicationContext().getPackageName()));
//            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(getApplicationContext())) {
//                    // PermissionDenied();
//                    Toast.makeText(getApplicationContext(), "You must give this app the permission to overlay other apps in order to it start the background service.", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Permission Granted-System will work
//                }
//
//            }
//        }
//    }

}
