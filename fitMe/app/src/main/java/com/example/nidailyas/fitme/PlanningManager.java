package com.example.nidailyas.fitme;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlanningManager {
    public void addPlanning(ArrayList<String> habitFrequencyTimings) {
        DatabaseReference databaseReference_plannings;
        databaseReference_plannings = FirebaseDatabase.getInstance().getReference("plannings");
        String planningId = databaseReference_plannings.push().getKey(); //id
        Planning planning = new Planning(planningId, habitFrequencyTimings);
        databaseReference_plannings.child(planningId).setValue(planning);
    }
    public void updatePlanning(String habitFrequency){
        String planId = new UserManager().getUserPlannigId();
        DatabaseReference databaseReference_plannings;
        databaseReference_plannings = FirebaseDatabase.getInstance().getReference("plannings");
        // start here
        databaseReference_plannings.child(planId).child("habitFrequencies").push().setValue(habitFrequency);

    }
}
