package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    RecyclerView records_list;
    EditText habitNameToFilter;
    ArrayList<Record> allRecords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        records_list = findViewById(R.id.records_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        records_list.setLayoutManager(layoutManager);


        habitNameToFilter = findViewById(R.id.editText_habitNameFilter);

        findViewById(R.id.button_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterdRecords(habitNameToFilter.getText().toString());
            }
        });

        showAllRecords();


    }

    private void showAllRecords() {
        new RecordManager().getUserRecordsFromDb(new MyCallback<ArrayList<Record>>() {
            @Override
            public void onCallback(final ArrayList<Record> records) {
                records_list.setAdapter(new RecordsAdapter(records));


                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Remove item from backing list here
                        int position = viewHolder.getAdapterPosition();

                        new RecordManager().removeRecordFromDb(records.get(position).getRecordId());
                        new RecordsAdapter(records).notifyDataSetChanged();
                    }
                });

                itemTouchHelper.attachToRecyclerView(records_list);
            }
        });
    }

    private void showFilterdRecords(String habitToFilter) {
        String formatedHabitName = formathabitName(habitToFilter);
        new RecordManager().getUserRecordsByHabitNameFromDb(new MyCallback<ArrayList<Record>>() {
            @Override
            public void onCallback(ArrayList<Record> records) {
                records_list.setAdapter(new RecordsAdapter(records));
            }
        }, formatedHabitName);
    }

    private String formathabitName(String habitName) {
        habitName = habitName.toLowerCase();
        habitName = habitName.substring(0, 1).
                toUpperCase() + habitName.substring(1);
        return habitName;
    }
}
