package com.example.nidailyas.fitme;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class ExecuteHabitActivity extends Main2Activity
        implements SensorEventListener {
    private TextView circle_level;
    private TextView textView_signatureName;
    private TextView textView_gold_coins;
    private TextView textView_gold_coins_count;
    private TextView textView_habitTitle;
    private TextView textView_habitDesc;
    private String habitId;
    private Button button_startHabit;
    private int steps;
    private int value = -1;


    private TextView textView_steps;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    private EditText editText_executeHabit_weight;
    private Button button_withings;
    private TextView textView_withingsWeightResult;
    private EditText editText_executeHabit_water;
    private LinearLayout linLayout_executeHabit_bp;
    private EditText editText_executeHabit_bp_U;
    private EditText editText_executeHabit_bp_L;
    private EditText editText_executeHabit_run_km;
    private EditText editText_executeHabit_run_min;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_habit);
        Bundle bundle = getIntent().getExtras();
        updateHeader();
        if (bundle != null) {
            habitId = bundle.getString("habitId");
            showHabitInfo(habitId);
        }


        textView_steps = findViewById(R.id.textView_steps);

        mSensorManager = (SensorManager)
                getSystemService(getApplicationContext().SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


        textView_habitTitle = findViewById(R.id.textView_habitTitle);
        textView_habitDesc = findViewById(R.id.textView_habitDesc);


        editText_executeHabit_weight = findViewById(R.id.editText_executeHabit_weight);
        button_withings = findViewById(R.id.button_withings);
        textView_withingsWeightResult = findViewById(R.id.textView_withingsWeightResult);
        editText_executeHabit_water = findViewById(R.id.editText_executeHabit_water);

        linLayout_executeHabit_bp = findViewById(R.id.linLayout_executeHabit_bp);
        editText_executeHabit_bp_U = findViewById(R.id.editText_executeHabit_bp_U);
        editText_executeHabit_bp_L = findViewById(R.id.editText_executeHabit_bp_L);
        editText_executeHabit_run_km = findViewById(R.id.editText_executeHabit_run_km);
        editText_executeHabit_run_min = findViewById(R.id.editText_executeHabit_run_min);


        button_startHabit = findViewById(R.id.button_startHabit);
        button_startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeHabit();
                //todo:   show textview; what will the user earn by doing this habit!
                // a textviw is added.
            }
        });
        button_withings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WithingWeightApiManager(ExecuteHabitActivity.this).setConnection(new MyCallback<Double>() {
                    @Override
                    public void onCallback(Double res) {
                        Log.w("NICE: ", Double.toString(res));
                        textView_withingsWeightResult.setText(Double.toString(res));
                        textView_withingsWeightResult.setVisibility(View.VISIBLE);
                        button_withings.setEnabled(false);
                    }
                });
            }
        });

    }

    public void executeHabit() {
        button_startHabit.setEnabled(false);
        String result = null;
        String resultValue = null;
        findViewById(R.id.animation_view_star).setVisibility(View.VISIBLE);
        Date today = new Date();
        switch (habitId) {
            case "habitId2": //run
                result = editText_executeHabit_run_min.getText().toString() +
                        " min;" + editText_executeHabit_run_km.getText().toString() + " km";
                break;
            case "habitId1": // walk
                result = editText_executeHabit_run_min.getText().toString() + " min;"
                        + editText_executeHabit_run_km.getText().toString() + " km;"
                        + String.valueOf(steps) + " steps";
                break;
            case "-LC9ugzpF6S_Gvx5VL_M": //weight
                result = editText_executeHabit_weight.getText().toString() + " kg";
                resultValue = editText_executeHabit_weight.getText().toString();

                break;
            case "habitId3": //bp
                result = editText_executeHabit_bp_U.getText().toString() + ";" +
                        editText_executeHabit_bp_L.getText().toString();
                resultValue = result;
                break;
            case "-LC9ytpajxQIBsna4E-p": // drink
                result = editText_executeHabit_water.getText().toString() + " liter";
                break;
            default:
        }
        final String finalResultValue = resultValue;

//        new UserManager().updateUserScore(finalResultValue, habitId,
//                new HabitManager().gethabitPriorityByHabitId(habitId));

        new UserManager().updateUserScore(finalResultValue, habitId,
                1);

        Record record = new Record(null, result, today,
                new UserManager().getCurrentUserIdFromDb(), habitId);
        new RecordManager().addRecordTodb(record);
    }

    public void showHabitInfo(final String habitId) {
        new HabitManager().getHabitByIdFromDb(new MyCallback<Habit>() {
            @Override
            public void onCallback(Habit habit) {
                textView_habitTitle.setText(habit.getHabitName());
                textView_habitDesc.setText(habit.getDescription());
                String habitName = habit.getHabitName();
                switch (habitName) {
                    case "Run":
                        editText_executeHabit_run_km.setVisibility(View.VISIBLE);
                        editText_executeHabit_run_min.setVisibility(View.VISIBLE);
                        break;
                    case "Walk":
                        editText_executeHabit_run_km.setVisibility(View.VISIBLE);
                        editText_executeHabit_run_min.setVisibility(View.VISIBLE);
                        textView_steps.setVisibility(View.VISIBLE);
                        break;
                    case "Weight":
                        editText_executeHabit_weight.setVisibility(View.VISIBLE);
                        button_withings.setVisibility(View.VISIBLE);
                        //textView_withingsWeightResult.setVisibility(View.VISIBLE);
                        break;
                    case "Bp":
                        linLayout_executeHabit_bp.setVisibility(View.VISIBLE);
                        break;
                    case "Drink water":
                        editText_executeHabit_water.setVisibility(View.VISIBLE);
                        break;

                }

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
                new LevelManager().getScoreEarnedToIncreaseNextLevel(new MyCallback<String>() {
                    @Override
                    public void onCallback(String element) {
                        textView_gold_coins_count.setText(element);
                    }
                }, user.getLevelId(), user.getScore());

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] values = sensorEvent.values;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps = value;
            textView_steps.setText("Step Counter Detected : " + value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            textView_steps.setText("Step Detector Detected : " + value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,

                SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }
}
