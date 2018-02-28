package com.example.user.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpComing extends AppCompatActivity {
    // private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);

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

