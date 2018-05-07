package com.example.nidailyas.fitme;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlanningManager {
    public void addPlanning(ArrayList<String> habitFrequencyTimings) {
        DatabaseReference databaseReference_plannings;
        databaseReference_plannings = FirebaseDatabase.getInstance().getReference("plannings");
        String planningId = databaseReference_plannings.push().getKey(); //id
        Planning planning = new Planning(planningId, habitFrequencyTimings);
        databaseReference_plannings.child(planningId).setValue(planning);
    }
    public void updatePlanning(final String habitFrequency){
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReferenceUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    DatabaseReference databaseReference_plannings = FirebaseDatabase.getInstance().getReference("plannings");
                    databaseReference_plannings.child(user.getPlanningId()).child("habitFrequencies").setValue(habitFrequency);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // start here
        //Log.w("PLANNNINGGG", databaseReference_plannings.child(planId).toString());
        //databaseReference_plannings.child(planId).child("habitFrequencies").setValue(habitFrequency);
                //push().setValue(habitFrequency);

    }
}
