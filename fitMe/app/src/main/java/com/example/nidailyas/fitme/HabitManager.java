package com.example.nidailyas.fitme;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HabitManager {

    DatabaseReference databaseReferenceHabit = FirebaseDatabase.getInstance().getReference("habits");

    public void getHabitNamesFromDb(final MyCallback<ArrayList<String>> myCallback) {
        final ArrayList<String> habitNames = new ArrayList<>();
        databaseReferenceHabit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habit habit = snapshot.getValue(Habit.class);
                    habitNames.add(habit.getHabitName());
                }
                habitNames.add("Add new habit");
                myCallback.onCallback(habitNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addHabitToDb() {
        String habitId = databaseReferenceHabit.push().getKey();
        Habit habit = new Habit(habitId, "Eat veggies", "Have almost 2 greens.");
        databaseReferenceHabit.child(habitId).setValue(habit);

        ArrayList<String> times = new ArrayList<String>();
        times.add("18:22");
        String habitFrequencyTimingId = new HabitFrequencyTimingManager().addHabitFrequencyTiming(times, habitId);
        //ArrayList<String> habitFrequencyTimings = new ArrayList<String>();
        //habitFrequencyTimings.add(habitFrequencyTimingId);
        //new PlanningManager().addPlanning(habitFrequencyTimings);
        new PlanningManager().updatePlanning(habitFrequencyTimingId);
    }

    public void getHabitByIdFromDb(final MyCallback<Habit> myCallback, String habitId) {
        databaseReferenceHabit.child(habitId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Habit habit = dataSnapshot.getValue(Habit.class);
                    myCallback.onCallback(habit);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getHabitByNameFromDb(final MyCallback<Habit>
                                             myCallback, final String habitName) {
        databaseReferenceHabit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habit habit = snapshot.getValue(Habit.class);
                    if (habit.getHabitName().equals(habitName)) {
                        myCallback.onCallback(habit);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
