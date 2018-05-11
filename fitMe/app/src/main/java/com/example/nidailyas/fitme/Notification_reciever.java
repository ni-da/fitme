package com.example.nidailyas.fitme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class Notification_reciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        // display the notification
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // after clicking on the notification --> redirects to an activity
        Intent executeHabitIntent = new Intent(context, ExecuteHabitActivity.class);
        executeHabitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity
                (context, 100, executeHabitIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "DEFAULT_CHANNEL_ID")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_media_next)
                .setContentTitle("notification Title")
                .setContentText("notification Text")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
