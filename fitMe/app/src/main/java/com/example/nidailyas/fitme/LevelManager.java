package com.example.nidailyas.fitme;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LevelManager {
    DatabaseReference databaseReferenceLevel = FirebaseDatabase.getInstance().getReference("levels");

    public void getLevelFromDb(final MyCallback<Level> myCallback, String levelId) {
        databaseReferenceLevel.child(levelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Level level = dataSnapshot.getValue(Level.class);
                    myCallback.onCallback(level);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void raiseUserLevel(final Long score, final String levelId) {
        getLevelFromDb(new MyCallback<Level>() {
            @Override
            public void onCallback(Level level) {
                if (score + 50 >= level.getMaxScore()) {
                    //verhoogLevel
                    int lvl = (Integer.parseInt(levelId)) + 1;
                    new UserManager().updateUserLevel(Integer.toString(lvl));
                    Log.w("LEVELLLLL: ", Integer.toString(lvl));
                }
            }
        }, levelId);
    }


    /**
     * returns a callback that includes the score that the user still needs to get to the next level.
     *
     * @param myCallback
     * @param levelId
     * @param score
     */
    public void getScoreToNextLevel(final MyCallback<Long> myCallback, String levelId, final Long score) {
        databaseReferenceLevel.child(levelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Level level = dataSnapshot.getValue(Level.class);
                    long scoreToGO = level.getMaxScore() - score;
                    myCallback.onCallback(scoreToGO);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * returns a callback that includes the score that the user have earned to increase to the next level.
     *
     * @param myCallback
     * @param levelId
     * @param score
     */
    public void getScoreEarnedToIncreaseNextLevel(final MyCallback<String> myCallback, String levelId, final Long score) {
        databaseReferenceLevel.child(levelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Level level = dataSnapshot.getValue(Level.class);
                    long scoreToGO = level.getMaxScore() - score;
                    long scoreEarned = score -  level.getMinScore();
                    myCallback.onCallback(Long.toString(scoreEarned) + "/" + Long.toString((level.getMaxScore() - level.getMinScore())));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
