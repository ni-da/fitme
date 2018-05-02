package com.example.nidailyas.fitme;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DatabaseUser {
    //public User user;
    User user;
    public User getDbUser(){
        getDatabaseUser();
        return this.user;
    }
    public void getDatabaseUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user1 = dataSnapshot.getValue(User.class);
                            user = user1;
                        }
                        //Log.w("myAPp", user[0].planningId);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
//        Log.w("myAPp", user[0].planningId);

    }
    public List<Habit> getAllHabitsFromDb(){
        List<Habit> habits = null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        DataSnapshot dataSnapshot = databaseReference.child("Habits").a;
        return habits;
    }

}
