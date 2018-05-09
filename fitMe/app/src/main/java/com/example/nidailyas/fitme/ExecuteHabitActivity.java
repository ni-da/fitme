package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExecuteHabitActivity extends Main2Activity {
    TextView circle_level;
    TextView textView_signatureName;
    TextView textView_gold_coins;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_habit);
        Bundle bundle = getIntent().getExtras();
        final String key = bundle.getString("itemKey");
        String value = bundle.getString("itemValue");
        textView_gold_coins = findViewById(R.id.textView_gold_coins);//getUserFromDb
        circle_level = findViewById(R.id.circle_level);
        textView_signatureName = findViewById(R.id.textView_signatureName);

        new UserManager().getUserFromDb(new MyCallback<User>() {
            @Override
            public void onCallback(User user) {
                textView_gold_coins.setText(user.getScore().toString());
                circle_level.setText(user.getLevelId());
                new LevelManager().getLevelFromDb(new MyCallback<Level>() {
                    @Override
                    public void onCallback(Level element) {
                        textView_signatureName.setText(element.getSignatureName());
                    }
                }, user.getLevelId());

            }
        });

        TextView textView_habitTitle = findViewById(R.id.textView_habitTitle);
        textView_habitTitle.setText(key);
        TextView textView_habitDesc = findViewById(R.id.textView_habitDesc);
        textView_habitDesc.setText(value);
        Button button_startHabit = findViewById(R.id.button_startHabit);
        button_startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExecuteHabitActivity.this, key + " is mark as done!", Toast.LENGTH_SHORT).show();
            }
        });
        //button_startHabit.setText("Mark done!"+key);
        button_startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScore();

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

    public void updateScore() {
        findViewById(R.id.textView_addingScor50).setVisibility(View.VISIBLE);
        new UserManager().updateUserScore((long) 50);
    }
}

