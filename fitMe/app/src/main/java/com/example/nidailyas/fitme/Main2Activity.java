package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        RecyclerView tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tasksList.setLayoutManager(layoutManager);
        String[] languages = {"JAVA", "KOTLIN", "PHP", "C", "C++", "C#", "PYTHON"};
        tasksList.setAdapter(new taskAdapter(languages));

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
    }

}