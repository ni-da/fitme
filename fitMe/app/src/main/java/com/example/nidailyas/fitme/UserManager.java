package com.example.nidailyas.fitme;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

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

    public Long getUserScore() {
        final Long[] score = new Long[1];
        DatabaseReference databaseReferenceUser;
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReferenceUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    score[0] = user.getScore();
                    Log.w("Scoreeeee: ", score[0].toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return score[0];
    }
    User user1 = new User();


    public User getUserFromDb(){
        final User[] user = new User[1];
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReferenceUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user[0] = dataSnapshot.getValue(User.class);
                    user1.userId = (user[0].userId);
                    user1.name = (user[0].name);
                    user1.dateOfBirth = (user[0].dateOfBirth);
                    user1.gender = (user[0].gender);
                    user1.weight = (user[0].weight);
                    user1.height = (user[0].height);
                    user1.begin_bp_upper = (user[0].begin_bp_upper);
                    user1.begin_bp_lower = (user[0].begin_bp_lower);
                    user1.score = (user[0].score);
                    user1.planningId = (user[0].planningId);
                    user1.levelId = (user[0].levelId);
                    user1.profileImageUrl = (user[0].profileImageUrl);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user1;
        }
}