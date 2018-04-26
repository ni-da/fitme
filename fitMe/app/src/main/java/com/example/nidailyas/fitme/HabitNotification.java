package com.example.nidailyas.fitme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import static android.content.Context.ALARM_SERVICE;

public class HabitNotification{
    public void registerNotification(int notifyHour, int notifyMin, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, notifyHour);
        calendar.set(Calendar.MINUTE, notifyMin);
        calendar.set(Calendar.SECOND, 30);

        Intent intent = new Intent(context, Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
