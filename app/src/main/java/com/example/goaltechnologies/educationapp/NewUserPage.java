package com.example.goaltechnologies.educationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewUserPage extends AppCompatActivity {
    Button stureg,empreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_page);
        stureg=(Button)findViewById(R.id.button);
        empreg=(Button)findViewById(R.id.button4);
        stureg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),StudentReg.class);
                startActivity(i);
            }
        });

        empreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),EmployeeReg.class);
                startActivity(i);
            }
        });
    }
}
