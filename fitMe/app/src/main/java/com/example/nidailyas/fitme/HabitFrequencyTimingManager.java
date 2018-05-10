package com.example.nidailyas.fitme;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HabitFrequencyTimingManager {
    DatabaseReference databaseReferenceHabitFrequencyTimings
            = FirebaseDatabase.getInstance().getReference("habitFrequencyTimings");

    public String addHabitFrequencyTimingToDb(HabitFrequencyTiming habitFrequencyTiming) {
        String habitFrequencyTimingId = databaseReferenceHabitFrequencyTimings.push().getKey();
        habitFrequencyTiming.habitFrequencyTimingId = habitFrequencyTimingId;
        databaseReferenceHabitFrequencyTimings.child(habitFrequencyTimingId)
                .setValue(habitFrequencyTiming);
        return habitFrequencyTimingId;
    }
}
