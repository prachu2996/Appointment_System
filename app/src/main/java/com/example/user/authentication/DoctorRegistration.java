package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DoctorRegistration extends AppCompatActivity {



    private EditText Name;
    private EditText Email;
    private EditText Dob;
    private EditText Fees;
    private EditText Time;
    private EditText Location;
    private EditText Mob;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button Register_button;
    private Button logout_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        Fees = (EditText) findViewById(R.id.Fees);
        Location = (EditText) findViewById(R.id.Location);
        Time = (EditText) findViewById(R.id.Time);
        Dob = (EditText) findViewById(R.id.Dob);
        Mob = (EditText) findViewById(R.id.Mob);
        Register_button = (Button) findViewById(R.id.Register_button);
        logout_btn = (Button) findViewById(R.id.logout_btn);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSexGroup);
        mAuth = FirebaseAuth.getInstance();


        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = Name.getText().toString();
                final String email = Email.getText().toString();
                final String dob = Dob.getText().toString();
                final String mob = Mob.getText().toString();
                final String fees = Fees.getText().toString();
                final String location = Location.getText().toString();
                final String time = Time.getText().toString();

                final String gender = radioSexButton.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors").child(user_id);
                   DatabaseReference docNames = FirebaseDatabase.getInstance().getReference().child("DocNames").child(user_id).child("Name");



                    Map newPost = new HashMap();
                    newPost.put("name", name);
                    newPost.put("Email", email);
                    newPost.put("Dob", dob);
                    newPost.put("Mob", mob);
                    newPost.put("Gender", gender);
                    newPost.put("Fees", fees);
                    newPost.put("Location", location);
                    newPost.put("Time", time);

                    current_user_db.setValue(newPost);

                 /*   Map newPost1 = new HashMap();
                    newPost1.put("Name", name);
                    docNames.setValue(newPost1);*/

                    docNames.setValue(name);
                    Toast.makeText(DoctorRegistration.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();
                    Intent viewAppointment = new Intent(DoctorRegistration.this,ViewAppointment.class);
                    startActivity(viewAppointment);
                    finish();


                }
            }
        });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendToAuth();
            }
        });

    }


    private void sendToAuth(){
        Intent authIntent = new Intent(DoctorRegistration.this,AuthActivity.class);
        startActivity(authIntent);
        finish();

    }

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        int id = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(id);

    }



    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            Intent authintent = new Intent(DoctorRegistration.this,AuthActivity.class);
            startActivity(authintent);



            finish();
        }




    }





}
