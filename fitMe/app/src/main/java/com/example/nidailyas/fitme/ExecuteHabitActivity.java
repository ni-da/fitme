package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ExecuteHabitActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_habit);
        Bundle bundle = getIntent().getExtras();
        final String key = bundle.getString("itemKey");
        String value = bundle.getString("itemValue");

        TextView textView_habitTitle = findViewById(R.id.textView_habitTitle);
        textView_habitTitle.setText(key);
        TextView textView_habitDesc = findViewById(R.id.textView_habitDesc);
        textView_habitDesc.setText(value);

        Button button_startHabit = findViewById(R.id.button_startHabit);
        button_startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExecuteHabitActivity.this, key+" is mark as done!",Toast.LENGTH_SHORT).show();
            }
        });
        //button_startHabit.setText("Mark done!"+key);
    }
}

