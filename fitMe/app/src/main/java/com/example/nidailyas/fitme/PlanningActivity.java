package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlanningActivity extends AppCompatActivity {
    Spinner spinner_habits;
    EditText editText_habitName;
    EditText getEditText_habitFrequency;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        firebaseAuth = FirebaseAuth.getInstance();

        // auth 3. check to see if the user is currently signed in.
        if (firebaseAuth.getCurrentUser() == null) {
            // user is not loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("habits");
        spinner_habits = findViewById(R.id.spinner_habits);
        editText_habitName = findViewById(R.id.editText_habitName);
        getEditText_habitFrequency = findViewById(R.id.editText_habitFrequency);

        final String[] languages = {"C++", "C#", "PYTHON", "Add new habit"};
//        setAdapter(new taskAdapter(languages));
//        ArrayAdapter <String> habitsAdapter = new ArrayAdapter
//                <String>(PlanningActivity.this,
//                android.R.layout.simple_list_item_1,
//                getResources().getStringArray(R.array.habit_names));

        ArrayAdapter<String> habitsAdapter = new ArrayAdapter<String>(PlanningActivity.this,
                android.R.layout.simple_spinner_dropdown_item, languages);

        habitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_habits.setAdapter(habitsAdapter);

        spinner_habits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PlanningActivity.this, spinner_habits.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (spinner_habits.getSelectedItem().toString() == languages[languages.length - 1]) {
//                    add_habit_content
                    findViewById(R.id.add_habit_content).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.add_habit_content).setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        findViewById(R.id.button_saveHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHabitToDb();
            }
        });


    }

    private void addHabitToDb() {
        String name = editText_habitName.getText().toString().trim();
        int frequency;
        if (!TextUtils.isEmpty(getEditText_habitFrequency.getText().toString())) {
            frequency = Integer.parseInt(getEditText_habitFrequency.getText().toString());
        } else {
            frequency = 1;
        }
        if (!TextUtils.isEmpty(name)) {
            String habitId = databaseReference.push().getKey();
            Habit habit = new Habit(habitId, name, frequency);
            databaseReference.child(habitId).setValue(habit);
            Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
        } else {
            editText_habitName.setError("Name required");
            editText_habitName.requestFocus();
        }
    }
}
