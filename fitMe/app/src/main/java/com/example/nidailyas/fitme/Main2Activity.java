package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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
    //ArrayList<Pair> items = new ArrayList<>();
    ArrayList<Habit> habits = new ArrayList<>();
    ArrayList<String> habitIds = new ArrayList<>();

    ViewGroup activity_main2;
    TextView circle_level;
    TextView textView_signatureName;
    TextView textView_gold_coins_count;
    RecyclerView tasksList;
    TextView textView_gold_coins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity_main2 = findViewById(R.id.activity_main2);
        tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tasksList.setLayoutManager(layoutManager);
        tasksList.addOnItemTouchListener(new RecyclerItemClickListener(Main2Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ExecuteHabitActivity.class);
                intent.putExtra("habitId", habitIds.get(position));

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
        findViewById(R.id.button_records).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecordsActivity.class));
                //startActivity(new Intent(getApplicationContext(), WeightActivity.class));
            }
        });
        updateHeader();


    }

    private void updateHeader() {
        circle_level = findViewById(R.id.circle_level);
        textView_signatureName = findViewById(R.id.textView_signatureName);
        textView_gold_coins = findViewById(R.id.textView_gold_coins);
        textView_gold_coins_count = findViewById(R.id.textView_gold_coins_count);

        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User user) {
                textView_gold_coins.setText(user.getScore().toString());
                circle_level.setText(user.getLevelId());
                new LevelManager().getLevelFromDb(new MyCallback<Level>() {
                    @Override
                    public void onCallback(Level level) {
                        textView_signatureName.setText(level.getSignatureName());
                    }
                }, user.getLevelId());
                new LevelManager().getScoreToNextLevel(new MyCallback<Long>() {
                    @Override
                    public void onCallback(Long element) {
                        textView_gold_coins_count.setText(Long.toString(element) + "/300");
                    }
                }, user.getLevelId(), user.getScore());

            }
        });
    }


    private void showHabits(final RecyclerView tasksList) {
//        new HabitManager().getUserHabits(new MyCallback<ArrayList<Habit>>() {
//            @Override
//            public void onCallback(ArrayList<Habit> habits) {
//                tasksList.setAdapter(new taskAdapter(habits));
//                for (Habit habit : habits) {
//                    habitIds.add(habit.getHabitId());
//                }
//            }
//        });

        String userId = new UserManager().getCurrentUserIdFromDb();
        // Toast.makeText(Main2Activity.this, "Userid: " + userId, Toast.LENGTH_SHORT).show();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            String planningId = user.planningId;
                            mDatabase.child("plannings").child(planningId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Planning planning = dataSnapshot.getValue(Planning.class);
                                    final ArrayList<String> habitsFreqIds = planning.getHabitFrequencies();
                                    for (String object : habitsFreqIds) {
                                        mDatabase.child("habitFrequencyTimings").child(object).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                HabitFrequencyTiming habitFrequencyTiming = dataSnapshot.getValue(HabitFrequencyTiming.class);

                                                mDatabase.child("habits").child(habitFrequencyTiming.getHabitId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Habit habit = dataSnapshot.getValue(Habit.class);
                                                        habitIds.add(habit.getHabitId());
                                                        habits.add(habit);
                                                        tasksList.setAdapter(new taskAdapter(habits));

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


}