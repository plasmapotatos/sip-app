package com.example.firebasemessagingtrial2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
/*
    MainActivity is the initial activity that it loads and immediately sends the user over to the login activity
    Totally there are 15+ activities in this application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
        MainActivity.this.startActivity(intent);
    }
}