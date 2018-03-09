package com.example.user.authentication;

import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.FirebaseException;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.PhoneAuthCredential;
        import com.google.firebase.auth.PhoneAuthProvider;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {
    private EditText contact_edit_text;
    private EditText code_edit_text;
    private Button verify_btn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private TextView error_textview;
    private TextView Text;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth.AuthStateListener AuthListener;//if user already logged in
    private DatabaseReference database;
    private ProgressBar progressBar;


    private int btnType = 0; //intially button is not clicked

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(AuthListener);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        contact_edit_text = (EditText) findViewById(R.id.contact_edit_text);
        code_edit_text = (EditText) findViewById(R.id.code_edit_text);
        verify_btn = (Button) findViewById(R.id.verify_btn);
        error_textview = (TextView) findViewById(R.id.error_textview);
        Text = (TextView) findViewById(R.id.Text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final  String phone =contact_edit_text.getText().toString();


        mAuth = FirebaseAuth.getInstance(); //firebase instance

        database = FirebaseDatabase.getInstance().getReference().child("User");



        AuthListener = new FirebaseAuth.AuthStateListener() {//if user is already logged in
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){//if not null means there is a user already logged in
                    checkUserExist();
                    startActivity(new Intent(AuthActivity.this,Home.class));//if logged in already send him to homeactivity rather than login activity
                }
            }
        };



        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //when verify button is clicked


                if(btnType==0) {

                    progressBar.setVisibility(View.VISIBLE);
                    Text.setText("Waiting for OTP");



                    String phone_number = contact_edit_text.getText().toString();


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phone_number,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            AuthActivity.this,               // Activity (for callback binding)
                            mCallbacks
                    );        // OnVerificationStateChangedCallbacks



                }else {
                    verify_btn.setEnabled(false);
                    String verificationCode = code_edit_text.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                error_textview.setText("There was some error in verification");
                error_textview.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                btnType = 1;
                //TO MAKE THE SCREEN AVAILABLE ONLY TO ENTER CODE
                progressBar.setVisibility(View.INVISIBLE);
                contact_edit_text.setVisibility(View.INVISIBLE);
                code_edit_text.setVisibility(View.VISIBLE);
                Text.setText("Please Entert the OTP and click SUBMIT");

                verify_btn.setText("SUBMIT");
                verify_btn.setEnabled(true);


                // ...
            }

        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {//this function is used to signin user

        String phone_number = contact_edit_text.getText().toString();

        if(!TextUtils.isEmpty(phone_number)){
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "signInWithCredential:success");

                                checkUserExist(); //if user is successfully logged in we need to check if he is already registered




                                // ...
                            } else {
                                // Sign in failed, display a message and update the UI
                                error_textview.setText("There was some error in logging in ");
                                error_textview.setVisibility(View.VISIBLE);

                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                    Toast.makeText(AuthActivity.this,"Incorrect OTP. Please check the otp and re-enter",Toast.LENGTH_SHORT);
                                }
                            }
                        }
                    });
        }
    }

    private void checkUserExist(){ //to check if the user already registered and has his data in the database

        final String user_id = mAuth.getCurrentUser().getUid(); //get the id of the currently logged in user

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(user_id)){ //if there is a child in the database having this particular user_id
                    //send  him directly to his home page rather than register

                    Intent registerintent = new Intent(AuthActivity.this, Option.class);
                    startActivity(registerintent);
                    finish();

                }
                else{   //if the user is only logged in and not registered than the we need to get him registered from MainActivity
                   // Toast.makeText(AuthActivity.this,"You need set your account",Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(AuthActivity.this, Home.class);
                    startActivity(HomeIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

