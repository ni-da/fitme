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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //check if user is already loged in
        if (firebaseAuth.getCurrentUser() != null){
            // user is loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        registerButton = (Button) findViewById(R.id.button_register);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        textViewSignin = (TextView) findViewById(R.id.textView_signin);

        registerButton.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // validations
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            //email or password is empty
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if the email and password are not empty
        // show progressbar
        progressDialog.setMessage("Registering User ...");
        progressDialog.show();


        // creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()){
                            // user is successfully registerd and logged in
                            // start the main (profile) activity here

                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            // right now lets display a toast only
                            //Toast.makeText(RegisterActivity.this, "Regesterd! ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Failed to regester! ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    @Override
    public void onClick(View view) {
        if(view == registerButton){
            registerUser();
        }else if(view == textViewSignin){
            // will open login act here.
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }
    }
}
