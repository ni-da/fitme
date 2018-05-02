package com.example.nidailyas.fitme;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.TimePicker;

public class ExecuteHabitActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_habit);
        Bundle bundle = getIntent().getExtras();
        String key = bundle.getString("itemKey");
        String value = bundle.getString("itemValue");

        TextView textView = findViewById(R.id.textView_hello);
        textView.setText(key+ ": "+value);
    }
}

