package com.example.nidailyas.fitme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class PlanningActivity extends AppCompatActivity {
    EditText editText_habitName;
    EditText editText_habitDetail;
    TimePicker timePicker;
    int notifyHour, notifyMin;
    Boolean customHabit;
    String selectedHabitId;
    ArrayList<String> times = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        final Spinner spinner_habits = findViewById(R.id.spinner_habits);


        editText_habitName = findViewById(R.id.editText_habitName);
        editText_habitDetail = findViewById(R.id.editText_habitDetail);
        timePicker = findViewById(R.id.timePicker1);
        notifyHour = timePicker.getCurrentHour();
        notifyMin = timePicker.getCurrentMinute();


        new HabitManager().getHabitNamesFromDb(new MyCallback<ArrayList<String>>() {
            @Override
            public void onCallback(final ArrayList<String> element) {
                final String[] habitNamesArray = element.toArray(new String[element.size()]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PlanningActivity.this,
                        android.R.layout.simple_list_item_1, habitNamesArray);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_habits.setAdapter(arrayAdapter);

                spinner_habits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == element.size() - 1) {
                            customHabit = true;
                            // add new habit is selected
                            // - add habit to db => habits, habitFrequencies, getUserPlanningId(), plannings
                            //

                        } else {
                            customHabit = false;
                            // - add habit to db => habitFrequencies, getUserPlanningId(), plannings
                            new HabitManager().getHabitByNameFromDb(new MyCallback<Habit>() {
                                @Override
                                public void onCallback(final Habit element) {
                                    editText_habitName.setText(element.getHabitName());
                                    editText_habitName.setInputType(InputType.TYPE_NULL);
                                    editText_habitDetail.setText(element.getDescription());
                                    editText_habitDetail.setInputType(InputType.TYPE_NULL);
                                    selectedHabitId = element.getHabitId();
                                }
                            }, habitNamesArray[i]);
                        }

                        // plan notifications
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        findViewById(R.id.button_saveHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customHabit) {
                } else {
                    addHabitToUserProfile(times, selectedHabitId);
                }
            }
        });


        findViewById(R.id.button_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HabitNotification().registerNotification(notifyHour, notifyMin, getApplicationContext());
            }
        });

        findViewById(R.id.button_addTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeString = Integer.toString(timePicker.getCurrentHour()) + ":" +
                        Integer.toString(timePicker.getCurrentMinute());
                LinearLayout linearLayout = findViewById(R.id.LinearLayout_habitTimes);
                TextView time = new TextView(getApplicationContext());
                times.add(timeString);
                time.setText(timeString);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                time.setLayoutParams(params);
                linearLayout.addView(time);
            }
        });


    }

    private void registerNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 30);

        Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    private void addHabitToUserProfile(ArrayList<String> times, String habitId) {
        new UserManager().addHabitToUserPlanning(times, habitId);

//        String name = editText_habitName.getText().toString().trim();
//        int frequency;
//        if (!TextUtils.isEmpty(getEditText_habitFrequency.getText().toString())) {
//            frequency = Integer.parseInt(getEditText_habitFrequency.getText().toString());
//        } else {
//            frequency = 1;
//        }
//        if (!TextUtils.isEmpty(name)) {
//            String habitId = datassbaseReference.push().getKey();
//            Habit habit = new Habit(habitId, name, frequency);
//            databaseReference.child(habitId).setValue(habit);
//            Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
//        } else {
//            editText_habitName.setError("Name required");
//            editText_habitName.requestFocus();
//        }
    }


}
