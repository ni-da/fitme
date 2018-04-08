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

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //check if user is already loged in
        if (firebaseAuth.getCurrentUser() != null){
            // user is loged in --> start direct profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }



        buttonSignIn = (Button) findViewById(R.id.button_signIn);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        textViewSignUp = (TextView) findViewById(R.id.textView_signup);

        //progressDialog
        progressDialog = new ProgressDialog(this);



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
        progressDialog.setMessage("Registering User ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            // start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });
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
