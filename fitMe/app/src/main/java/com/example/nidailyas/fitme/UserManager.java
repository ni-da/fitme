package com.example.nidailyas.fitme;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {
    DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");

    public void getUserFromDb(final MyCallback<User> myCallback) {
        databaseReferenceUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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

    public void updateUserScore(final Long score){
        getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User element) {
                Long newScore = element.getScore()+score;
                databaseReferenceUser.child(element.getUserId()).child("score").setValue(newScore);
            }
        });
    }
}

//new UserManager().getUserFromDb(new MyCallback<User>() {
//@Override
//public void onCallback(User element) {
//        textView_gold_coins.setText(element.getScore().toString());
//        }
//        });