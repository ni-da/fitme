package com.example.nidailyas.fitme;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserManager {
    DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");

    public void getUserFromDb(final MyCallback<User> myCallback) {
        databaseReferenceUser.child(getCurrentUserIdFromDb())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            myCallback.onCallback(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void updateUserScore(final Long score) {

//        if (habitType == "Weight") {
//            if (result + 3 < idealWeight) {
//                score = -50;
//            } else {
//                score = 50;
//            }
//        }
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Long newScore = user.getScore() + score;
                databaseReferenceUser.child(user.getUserId()).child("score").setValue(newScore);
                new LevelManager().raiseUserLevel(user.getScore(), user.getLevelId());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void updateUserLevel(final String levelId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                databaseReferenceUser.child(user.getUserId()).child("levelId").setValue(levelId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void addHabitToUserPlanning(ArrayList<String> times, String habitId) {
        HabitFrequencyTiming habitFrequencyTiming = new HabitFrequencyTiming(null,
                times, habitId);
        String habitFrequencyTimingId = new HabitFrequencyTimingManager()
                .addHabitFrequencyTimingToDb(habitFrequencyTiming);
        new PlanningManager().updatePlanning(habitFrequencyTimingId);
    }

    public String getCurrentUserIdFromDb() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}

//new UserManager().getUserFromDb(new MyCallback<User>() {
//@Override
//public void onCallback(User element) {
//        textView_gold_coins.setText(element.getScore().toString());
//        }
//        });