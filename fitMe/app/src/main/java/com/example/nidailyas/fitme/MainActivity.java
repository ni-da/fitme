package com.example.nidailyas.fitme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.fitMe.MESSAGE";
    public static final String TAG = "MainActivity";

    private static final int CHOOSE_IMAGE = 101;
    Uri uriProfileImage;
    String profileImageUrl;
    String name, gender;
    double weight, height, begin_bp_upper, begin_bp_lower;
    Calendar dateOfBirth;
    // objects for calender
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    //view objects
    private Toolbar toolbar;
    private RadioGroup radioGroup_gender;
    private ImageView imageView_profile;
    private ProgressBar progressBar_profile;
    private TextView dateView, textView_email;
    private Button button_logout, button_save, button_reset;
    private EditText editText_name, editText_weight, editText_height, editText_bp_U, editText_bp_L;

    //auth 1. Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //auth 2. initialize the FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();


        // auth 3. check to see if the user is currently signed in.
        if (firebaseAuth.getCurrentUser() == null) {
            // user is not loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        //get loged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        dateView = findViewById(R.id.textView_dob);
        toolbar = findViewById(R.id.toolbar);
        button_reset = findViewById(R.id.button_reset);
        editText_name = findViewById(R.id.editText_name);
        textView_email = findViewById(R.id.textView_email);
        button_logout = findViewById(R.id.button_logout);
        button_save = findViewById(R.id.button_save);
        imageView_profile = findViewById(R.id.imageView_profile);
        progressBar_profile = findViewById(R.id.progressBar_profile);
        radioGroup_gender = findViewById(R.id.radioGroup_gender);
        editText_height = findViewById(R.id.editText_height);
        editText_weight = findViewById(R.id.editText_weight);
        editText_bp_U = findViewById(R.id.editText_bp_U);
        editText_bp_L = findViewById(R.id.editText_bp_L);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView_email.setText(user.getEmail());

        //todo: fix
//        loadUserInformation();

        // attch lister to buttons
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfoToDb();

                // to save whole profile
                name = editText_name.getText().toString();
                if (editText_height.getText() != null)
                    height = Double.parseDouble(editText_height.getText().toString());
                if (editText_weight.getText() != null)
                    weight = Double.parseDouble(editText_weight.getText().toString());
                if (editText_bp_U.getText() != null)
                    begin_bp_upper = Double.parseDouble(editText_bp_U.getText().toString());
                if (editText_bp_L.getText() != null)
                    begin_bp_lower = Double.parseDouble(editText_bp_L.getText().toString());
                getGender();
                saveUserInfoToDb();
            }
        });
        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });


        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateView.setText(date);
                dateOfBirth = Calendar.getInstance();
                dateOfBirth.set(Calendar.YEAR, year);
                dateOfBirth.set(Calendar.MONTH, month);
                dateOfBirth.set(Calendar.DAY_OF_MONTH, day);
            }
        };
    }

    public void saveUserInfoToDb() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        User user = new User(currentUser.getUid().toString(), name, dateOfBirth.getTime(),
                gender, weight, height, begin_bp_upper, begin_bp_lower,
                2L, "abc123", "abc1234", profileImageUrl);

        databaseReference.child(currentUser.getUid().toString()).setValue(user);
        Toast.makeText(this, "All set", Toast.LENGTH_SHORT).show();
    }

    public void getGender() {
        int index = radioGroup_gender.getCheckedRadioButtonId();
        if (index == -1) {
            // no item slected
            Toast.makeText(this, "No gender selected.", Toast.LENGTH_SHORT).show();
        }
        if (index == R.id.radio_female) {
            gender = "F";
            Toast.makeText(this, "Femail gender selected.", Toast.LENGTH_SHORT).show();
        }
        if (index == R.id.radio_male) {
            gender = "M";
            Toast.makeText(this, "Mail gender selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
//                                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                                User user = dataSnapshot.getValue(User.class);
                                editText_name.setText(user.getName());
                                editText_weight.setText(Double.toString(user.getWeight()));
                                editText_height.setText(Double.toString(user.getHeight()));
                                editText_bp_L.setText(Double.toString(user.getBegin_bp_lower()));
                                editText_bp_U.setText(Double.toString(user.getBegin_bp_upper()));
                                dateView.setText(user.getDateOfBirth().toString());
                                // setting up gender radios
                                if (user.getGender().equals("M")) {
                                    radioGroup_gender.check(R.id.radio_male);
                                } else if (user.getGender().equals("F")) {
                                    radioGroup_gender.check(R.id.radio_female);
                                }

                                if (user.getProfileImageUrl() != null &&
                                        !MainActivity.this.isFinishing()) {
                                    Glide.with(MainActivity.this)
                                            .load(user.getProfileImageUrl()).
                                            into(imageView_profile);
                                } else {
                                    imageView_profile.setImageResource(R.drawable.camera);
                                }

//                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        switch (id) {
//            case R.id.search_id:
//                Toast.makeText(this, "Search icon clicked!", Toast.LENGTH_SHORT).show();
//                break;
//            case android.R.id.home:
//                finish();
//        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void reset_all(View view) {
        editText_name.setText("");
    }

    public void sendForm(View view) {
        Intent intent_main_screen = new Intent(this, Main2Activity.class);
        intent_main_screen.putExtra("name", String.valueOf(editText_name));
        startActivity(intent_main_screen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                        uriProfileImage);
                imageView_profile.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile image"),
                CHOOSE_IMAGE);
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            // upload started
            progressBar_profile.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // upload is completed
                            progressBar_profile.setVisibility(View.GONE);
                            // Get a URL to the uploaded content
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                            updateProfileImageUrlInDb(profileImageUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // upload is not completed,  there is some prob
                    progressBar_profile.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateProfileImageUrlInDb(String profileImageUrl) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        Map<String, Object> profImageUpdate = new HashMap<>();
        profImageUpdate.put("profileImageUrl", profileImageUrl);
        databaseReference.child(currentUser.getUid()).updateChildren(profImageUpdate);
    }
}
