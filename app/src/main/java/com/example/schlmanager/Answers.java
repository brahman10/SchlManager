package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Answers extends AppCompatActivity {

    ListView listView;
    // adapter class object
    DoubtAdapter adapter;
    private List<Doubt> UploadList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        Bundle extras = getIntent().getExtras();
        final String ques = extras.getString("Question");
        final String id = extras.getString("Id");

        listView = findViewById(R.id.listAnswers);
        UploadList = new ArrayList<>();
        TextView question = findViewById(R.id.question);
        question.setText(ques);
        DatabaseReference ansref = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Answers").child(id);

        ansref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot placeSnapshot : dataSnapshot.getChildren())
                {
                    Log.d("DATA",placeSnapshot.toString());
                    Doubt ques = placeSnapshot.getValue(Doubt.class);
                    UploadList.add(ques);
                }
                adapter = new DoubtAdapter(Answers.this,UploadList);
                listView.setAdapter(adapter);
                findViewById(R.id.progressAnswer).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Answers.this, AddAnswer.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });
    }
}