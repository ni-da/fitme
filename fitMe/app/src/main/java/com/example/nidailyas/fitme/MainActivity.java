package com.example.nidailyas.fitme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.fitMe.MESSAGE";
    private static final int CHOOSE_IMAGE = 101;
    Uri uriProfileImage;
    String profileImageUrl;


    // objects for calender
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    //view objects
    private Toolbar toolbar;
    private TextView dateView;
    private EditText editText_name;
    private TextView textView_email;
    private Button button_logout;
    private Button button_save;
    private Button button_reset;
    private ImageView imageView_profile;
    private ProgressBar progressBar_profile;

    //auth 1. Declare an instance of FirebaseAuth
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


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView_email.setText(user.getEmail());

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
                saveUserInfo();
            }
        });
        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        loadUserInformation();
    }

    private void loadUserInformation() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                // load image by gvn url
                // add glide lib -- in gradle files
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView_profile);
            }else {
                imageView_profile.setImageResource(R.drawable.camera);
            }
            if (user.getDisplayName() != null) {
                String name = user.getDisplayName();
                editText_name.setText(user.getDisplayName());
            }


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
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

    private void saveUserInfo() {
        Toast.makeText(this, "u clicked", Toast.LENGTH_SHORT).show();
        String name = editText_name.getText().toString();
//        Date dateOfBirth;
//        char gender;
//        double weight;
//        double height;

        // check if name is empty
        if (name.isEmpty()) {
            editText_name.setError("Name required");
            editText_name.requestFocus();
            return;
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profileChangeRequest =
                    new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(Uri.parse(profileImageUrl)).build();

            user.updateProfile(profileChangeRequest).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
//        User userInfo = new User(name);
//        //using uniqe id of loged in user
//        String id = databaseReference.push().getKey();
//        databaseReference.child(id).setValue(userInfo);
//        Toast.makeText(this, "Info saved", Toast.LENGTH_SHORT).show();
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

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile image"),
                CHOOSE_IMAGE);
    }
}
