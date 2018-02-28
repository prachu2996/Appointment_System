package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Confirm extends AppCompatActivity {
    Button book;
   DatabaseReference doctor;
   DatabaseReference database;
    private TextView DisplayName;
    private TextView DisplayFees;
    private TextView DisplayLocation;
   // String DocName = "";
    




    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        DisplayName = (TextView)findViewById(R.id.DisplayName);
        DisplayFees = (TextView)findViewById(R.id.DisplayFees);
        DisplayLocation = (TextView)findViewById(R.id.DisplayLocation);

     /*  Intent intent = getIntent();
        if(intent != null) {
            DocName = intent.getExtras().getString("DocName", "");
            Toast.makeText(Confirm.this,"docName is"+DocName,Toast.LENGTH_SHORT).show();


        }*/

        book = (Button)findViewById(R.id.book);
        mAuth = FirebaseAuth.getInstance();

        doctor = FirebaseDatabase.getInstance().getReference().child("Doctors").child("Physician").child("User1").child("Appointments");
        database = FirebaseDatabase.getInstance().getReference().child("Doctors").child("vQJ9IZt3yNfSylCA27UT6KrQKLX2");

       database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String displayName  = dataSnapshot.child("name").getValue().toString();
                String displayFees  = dataSnapshot.child("fees").getValue().toString();
                String displayLocation  = dataSnapshot.child("location").getValue().toString();

                DisplayName.setText(displayName);
                DisplayFees.setText(displayFees);
                DisplayLocation.setText(displayLocation);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient();
                Intent book = new Intent(Confirm.this,UpComing.class);
                startActivity(book);
            }
        });



    }
    private void addPatient() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String user_id = mAuth.getCurrentUser().getUid();
                String name = mAuth.getCurrentUser().getDisplayName();

        if (user != null) {
             DatabaseReference Appointment = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);

            Map newPost = new HashMap();
            newPost.put("name", name);
            newPost.put("id", user_id);
          //  newPost.put("docName", DocName);


        Appointment.setValue(newPost);
    }
        else{
            Toast.makeText(Confirm.this,"Please Login",Toast.LENGTH_SHORT).show();
        }


    }
}
