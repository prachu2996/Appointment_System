package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Option extends AppCompatActivity {


    private Button doctor;
    private Button patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        doctor = (Button) findViewById(R.id.doctor);
        patient = (Button) findViewById(R.id.patient);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doctor = new Intent(Option.this,DoctorRegistration.class);
                startActivity(doctor);
                finish();
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent patient = new Intent(Option.this,MainActivity.class);
                startActivity(patient);
                finish();
            }
        });
    }
}
