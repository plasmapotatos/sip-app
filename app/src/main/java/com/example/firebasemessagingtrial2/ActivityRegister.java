package com.example.firebasemessagingtrial2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
/*
    ActivityRegister allows the user to create a new account
 */
public class ActivityRegister extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText edtUser, edtEmail, edtPass1, edtPass2;
    TextView btnRegister;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayoutRegister);

        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.tvRegisterUser);
        edtUser = findViewById(R.id.edtLoginEmail);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass1 = findViewById(R.id.edtPassword1);
        edtPass2 = findViewById(R.id.edtPassword2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    @Override
    public void onClick(View v) {
        return;
    }

    //checks if user info is valid, then registers user with firebase:
    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password1 = edtPass1.getText().toString();
        String password2 = edtPass2.getText().toString();
        String username = edtUser.getText().toString().trim();

        if (username.isEmpty()) {
            edtUser.setError("Please enter a username.");
            edtUser.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email.");
            edtEmail.requestFocus();
            return;
        } else if (password1.isEmpty()) {
            edtPass1.setError("Please enter a valid password.");
            edtPass1.requestFocus();
            return;
        } else if (!password1.equals(password2)) {
            edtPass1.setError("Passwords must match.");
            edtPass1.requestFocus();
            return;
        } else if (password1.length() < 6) {
            edtPass1.setError("Password must be atleast 6 characters.");
            edtPass1.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();


                            //update username
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();
                            user1.updateProfile(profileUpdates);


                            User user = new User(username, email);

                            //add user to firebase database:
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(ActivityRegister.this, "Successfully registered " + username, Toast.LENGTH_LONG).show();

                                                //create user object and upload it:
                                                User newUser = new User();

                                                progressBar.setVisibility(View.GONE);
                                                Intent i = new Intent(ActivityRegister.this, ActivityLogin.class);
                                                i.putExtra("allDecks", "");
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(ActivityRegister.this, "Failed to register (Database)", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(ActivityRegister.this, "Failed to register (Authentication)", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}