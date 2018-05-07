package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    LinearLayoutManager layoutManager;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<Pair> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Intent intent = getIntent();
        final String name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final RecyclerView tasksList = (RecyclerView) findViewById(R.id.tasks_list);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tasksList.setLayoutManager(layoutManager);
        tasksList.addOnItemTouchListener(new RecyclerItemClickListener(Main2Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(Main2Activity.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ExecuteHabitActivity.class);
                intent.putExtra("itemKey", items.get(position).getKey());
                intent.putExtra("itemValue", items.get(position).getValue());
                startActivity(intent);
            }
        }));
        showHabits(tasksList);

        findViewById(R.id.imageView_profile_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        findViewById(R.id.imageView_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlanningActivity.class));
            }
        });
        findViewById(R.id.button_addDummyData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SavingDummyData.class));
            }
        });

        User user = new UserManager().getUserFromDb();
        TextView textView_gold_coins = findViewById(R.id.textView_gold_coins);//getUserFromDb
        //Log.w("USERRRRR: ", user.gender);
        new UserManager().getUserScore();
        //textView_gold_coins.setText(new UserManager().getUserScore().toString());

        //textView_gold_coins.setText("Ok");
    }

    private void showHabits(final RecyclerView tasksList) {
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            String planningId = user.planningId;
                            //Toast.makeText(Main2Activity.this, "Thissss: " +  user.planningId, Toast.LENGTH_SHORT).show();
                            mDatabase.child("plannings").child(planningId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Planning planning = dataSnapshot.getValue(Planning.class);
                                    //Toast.makeText(Main2Activity.this, "Thissss: " +  planning.getHabitFrequencies(), Toast.LENGTH_SHORT).show();
                                    ArrayList<String> habitsFreqIds = planning.getHabitFrequencies();
                                    //tasksList.setAdapter(new taskAdapter(habitsFreqIds));

                                    for (String object : habitsFreqIds) {
                                        mDatabase.child("habitFrequencyTimings").child(object).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                HabitFrequencyTiming habitFrequencyTiming = dataSnapshot.getValue(HabitFrequencyTiming.class);

                                                mDatabase.child("habits").child(habitFrequencyTiming.getHabitId()).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Habit habit = dataSnapshot.getValue(Habit.class);
                                                        items.add(new Pair(habit.getHabitName(), habit.getDescription()));
                                                        tasksList.setAdapter(new taskAdapter(Main2Activity.this, items));
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

//    private void showScores() {
//        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            User user = dataSnapshot.getValue(User.class);
//                            String score = user.getScore();


}