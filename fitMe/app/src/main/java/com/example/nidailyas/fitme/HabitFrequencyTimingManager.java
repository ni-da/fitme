package com.example.nidailyas.fitme;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HabitFrequencyTimingManager {

    public String addHabitFrequencyTiming(ArrayList<String> times, String habitId) {
//        ArrayList<String> times = new ArrayList<String>();
//        times.add("18:22");
        DatabaseReference databaseReference_HabitFrequencyTimings;
        databaseReference_HabitFrequencyTimings = FirebaseDatabase.getInstance().getReference("habitFrequencyTimings");
        String habitFrequencyTimingId = databaseReference_HabitFrequencyTimings.push().getKey();
        HabitFrequencyTiming habitFrequencyTiming = new HabitFrequencyTiming(habitFrequencyTimingId,
                times,habitId);
        databaseReference_HabitFrequencyTimings.child(habitFrequencyTimingId).setValue(habitFrequencyTiming);
        return habitFrequencyTimingId;
    }
}
