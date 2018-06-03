package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private final ArrayList<String> habitIdss = new ArrayList<>();
    private TextView circle_level;
    private TextView textView_signatureName;
    private TextView textView_gold_coins_count;
    private TextView textView_gold_coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


        ViewGroup activity_main2 = findViewById(R.id.activity_main2);
        RecyclerView tasksList = findViewById(R.id.tasks_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        tasksList.setLayoutManager(layoutManager);

        tasksList.addOnItemTouchListener(new RecyclerItemClickListener
                (Main2Activity.this,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(),
                                        ExecuteHabitActivity.class);
                                intent.putExtra("habitId",
                                        habitIdss.get(position));

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
                new LevelManager().getScoreEarnedToIncreaseNextLevel(new MyCallback<String>() {
                    @Override
                    public void onCallback(String element) {
                        textView_gold_coins_count.setText(element);
                    }
                }, user.getLevelId(), user.getScore());

            }
        });
    }


    private void showHabits(final RecyclerView tasksList) {
        new HabitManager().getUserHabits(new MyCallback<ArrayList<Habit>>() {
            @Override
            public void onCallback(ArrayList<Habit> habits) {
                tasksList.setAdapter(new taskAdapter(habits));
                for (Habit habit : habits) {
                    if (!habitIdss.contains(habit.getHabitId())) {
                        habitIdss.add(habit.getHabitId());
                    }

                }
            }
        });
    }
}