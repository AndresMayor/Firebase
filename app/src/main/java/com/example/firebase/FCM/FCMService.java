package com.example.firebase.FCM;

import androidx.annotation.NonNull;

import com.example.firebase.util.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {





    //Este metodo me permite recibir mensajes asi la aplicacion la hayan cerrado
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = remoteMessage.getData().toString();
        NotificationUtil.showNotification(getApplicationContext(),"Nuevo mensaje",message);
    }
}
