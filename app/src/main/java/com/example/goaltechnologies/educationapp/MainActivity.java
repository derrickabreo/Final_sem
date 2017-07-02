package com.example.goaltechnologies.educationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        // TODO: Your application init goes here.
                        Intent mInHome = new Intent(MainActivity.this, LoginPage.class);
                        startActivity(mInHome);
                        finish();
                    }
                }, 5000);
    }
}
