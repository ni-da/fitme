package com.example.nidailyas.fitme;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    public void getHabitFrequencyTimingByIdFromDb(final MyCallback<HabitFrequencyTiming> myCallback, String id) {
        databaseReferenceHabitFrequencyTimings.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HabitFrequencyTiming habitFrequencyTiming = dataSnapshot.getValue(HabitFrequencyTiming.class);
                    myCallback.onCallback(habitFrequencyTiming);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
