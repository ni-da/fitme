package com.example.nidailyas.fitme;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlanningManager {
    DatabaseReference databaseReferencePlannings = FirebaseDatabase.getInstance().getReference("plannings");

    public void addPlanning(ArrayList<String> habitFrequencyTimings) {
        String planningId = databaseReferencePlannings.push().getKey(); //id
        Planning planning = new Planning(planningId, habitFrequencyTimings);
        databaseReferencePlannings.child(planningId).setValue(planning);
    }

    public void updatePlanning(final String habitFrequencyId) {
        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User user) {
                final String planningId = user.getPlanningId();
                getPlanningByIdFromDb(new MyCallback<Planning>() {
                    @Override
                    public void onCallback(Planning planning) {
                        planning.habitFrequencies.add(habitFrequencyId);
                        databaseReferencePlannings.child(planningId).setValue(planning);
                    }
                }, planningId);
            }
        });
    }

    public void getPlanningByIdFromDb(final MyCallback<Planning> myCallback, final String planningId) {
        databaseReferencePlannings.child(planningId).addValueEventListener //addListenerForSingleValueEvent
                (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.w("Planning", "NotFML!");
                    Planning planning = dataSnapshot.getValue(Planning.class);
                    Log.w("getPlanningByIdFromDb: ", planning.getPlanningId());
                    myCallback.onCallback(planning);
                }else {
                    Log.w("Planning", "FML!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
