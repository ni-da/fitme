package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class PlanningActivity extends AppCompatActivity {
    private final ArrayList<String> habitIdss = new ArrayList<>();
    private final ArrayList<String> times = new ArrayList<>();
    private EditText editText_habitName;
    private EditText editText_habitDetail;
    private TimePicker timePicker;
    private int notifyHour;
    private int notifyMin;
    private Boolean customHabit;
    private String selectedHabitId;
    private String notifyHabitId;
    private TextView textView_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        final Spinner spinner_habits = findViewById(R.id.spinner_habits);

        ////////////////////// The seekbar for setting the priority ////////////////////////////////
        final SeekBar seekBar_priority = findViewById(R.id.seekBar_priority);
        final int[] seekbar_progress = {seekBar_priority.getProgress()+1};
        textView_priority = findViewById(R.id.textView_priority);
        textView_priority.setText("Priority: " + seekbar_progress[0]);
        seekBar_priority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // updated continuously as the user slides the thumb
                i++;
                textView_priority.setText("Progress: " + i);
                seekbar_progress[0] = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Priority set to: " + seekbar_progress[0],
                        Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView tasksList = findViewById(R.id.tasks_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        tasksList.setLayoutManager(layoutManager);
        tasksList.addOnItemTouchListener(new RecyclerItemClickListener
                (PlanningActivity.this,
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
        findViewById(R.id.button_showAddHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.habits_overview).setVisibility(View.GONE);
                findViewById(R.id.add_habit_content).setVisibility(View.VISIBLE);

            }
        });

        editText_habitName = findViewById(R.id.editText_habitName);
        editText_habitDetail = findViewById(R.id.editText_habitDetail);
        timePicker = findViewById(R.id.timePicker1);
        notifyHour = timePicker.getCurrentHour();
        notifyMin = timePicker.getCurrentMinute();


        new HabitManager().getHabitNamesFromDb(new MyCallback<ArrayList<String>>() {
            @Override
            public void onCallback(final ArrayList<String> element) {
                final String[] habitNamesArray = element.toArray(new String[element.size()]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PlanningActivity.this,
                        android.R.layout.simple_list_item_1, habitNamesArray);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_habits.setAdapter(arrayAdapter);

                spinner_habits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == element.size() - 1) {
                            customHabit = true;

                            editText_habitName.getText().clear();
                            editText_habitDetail.getText().clear();

                            editText_habitName.setInputType(InputType.TYPE_CLASS_TEXT);
                            editText_habitDetail.setInputType(InputType.TYPE_CLASS_TEXT);
                        } else {
                            customHabit = false;
                            new HabitManager().getHabitByNameFromDb(new MyCallback<Habit>() {
                                @Override
                                public void onCallback(final Habit element) {
                                    editText_habitName.setText(element.getHabitName());
                                    editText_habitName.setInputType(InputType.TYPE_NULL);
                                    editText_habitDetail.setText(element.getDescription());
                                    editText_habitDetail.setInputType(InputType.TYPE_NULL);
                                    selectedHabitId = element.getHabitId();
                                }
                            }, habitNamesArray[i]);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        findViewById(R.id.button_saveHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customHabit) {
                    if (checkEditTextValidation(editText_habitName) && checkEditTextValidation(editText_habitDetail)) {
                        String newAddedHabitId = addNewHabitToDb
                                (editText_habitName.getText().toString(),
                                        editText_habitDetail.getText().toString());
                        addHabitToUserProfile(times, newAddedHabitId);
                        //notifyHabitId = newAddedHabitId;
                    }
                } else {
                    addHabitToUserProfile(times, selectedHabitId);
                    //notifyHabitId = selectedHabitId;
                }
                //new HabitNotification().registerNotification(notifyHabitId, times, notifyHour, notifyMin, getApplicationContext());

            }
        });


        findViewById(R.id.button_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HabitNotification().registerNotification1(notifyHabitId, notifyHour, notifyMin, getApplicationContext());
            }
        });

        findViewById(R.id.button_addTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeString = Integer.toString(timePicker.getCurrentHour()) + ":" +
                        Integer.toString(timePicker.getCurrentMinute());
                LinearLayout linearLayout = findViewById(R.id.LinearLayout_habitTimes);
                TextView time = new TextView(getApplicationContext());
                times.add(timeString);
                time.setText(timeString);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                time.setLayoutParams(params);
                linearLayout.addView(time);
            }
        });


    }


    private void addHabitToUserProfile(ArrayList<String> times, String habitId) {
        new UserManager().addHabitToUserPlanning(times, habitId);
    }

    private Boolean checkEditTextValidation(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("Name required");
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private String addNewHabitToDb(String habitName, String description) {
        Habit habit = new Habit(null, habitName, description);
        return new HabitManager().addNewHabitToDb(habit);
    }

    private void showHabits(final RecyclerView tasksList) {
        new HabitManager().getUserHabits(new MyCallback<ArrayList<Habit>>() {
            @Override
            public void onCallback(ArrayList<Habit> habits) {
                tasksList.setAdapter(new habitAdapter(habits));
                for (Habit habit : habits) {
                    if (!habitIdss.contains(habit.getHabitId())) {
                        habitIdss.add(habit.getHabitId());
                    }

                }
            }
        });
    }


}
