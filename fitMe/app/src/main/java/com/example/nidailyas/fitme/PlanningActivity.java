package com.example.nidailyas.fitme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanningActivity extends AppCompatActivity {
    Spinner spinner_habits;
    EditText editText_habitName;
    EditText getEditText_habitFrequency;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TimePicker timePicker;
    int notifyHour, notifyMin;
    List<String> habitNamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        habitNamesList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();

        // auth 3. check to see if the user is currently signed in.
        if (firebaseAuth.getCurrentUser() == null) {
            // user is not loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("habits");
        spinner_habits = findViewById(R.id.spinner_habits);
        editText_habitName = findViewById(R.id.editText_habitName);
        getEditText_habitFrequency = findViewById(R.id.editText_habitFrequency);

        timePicker = findViewById(R.id.timePicker1);
        notifyHour = timePicker.getCurrentHour();
        notifyMin = timePicker.getCurrentMinute();


        ArrayAdapter<String> habitsAdapter = new ArrayAdapter<String>(PlanningActivity.this,
                android.R.layout.simple_spinner_dropdown_item, new HabitManager().getAllHabitNames());

        habitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_habits.setAdapter(habitsAdapter);

        spinner_habits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PlanningActivity.this, spinner_habits.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (spinner_habits.getSelectedItem().toString() == new HabitManager().getAllHabitNames().get(new HabitManager().getAllHabitNames().size()-1)){
//                    add_habit_content
                    findViewById(R.id.add_habit_content).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.add_habit_content).setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        findViewById(R.id.button_saveHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHabitToDb();
            }
        });

        findViewById(R.id.button_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HabitNotification().registerNotification(notifyHour, notifyMin, getApplicationContext());
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

    private void addHabitToDb() {
        String name = editText_habitName.getText().toString().trim();
        int frequency;
        if (!TextUtils.isEmpty(getEditText_habitFrequency.getText().toString())) {
            frequency = Integer.parseInt(getEditText_habitFrequency.getText().toString());
        } else {
            frequency = 1;
        }
        if (!TextUtils.isEmpty(name)) {
            String habitId = databaseReference.push().getKey();
            Habit habit = new Habit(habitId, name, frequency);
            databaseReference.child(habitId).setValue(habit);
            Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
        } else {
            editText_habitName.setError("Name required");
            editText_habitName.requestFocus();
        }
    }

    private Context getContext() {
        return this.getContext();
    }
}
