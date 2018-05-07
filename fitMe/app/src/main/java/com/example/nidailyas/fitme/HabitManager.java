package com.example.nidailyas.fitme;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HabitManager {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("habits");

    public ArrayList<String> getAllHabitNames() {
        final ArrayList<String> habitNamesList = new ArrayList<String>() {
        };
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                habitNamesList.clear();
                for (DataSnapshot habitSnapshot : dataSnapshot.getChildren()) {
                    Habit habit = habitSnapshot.getValue(Habit.class);
                    habitNamesList.add(habit.getHabitName());
                }
                habitNamesList.add("Add new habit");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return habitNamesList;
    }

    public void addHabitToDb() {
        String habitId = databaseReference.push().getKey();
        Habit habit = new Habit(habitId, "Eat veggies","Have almost 2 greens." );
        databaseReference.child(habitId).setValue(habit);

        ArrayList<String> times = new ArrayList<String>();
        times.add("18:22");
        String habitFrequencyTimingId =  new HabitFrequencyTimingManager().addHabitFrequencyTiming(times, habitId);
        Log.w("Thissssss", habitFrequencyTimingId);
        //ArrayList<String> habitFrequencyTimings = new ArrayList<String>();
        //habitFrequencyTimings.add(habitFrequencyTimingId);
        //new PlanningManager().addPlanning(habitFrequencyTimings);
        new PlanningManager().updatePlanning(habitFrequencyTimingId);
    }
}
