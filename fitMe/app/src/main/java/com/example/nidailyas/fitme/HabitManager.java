package com.example.nidailyas.fitme;

import android.util.Log;

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


    public String addNewHabitToDb(Habit habit) {
        String habitId = databaseReferenceHabit.push().getKey();
        habit.habitId = habitId;
        databaseReferenceHabit.child(habitId).setValue(habit);

        ArrayList<String> times = new ArrayList<String>();
        times.add("18:22");
        return habitId;
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

    public void getUserHabits(final MyCallback<ArrayList<Habit>> myCallback) {
        final ArrayList<Habit> habits = new ArrayList<>();
        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User user) {
                new PlanningManager().getPlanningByIdFromDb(new MyCallback<Planning>() {
                    @Override
                    public void onCallback(Planning planning) {
                        ArrayList<String> freqs = planning.getHabitFrequencies();
                        for (String freq : freqs) {
                            new HabitFrequencyTimingManager().getHabitFrequencyTimingByIdFromDb
                                    (new MyCallback<HabitFrequencyTiming>() {
                                @Override
                                public void onCallback(HabitFrequencyTiming habitFrequencyTiming) {
                                    getHabitByIdFromDb(new MyCallback<Habit>() {
                                        @Override
                                        public void onCallback(Habit habit) {
                                            habits.add(habit);
                                            Log.w("Habitssss: ", habit.getHabitName());
                                            myCallback.onCallback(habits);
                                        }
                                    }, habitFrequencyTiming.getHabitId());
                                }
                            }, freq);
                        }
                    }
                }, user.getPlanningId());
            }
        });
    }

    public void getHabitTimesByHabitId(final MyCallback<HabitFrequencyTiming> myCallback,
                                       final String habitId) {
        final ArrayList<String> times = new ArrayList<>();
        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User user) {
                new PlanningManager().getPlanningByIdFromDb(new MyCallback<Planning>() {
                    @Override
                    public void onCallback(Planning planning) {
                        ArrayList<String> freqs = planning.getHabitFrequencies();
                        for (String freq : freqs) {
                            new HabitFrequencyTimingManager().getHabitFrequencyTimingByIdFromDb
                                    (new MyCallback<HabitFrequencyTiming>() {
                                @Override
                                public void onCallback(HabitFrequencyTiming habitFrequencyTiming) {
                                    if (habitFrequencyTiming.getHabitId().equals(habitId)){
                                        //Log.w("idke: ", habitFrequencyTiming.times.get(0));
                                        myCallback.onCallback(habitFrequencyTiming);
                                    }

                                }
                            }, freq);
                        }
                    }
                }, user.getPlanningId());
            }
        });
    }


}
