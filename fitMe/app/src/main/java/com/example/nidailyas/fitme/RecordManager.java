package com.example.nidailyas.fitme;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecordManager {

    DatabaseReference databaseReferenceRecord = FirebaseDatabase.getInstance().getReference("records");

    public void getRecordByIdFromDb(final MyCallback<Record> myCallback, String recordId) {
        databaseReferenceRecord.child(recordId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Record record = dataSnapshot.getValue(Record.class);
                    myCallback.onCallback(record);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void getUserRecordsFromDb(final MyCallback<ArrayList<Record>> myCallback) {
        final ArrayList<Record> records = new ArrayList<Record>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        databaseReferenceRecord.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Record record = snapshot.getValue(Record.class);
                    if (record.getUserId()
                            .equals(user.getUid())) {
                        records.add(record);
                    }
                }
                myCallback.onCallback(records);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void addRecordTodb(Record record) {
        String recordId = databaseReferenceRecord.push().getKey();
        record.recordId = recordId;
        databaseReferenceRecord.setValue(record);
    }


}
