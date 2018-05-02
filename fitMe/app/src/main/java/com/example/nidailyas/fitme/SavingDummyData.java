package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class SavingDummyData extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_dummy_data);


        findViewById(R.id.button_addLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLevel();
            }
        });
        findViewById(R.id.button_addHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHabit();
            }
        });
        findViewById(R.id.button_addRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecord();
            }
        });
        findViewById(R.id.button_addHabitFrequencyTiming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHabitFrequencyTiming();
            }
        });
        findViewById(R.id.button_addPlanning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlanning();
            }
        });

    }


    public void addLevel() {
        DatabaseReference databaseReferenc_levels;
        databaseReferenc_levels = FirebaseDatabase.getInstance().getReference("levels");
        String levelId = databaseReferenc_levels.push().getKey(); //id
        Level level = new Level(levelId, "beginer", 0, 100);
        databaseReferenc_levels.child(levelId).setValue(level);
        toast();
    }

    public void addHabit() {
//        DatabaseReference databaseReference_habits;
//        databaseReference_habits = FirebaseDatabase.getInstance().getReference("habits");
//        String habitId = databaseReference_habits.push().getKey(); //id
//        Habit habit = new Habit(habitId, "Run", "Run for almost 20 minuts.");
//        databaseReference_habits.child(habitId).setValue(habit);
        new HabitManager().addHabitToDb();
        toast();
    }

    public void addRecord() {
        DatabaseReference databaseReference_records;
        databaseReference_records = FirebaseDatabase.getInstance().getReference("records");
        String recordId = databaseReference_records.push().getKey(); //id
        Record record = new Record(recordId, "Completed", Calendar.getInstance().getTime(), "4YhI1n2nAkUkW4IWuIxWq8EwMSj1",
                "-LB1j59qXHIOtCWZOxn9");
        databaseReference_records.child(recordId).setValue(record);
        toast();
    }

    public void addHabitFrequencyTiming() {
        ArrayList<String> times = new ArrayList<String>();
        times.add("18:22");
        DatabaseReference databaseReference_HabitFrequencyTimings;
        databaseReference_HabitFrequencyTimings = FirebaseDatabase.getInstance().getReference("habitFrequencyTimings");
        String habitFrequencyTimingId = databaseReference_HabitFrequencyTimings.push().getKey();
        HabitFrequencyTiming habitFrequencyTiming = new HabitFrequencyTiming(habitFrequencyTimingId,
                times,
                "-LB1kYrTXal7bwMkw8or");
        databaseReference_HabitFrequencyTimings.child(habitFrequencyTimingId).setValue(habitFrequencyTiming);
        toast();
    }
    public void addPlanning() {
        ArrayList<String> habitFrequencyTimings = new ArrayList<String>();
        habitFrequencyTimings.add("-LBSt6H9SHYau4eJjBfe");

        DatabaseReference databaseReference_plannings;
        databaseReference_plannings = FirebaseDatabase.getInstance().getReference("plannings");
        String planningId = databaseReference_plannings.push().getKey(); //id
        Planning planning = new Planning(planningId, habitFrequencyTimings);
        databaseReference_plannings.child(planningId).setValue(planning);
        toast();
    }

    public void toast() {
        Toast.makeText(this, "Database updated", Toast.LENGTH_LONG).show();
    }
}
