package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class ExecuteHabitActivity extends Main2Activity {
    TextView circle_level;
    TextView textView_signatureName;
    TextView textView_gold_coins;
    TextView textView_gold_coins_count;
    TextView textView_habitTitle;
    TextView textView_habitDesc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_habit);
        Bundle bundle = getIntent().getExtras();
//        final String key = bundle.getString("itemKey");
//        String value = bundle.getString("itemValue");

        updateHeader();
        if (bundle != null) {
            showHabitInfo(bundle.getString("habitId"));
        }


        textView_habitTitle = findViewById(R.id.textView_habitTitle);
        //textView_habitTitle.setText(key);
        textView_habitDesc = findViewById(R.id.textView_habitDesc);
        //textView_habitDesc.setText(value);
        Button button_startHabit = findViewById(R.id.button_startHabit);

        button_startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeHabit();

                //ImageView imageView_smiely = findViewById(R.id.imageView_smiely);
                //imageView_smiely.setVisibility(View.VISIBLE);
//                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.activity_execute_habit));
//
//                // position: LinearLayout
//                LinearLayout.LayoutParams pos = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                pos.gravity = Gravity.TOP;
//                //pos.gravity = Gravity.LEFT;
//                imageView_smiely.setLayoutParams(pos);

            }
        });

    }

    public void executeHabit() {
        findViewById(R.id.textView_addingScor50).setVisibility(View.VISIBLE);
        new UserManager().updateUserScore((long) 50);
        Date today = new Date();
        Record record = new Record(null, "Done", today,
                FirebaseAuth.getInstance().getCurrentUser().getUid(), "habitId");
        new RecordManager().addRecordTodb(record);
    }

    public void showHabitInfo(String habitId) {
        new HabitManager().getHabitByIdFromDb(new MyCallback<Habit>() {
            @Override
            public void onCallback(Habit element) {
                textView_habitTitle.setText(element.getHabitName());
                textView_habitDesc.setText(element.getDescription());
            }
        }, habitId);
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
}

