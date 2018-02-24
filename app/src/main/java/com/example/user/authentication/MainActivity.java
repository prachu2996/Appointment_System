package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logout_btn;
    private EditText Name;
    private EditText Email;
    private EditText Dob;
    private EditText Mob;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;


    private Button Register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        logout_btn = (Button) findViewById(R.id.logout_btn);
        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        Dob = (EditText) findViewById(R.id.Dob);
        Mob = (EditText) findViewById(R.id.Mob);
        Register_button = (Button) findViewById(R.id.Register_button);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSexGroup);



       /* search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(MainActivity.this,Home.class);
                startActivity(search);
            }
        });*/





        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String name = Name.getText().toString();
               final String email = Email.getText().toString();
                final String dob = Dob.getText().toString();
                final String mob = Mob.getText().toString();
                final String gender = radioSexButton.getText().toString();

                //details are empty
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(name)||TextUtils.isEmpty(dob)||TextUtils.isEmpty(mob)||TextUtils.isEmpty(gender)){
                    Toast.makeText(MainActivity.this,"Fields are Empty",Toast.LENGTH_SHORT).show();
                }
                    else {

                    Query emailquery = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(email);
                    emailquery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                Toast.makeText(MainActivity.this, "email id is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    // User is signed in
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
                                    Map newPost = new HashMap();
                                    newPost.put("Name", name);
                                    newPost.put("Email", email);
                                    newPost.put("Dob", dob);
                                    newPost.put("Mob", mob);
                                    newPost.put("Gender", gender);


                                    current_user_db.setValue(newPost);
                                    Toast.makeText(MainActivity.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();

                                    Intent mainIntent = new Intent(MainActivity.this,Home.class);
                                    startActivity(mainIntent);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        int id = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(id);

    }




    private void sendToAuth(){
        Intent authIntent = new Intent(MainActivity.this,AuthActivity.class);
       startActivity(authIntent);
       finish();

      //  Intent authIntent = new Intent(MainActivity.this,Main2Activity.class);
      //  startActivity(authIntent);
       // finish();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
           Intent authintent = new Intent(MainActivity.this,AuthActivity.class);
            startActivity(authintent);

          //  Intent authintent = new Intent(MainActivity.this,Main2Activity.class);
          //  startActivity(authintent);

            finish();
        }




    }






}
