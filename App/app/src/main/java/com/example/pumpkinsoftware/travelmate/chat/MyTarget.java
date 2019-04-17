package com.example.pumpkinsoftware.travelmate.chat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.RemoteMessage;

public class MyTarget extends SimpleTarget<Bitmap> implements Runnable{
    private final Context context;
    private final RemoteMessage remoteMessage;
    private final NotificationManager notificationManager;
    private final PendingIntent pendingIntent;

    public MyTarget(Context context, RemoteMessage remoteMessage, NotificationManager notificationManager, PendingIntent pendingIntent) {
        this.context = context;
        this.remoteMessage = remoteMessage;
        this.notificationManager = notificationManager;
        this.pendingIntent = pendingIntent;
    }

    public PendingIntent getPendingIntent(){
        return pendingIntent;
    }

    public Context getContext(){
        return context;
    }

    public RemoteMessage getRemoteMessage(){
        return remoteMessage;
    }

    public NotificationManager getNotificationManager(){
        return notificationManager;
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

    }

    @Override
    public void run() {

    }
}
