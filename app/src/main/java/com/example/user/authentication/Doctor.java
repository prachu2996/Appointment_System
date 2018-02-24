package com.example.user.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Doctor extends AppCompatActivity  {
   private ListView lst;

  private ArrayList<String> username = new ArrayList<>();

 // private ArrayList<DoctorModel> doctorDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        lst = (ListView) findViewById(R.id.listview);

         final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors");


       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, username);
        lst.setAdapter(arrayAdapter);


        current_user_db.addChildEventListener(new ChildEventListener() {
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
        });





        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;

                Intent confirm2 = new Intent(Doctor.this, Confirm.class);
               confirm2.putExtra("DocName", username.get(position));


                startActivity(confirm2);

            }

        });
    }}


       /* TextView name;
        TextView fees;*/
        // Button confirm_btn;

       /* name= (TextView)findViewById(R.id.doc_name);
        fees= (TextView)findViewById(R.id.doc_fees);*/
        // confirm_btn= (Button) findViewById(R.id.confirm_btn);

      /* confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirm = new Intent(Doctor.this,Confirm.class);
                        startActivity(confirm);

            }
        });*/

        // databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor");


  /*  @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Doc, DocViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doc, DocViewHolder>(


                Doc.class,
                R.layout.doc_list,
                DocViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(DocViewHolder viewHolder, Doc model, int position) {

     //           viewHolder.setName("hty");
   //             viewHolder.setFees("jhh");

            }
        };

        doc_list.setAdapter(firebaseRecyclerAdapter);

    }

    public static class DocViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Context mContext;

        public DocViewHolder(View itemView) {
            super(itemView);

             mView = itemView;
             mContext = itemView.getContext();


        }

       /* public void setName(String Name){

            TextView NameText = (TextView) mView.findViewById(R.id.NameText);
            NameText.setText(Name);
        }

        public void setFees(String fees){

            TextView FeeText = (TextView) mView.findViewById(R.id.FeeText);
            FeeText.setText(fees);
        }*/




