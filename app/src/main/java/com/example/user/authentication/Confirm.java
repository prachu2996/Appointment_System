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
import com.google.firebase.database.ChildEventListener;
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
    private TextView DisplayTime;
    private FirebaseAuth mAuth;

   String docId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        DisplayName = (TextView)findViewById(R.id.DisplayName);
        DisplayFees = (TextView)findViewById(R.id.DisplayFees);
        DisplayLocation = (TextView)findViewById(R.id.DisplayLocation);
        DisplayTime = (TextView)findViewById(R.id.DisplayTime);


       Intent intent = getIntent();
        if(intent != null) {
            docId = intent.getExtras().getString("docId", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();


        }

        book = (Button)findViewById(R.id.book);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference().child("Doctors").child(docId);

       database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String displayName  = dataSnapshot.child("name").getValue().toString();
                String displayFees  = dataSnapshot.child("fees").getValue().toString();
                String displayLocation  = dataSnapshot.child("location").getValue().toString();
                String displayTime = dataSnapshot.child("time").getValue().toString();

                DisplayName.setText(displayName);
                DisplayFees.setText(displayFees);
                DisplayLocation.setText(displayLocation);
                DisplayTime.setText(displayTime);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPatient();
                DatabaseReference avg_time = FirebaseDatabase.getInstance().getReference().child("Doctors").child(docId);
                avg_time.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String average_time = dataSnapshot.child("avg_time").getValue().toString();
                        Intent book = new Intent(Confirm.this,UpComing.class);
                        book.putExtra("average_time",average_time);
                        startActivity(book);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                Intent book = new Intent(Confirm.this,UpComing.class);
                startActivity(book);
                finish();
            }
        });

    }



    private void addPatient() {
       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


              final  String patientName  = dataSnapshot.child("name").getValue().toString();
                if (user != null) {
                    //create a new node and add patient name under a child with id of a doctor
                    String user_id = mAuth.getCurrentUser().getUid();
                 final  DatabaseReference Appointment = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);

                            Map newPost = new HashMap();
                            newPost.put("docId",docId);
                            newPost.put("patient_name",patientName);
                            newPost.put("app_num",null);
                            Appointment.setValue(newPost);
                }
                else{
                    Toast.makeText(Confirm.this,"Please Login",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




       /* if (user != null) {
             DatabaseReference Appointment = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);

            Map newPost = new HashMap();
          //  newPost.put("name", name);
            newPost.put("id", user_id);
            newPost.put("patient_name",patientName);
            newPost.put("appointment_num",appointment_number);
          //  newPost.put("docName", DocName);


        Appointment.setValue(newPost);
    }
        else{
            Toast.makeText(Confirm.this,"Please Login",Toast.LENGTH_SHORT).show();
        }*/


    }
}
