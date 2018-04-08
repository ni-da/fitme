package com.example.nidailyas.fitme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.example.fitMe.MESSAGE";
    Toolbar toolbar;
    // objects for calender
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    //view objects
    private TextView dateView;
    private EditText editText_name;
    private TextView textView_email;
    private Button button_logout;
    private Button button_save;
    private Button button_reset;

    // firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
//            arg1 = year;
//            arg2 = month;
//            arg3 = day;
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //check if user is already loged in
        if (firebaseAuth.getCurrentUser() == null) {
            // user is not loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        //get loged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();


        dateView = (TextView) findViewById(R.id.textView_dob);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month + 1, day);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button_reset = (Button) findViewById(R.id.button_reset);
        editText_name = findViewById(R.id.editText_name);

        textView_email = (TextView) findViewById(R.id.textView_email);
        button_logout = (Button) findViewById(R.id.button_logout);
        button_save = (Button) findViewById(R.id.button_save);


        textView_email.setText(user.getEmail());

        // attch lister to buttons
        button_logout.setOnClickListener(this);
        button_reset.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search_id:
                Toast.makeText(this, "Search icon clicked!", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_female:
                if (checked)

                    break;
            case R.id.radio_male:
                if (checked)

                    break;
        }
    }


    public void reset_all(View view) {
        editText_name.setText("");
    }

    public void sendForm(View view) {
        Intent intent_main_screen = new Intent(this, Main2Activity.class);
        intent_main_screen.putExtra("name", String.valueOf(editText_name));
        startActivity(intent_main_screen);
    }

    private void saveUserInfo(){
        String name = editText_name.getText().toString().trim();
//        Date dateOfBirth;
//        char gender;
//        double weight;
//        double height;
       User userInfo = new User(name);
       //using uniqe id of loged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        databaseReference.child(user.getUid()).setValue(userInfo);
        databaseReference.child(user.getUid()).setValue(userInfo);
        Toast.makeText(this, "Info saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == button_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (view == button_save){
            saveUserInfo();
        }
    }
}
