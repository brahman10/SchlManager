package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DoubtActivity extends AppCompatActivity {

    ListView listView;
    // adapter class object
    DoubtAdapter adapter, adapter1;
    private List<Doubt> UploadList;
    private List<Doubt> filteredlist;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);

        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        listView = findViewById(R.id.listdoubt);
        UploadList = new ArrayList<>();
        filteredlist = new ArrayList<>();

        final DatabaseReference quesref = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Questions");
        quesref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot placeSnapshot : dataSnapshot.getChildren())
                {
                    Log.d("DATA",placeSnapshot.toString());
                    Doubt ques = placeSnapshot.getValue(Doubt.class);
                    UploadList.add(ques);
                }
                adapter = new DoubtAdapter(DoubtActivity.this,UploadList);
                listView.setAdapter(adapter);
                findViewById(R.id.progressDoubt).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doubt ques = (Doubt) listView.getItemAtPosition(position);
                String question = ques.getQuestion();
                String quesid = ques.getId();
                Intent intent = new Intent(DoubtActivity.this, Answers.class);
                intent.putExtra("Question", question);
                intent.putExtra("Id", quesid);
                startActivity(intent);
            }
        });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddDoubt.class));
            }
        });

        search= findViewById(R.id.searchBar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredlist.clear();
                if(search!=null)
                {
                    for (Doubt upload:UploadList) {

                        if(upload.Question.toLowerCase().contains(newText.toLowerCase()))
                            filteredlist.add(upload);
                    }
                }
                else
                    filteredlist = UploadList;

                adapter1 = new DoubtAdapter(DoubtActivity.this,filteredlist);
                listView.setAdapter(adapter1);
                return false;
            }
        });

    }
}