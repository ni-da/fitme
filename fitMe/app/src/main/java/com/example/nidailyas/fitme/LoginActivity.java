package com.example.nidailyas.fitme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressBar progressBarLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase
        firebaseAuth = FirebaseAuth.getInstance();

//        //check if user is already loged in
//        if (firebaseAuth.getCurrentUser() != null){
//            // user is loged in --> start direct profile activity
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }



        buttonSignIn = findViewById(R.id.button_signIn);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        textViewSignUp = findViewById(R.id.textView_signup);
        progressBarLogin = findViewById(R.id.progressBar_login);


        // attach listener
        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // validations
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            //email or password is empty
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        //if validations are ok
        // show progressbar
        progressBarLogin.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarLogin.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            // start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }else if(view == textViewSignUp){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
