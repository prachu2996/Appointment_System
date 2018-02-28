package com.example.user.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Doctor extends AppCompatActivity {
    // private ListView lst;

    // private ArrayList<String> username = new ArrayList<>();

    // private ArrayList<DoctorModel> doctorDetails = new ArrayList<>();

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("/Doctors");


        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<DocDetail, Doctor.DocViewHolder> adapter = new FirebaseRecyclerAdapter<DocDetail, Doctor.DocViewHolder>(
                DocDetail.class,
                R.layout.individual_doctor,
                Doctor.DocViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(Doctor.DocViewHolder viewHolder, DocDetail model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setFees(model.getFees());
                viewHolder.setTime(model.getTime());
                //  viewHolder.setLocation(model.getLocation());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent doctorProfile = new Intent(Doctor.this,Confirm.class);
                        startActivity(doctorProfile);
                        finish();
                    }
                });

            }
        };


        recyclerView.setAdapter(adapter);
    }


    public static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Fees;
        TextView Time;
        // TextView Location;
        private CardView card;

        public DocViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Fees = (TextView) itemView.findViewById(R.id.Fees);
            Time = (TextView) itemView.findViewById(R.id.Time);
            //  Location = (TextView) itemView.findViewById(R.id.Location);


        }

        public void setName(String name) {

            Name.setText("Name:" + name);
        }

        public void setFees(String fees) {

            Fees.setText("Fees:" + fees);
        }


        public void setTime(String time) {

            Time.setText("Time" + time);
        }

      /*  public void setLocation(String location) {
            Time.setText(location);
        }*/

    }






}
      //  lst = (ListView) findViewById(R.id.listview);

       //  final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors");


      // final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, username);
       // lst.setAdapter(arrayAdapter);


       /* current_user_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                DoctorList doctor =(DoctorList)dataSnapshot.getValue(DoctorList.class);
                String doctorString = String.valueOf(doctor);
               // arrayAdapter.add(doctorString);
                username.add(doctor.getName());
                //String docid = doctor.getId();

               arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





      /*  lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;

                Intent confirm2 = new Intent(Doctor.this, Confirm.class);
               confirm2.putExtra("DocName", username.get(position));


                startActivity(confirm2);

            }

        });*/



