package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    ViewGroup activity_main2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity_main2 = findViewById(R.id.activity_main2);


        final Intent intent = getIntent();
        final RecyclerView tasksList = (RecyclerView) findViewById(R.id.tasks_list);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tasksList.setLayoutManager(layoutManager);
        tasksList.addOnItemTouchListener(new RecyclerItemClickListener(Main2Activity.this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
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

        final TextView textView_gold_coins = findViewById(R.id.textView_gold_coins);//getUserFromDb
        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User element) {
                textView_gold_coins.setText(element.getScore().toString());
            }
        });
//        textView_gold_coins.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImageView imageView = findViewById(R.id.imageView_settings);
//                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.activity_main2));
//
//                // position: LinearLayout
//                LinearLayout.LayoutParams pos = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                pos.gravity = Gravity.TOP;
//                pos.gravity = Gravity.LEFT;
//                imageView.setLayoutParams(pos);
//            }
//        });

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


}