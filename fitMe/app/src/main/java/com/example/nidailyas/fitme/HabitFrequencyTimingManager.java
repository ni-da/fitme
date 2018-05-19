package com.example.nidailyas.fitme;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void getHabitFrequencyTimingByIdFromDb(final MyCallback<HabitFrequencyTiming> myCallback,
                                                  String id) {
        databaseReferenceHabitFrequencyTimings.child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            HabitFrequencyTiming habitFrequencyTiming = dataSnapshot.getValue(
                                    HabitFrequencyTiming.class);
                            myCallback.onCallback(habitFrequencyTiming);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

//    public void getUserHabitFrequencyTimingByHabitIdFromDb(
//            final MyCallback<HabitFrequencyTiming> myCallback,
//            String habitId) {
//        databaseReferenceHabitFrequencyTimings.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds: dataSnapshot){
//
//                        }
//                        if (dataSnapshot.exists()) {
//                            HabitFrequencyTiming habitFrequencyTiming = dataSnapshot.getValue(
//                                    HabitFrequencyTiming.class);
//                            myCallback.onCallback(habitFrequencyTiming);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }
}
