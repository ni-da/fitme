package com.example.nidailyas.fitme;

import android.util.Log;

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

    public void updateUserScore(final String result, String habitId, int priority) {
        //todo: add bonus scoring!
        final int routineScore = 20;
        final int priorityBonus = 10;
        final int idealValueBonus = 40;
        final int[] totalScore = {20};

        Log.w("ResultValue: ", result);

        // - ideal value
        switch (habitId) {
            case "-LC9ugzpF6S_Gvx5VL_M": //weight
                final double weightResult = Double.valueOf(result);
                getUserFromDb(new MyCallback<User>() {
                    @Override
                    public void onCallback(User user) {
                        // toDo: calculate BMI
                        if (weightResult == user.getWeight()+2 ||
                                weightResult  == user.getWeight()-2 ||
                                weightResult == user.getWeight()) {
                            Log.w("Ideal weight!", Integer.toString(totalScore[0]));
                            totalScore[0] += idealValueBonus;
                            Log.w("Ideal score!", Integer.toString(totalScore[0]));
                        }
                    }
                });
                break;
            case "habitId3": //bp
                String bpResult[]= result.split(";");
                String bp1 = bpResult[0];
                String bp2 = bpResult[1];

                final double upperBpResult = Double.valueOf(bp1),
                        lowerBpResult = Double.valueOf(bp2);
                getUserFromDb(new MyCallback<User>() {
                    @Override
                    public void onCallback(User user) {
                        if ((upperBpResult == user.getBegin_bp_upper()) &&
                                (lowerBpResult == user.getBegin_bp_lower())) {
                            totalScore[0] += idealValueBonus;
                            Log.w("Ideal score!", Integer.toString(totalScore[0]));
                        }
                    }
                });
                break;
        }
        // - priority
        totalScore[0] = (0-priorityBonus) * priority + 60;


        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Long newScore = user.getScore() + totalScore[0];

                databaseReferenceUser.child(user.getUserId()).child("score").setValue(newScore);
                new LevelManager().raiseUserLevel(user.getScore(), totalScore[0], user.getLevelId());
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


    public void addHabitToUserPlanning(ArrayList<String> times, String habitId, int habit_Priority) {
        HabitFrequencyTiming habitFrequencyTiming = new HabitFrequencyTiming
                (null,
                        times, habitId, habit_Priority);
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