package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    DatabaseReference databaseReferenceRecord = FirebaseDatabase.getInstance().getReference("records");
    RecyclerView records_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        records_list = findViewById(R.id.records_list);
        records_list.setLayoutManager(new LinearLayoutManager(this));

        //ArrayList<Record> data = new ArrayList<>();
//        data.add("a");
//        data.add("f");
//        data.add("d");
//        data.add("e");
//        data.add("r");
//        data.add("p");

        //records_list.setAdapter(new RecordsAdapter(data));
        new RecordManager().getUserRecordsFromDb(new MyCallback<ArrayList<Record>>() {
            @Override
            public void onCallback(ArrayList<Record> records) {
                records_list.setAdapter(new RecordsAdapter(records));

//                for (Record record : records) {
//                    //Toast.makeText(getApplicationContext(), record.getRecordId(), Toast.LENGTH_SHORT).show();
//
//                }
            }
        });


    }
}
