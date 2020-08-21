package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatesheetActivity extends AppCompatActivity {

    ListView listView;
    // adapter class object
    DataAdapter adapter;
    private List<Upload> UploadList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datesheet);
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();
        listView = findViewById(R.id.listview);
        UploadList = new ArrayList<>();
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String classs = dataSnapshot.child("Class").getValue().toString();
                DatabaseReference classref = FirebaseDatabase.getInstance().getReference().child("Datesheet").child(classs);
                classref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        for(DataSnapshot placeSnapshot : dataSnapshot1.getChildren())
                        {
                            Upload places = placeSnapshot.getValue(Upload.class);
                            UploadList.add(places);
                        }
                        adapter = new DataAdapter(DatesheetActivity.this,UploadList);
                        listView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upload upload = (Upload) listView.getItemAtPosition(position);
                String name = upload.getName();
                String url = upload.getUrl();
                String dop = upload.getDop();
                Intent intent = new Intent(DatesheetActivity.this, WebActivity.class);
                intent.putExtra("Name", name);
                intent.putExtra("Url", url);
                intent.putExtra("dop", dop);
                startActivity(intent);
            }
        });
    }
}