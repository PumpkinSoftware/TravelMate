/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pumpkinsoftware.travelmate.chat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.TravelDetailsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String type = remoteMessage.getData().get("type");

        System.out.println("TYPE IS: "+type);

        switch(type){
            case "image": notificationImage(remoteMessage); break;
            case "message" : notificationMessage(remoteMessage); break;
            case "trip" : notificationTripInformation(remoteMessage); break;
            default: notificationStandard(remoteMessage); break;
        }

    }

    public void notificationImage(RemoteMessage remoteMessage){

        Intent notificationIntent = new Intent(this, TravelDetailsActivity.class);

        //if(TravelDetailsActivity.isAppRunning && (TravelDetailsActivity.tripId.equals(remoteMessage.getData().get("tripId")))){
        //    //Some action
        //    return;
        //}
        /*else{
            //Show notification as usual
        }*/

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.default_trip);

        Intent likeIntent = new Intent(this,TravelDetailsActivity.class);
        likeIntent.putExtra(TravelDetailsActivity.EXTRA_ID,remoteMessage.getData().get("tripId"));
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, likeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        /*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels(notificationManager,"chatChannel");
        }
        */
        RequestOptions myOptions = new RequestOptions().circleCrop();

        /*MOLTO PROBABILMENTE L'ERRORE STA QUI ED E'QUESTO:
        * java.lang.IllegalArgumentException: You must call this method on the main thread
        * */
        
        Glide.with(this)
                .asBitmap()
                .load(remoteMessage.getData().get("image"))
                .apply(myOptions)
                .into(new MyTarget(this,remoteMessage,notificationManager,pendingIntent) {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        //You should use an actual ID instead
                        int notificationId = new Random().nextInt(60000);

                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        NotificationCompat.Builder notificationBuilder =
                                new NotificationCompat.Builder(this.getContext(), "chatChannel")
                                        .setLargeIcon(resource)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle(this.getRemoteMessage().getData().get("title"))
                                        .setStyle(new NotificationCompat.BigPictureStyle()
                                                .setSummaryText(this.getRemoteMessage().getData().get("message"))
                                                .bigPicture(resource))/*Notification with Image*/
                                        .setContentText(this.getRemoteMessage().getData().get("message"))
                                        .setAutoCancel(true)
                                        .setSound(defaultSoundUri)
                                        .setContentIntent(this.getPendingIntent());

                        this.getNotificationManager().notify(notificationId, notificationBuilder.build());

                    }
                });

    }

    public void notificationMessage(RemoteMessage remoteMessage){
        return;
    }

    public void notificationTripInformation(RemoteMessage remoteMessage){
        return;
    }

    public void notificationStandard(RemoteMessage remoteMessage){
        return;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager, String channel){
        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(channel, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}

/*
* PER TESTARE: 
* AVVIA L'APP, SUCCESSIVAMENTE IN VERBOSE SCRIVI 'TOKEN IS'
* COPIA IL TOKEN ED ESEGUI https://debugtm.herokuapp.com/user/test?token=
* AD APP APERTA SI VERIFICA IL CRASH
* SE FUNZIONA, DOVRESTI VEDERE UNA NOTIFICA E CON LO SWIPE DOWN L'ANTPRIMA DI UN'IMMAGINE
* */
