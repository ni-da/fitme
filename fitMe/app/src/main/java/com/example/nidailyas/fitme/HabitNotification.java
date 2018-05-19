package com.example.nidailyas.fitme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class HabitNotification {
    public void registerNotification(String notifyHabitId, ArrayList<String> times,
                                     int notifyHour, int notifyMin, Context context) {
        Calendar calendar = Calendar.getInstance();
        for (String timeItem : times) {
            String strHr = timeItem.substring(0, 2);
            String strMin = timeItem.substring(3, 5);

            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(strHr));
            calendar.set(Calendar.MINUTE, Integer.valueOf(strMin));
            calendar.set(Calendar.SECOND, 00);

            Intent intent = new Intent(context, Notification_reciever.class);
            intent.putExtra("habitId", notifyHabitId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
    }

    public void registerNotification1(String notifyHabitId,
                                     int notifyHour, int notifyMin, Context context) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(notifyHour));
        calendar.set(Calendar.MINUTE, Integer.valueOf(notifyMin));
        calendar.set(Calendar.SECOND, 00);

        Intent intent = new Intent(context, Notification_reciever.class);
        //intent.putExtra("habitId", notifyHabitId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
