package com.example.user.authentication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UpComing extends AppCompatActivity {
    // private RecyclerView recyclerView;
    private Button cancel_appointment;
    String average_time;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);


        Intent intent = getIntent();
        if(intent != null) {
          average_time   = intent.getExtras().getString("average_time", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();


        }

        mAuth = FirebaseAuth.getInstance();

        cancel_appointment = (Button)findViewById(R.id.cancel_appointment);

      // final String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference count = FirebaseDatabase.getInstance().getReference().child("Appointment");
        count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num = (int) dataSnapshot.getChildrenCount();
                int average = Integer.parseInt(average_time);
                average = average*num;

                String user_id = mAuth.getCurrentUser().getUid();

                Toast.makeText(UpComing.this,"average time remaining is "+ average , Toast.LENGTH_SHORT).show();
                DatabaseReference setApp_Num = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);
                if(setApp_Num.child("app_num")==null) {

                    setApp_Num.child("app_num").setValue(num);
                }
                else{
                    Toast.makeText(UpComing.this,"You already have an appointment with the doctor" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference removeChild = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);
                removeChild.removeValue();
                Intent home = new Intent(UpComing.this,Home.class);
                startActivity(home);
                finish();

            }
        });


    }
}
      /* DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("/Doctors");

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<DocDetail,DocViewHolder> adapter = new FirebaseRecyclerAdapter<DocDetail, DocViewHolder>(
                DocDetail.class,
                R.layout.individual_row,
                DocViewHolder.class,
                reference

        )  {
            @Override
            protected void populateViewHolder(DocViewHolder viewHolder, DocDetail model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
            }
        };


        recyclerView.setAdapter(adapter);
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Email;
        public DocViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Email = (TextView) itemView.findViewById(R.id.Email);
        }

        public void setName(String name) {

            Name.setText(name);
            }

        public void setEmail(String email) {

            Email.setText(email);
        }



    }*/

