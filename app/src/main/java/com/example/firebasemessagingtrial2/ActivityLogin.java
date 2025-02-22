package com.example.firebasemessagingtrial2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
/*
    LoginActivity allows the user to log in with their email and password
 */
public class ActivityLogin extends AppCompatActivity {

    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private Button btnLogin;
    private TextView btnGuest, btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayout20);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        edtEmail = (TextInputEditText) findViewById(R.id.edtLoginEmail);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPass);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGuest = (TextView) findViewById(R.id.tvGuest);
        btnSignup = (TextView)findViewById(R.id.tvSignup);

        progressBar = (ProgressBar)findViewById(R.id.loginPgBr);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getCurrentUser());
        btnGuest.setClickable(true);
        btnSignup.setClickable(true);


        //login
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pss = edtPassword.getText().toString();

                if (email.isEmpty()){
                    edtEmail.setError("Please enter an email.");
                    edtEmail.requestFocus();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Enter a valid email.");
                    edtEmail.requestFocus();
                    return;
                }
                else if(pss.isEmpty()){
                    edtPassword.setError("Please enter a password.");
                    edtPassword.requestFocus();
                    return;
                }
                else if(pss.length() < 6){
                    edtPassword.setError("Invalid password.");
                    edtPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(ActivityLogin.this, HomeActivity.class);
                            System.out.println(mAuth.getCurrentUser());
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(ActivityLogin.this, "Failed to login. Please retry.", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        //guest mode
        btnGuest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

        //signup
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),ActivityRegister.class);
                startActivity(i);
            }
        });

    }

}