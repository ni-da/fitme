package com.example.nidailyas.fitme;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {
    public String getUserPlannigId() {
        final String[] planningId = new String[1];
        DatabaseReference databaseReferenceUser;
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReferenceUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    planningId[0] = user.planningId;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return planningId[0];

    }
}