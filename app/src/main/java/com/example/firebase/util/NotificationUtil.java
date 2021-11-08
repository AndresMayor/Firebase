package com.example.firebase.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.firebase.BuildConfig;
import com.example.firebase.R;

public class NotificationUtil {

    private static final String CHANNEL_ID ="messages";
    private static final  String CHANNEL_NAME ="Messages";

    private static  int ID = 0;


    //Context es el objeto que nos permite hacer  llamados dentro de las actividades, en este caso vamos hacer un llamado al sistema para mostrar la notificacion
    public static void showNotification(Context context,String title,String message){

        NotificationManager manager =( NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.icono);


        Notification notification = builder.build();
        manager.notify(ID++,notification);


    }




}
