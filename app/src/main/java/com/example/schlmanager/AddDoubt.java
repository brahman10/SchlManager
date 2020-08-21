package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddDoubt extends AppCompatActivity {

    FirebaseAuth mAuth ;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);

        final DatabaseReference quesref = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Questions");

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        Button add =findViewById(R.id.add);
        final EditText etQues = findViewById(R.id.etQuestion);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren() ){
                            if(data.getKey().equals("Name")){
                                name=data.getValue().toString();

                                final String ques = etQues.getText().toString();
                                final String ansid=quesref.push().getKey();
                                Doubt doubt = new Doubt(ques,ansid,name);
                                quesref.child(ansid).setValue(doubt);

                                Toast.makeText(getApplicationContext(),"Doubt Added Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),DoubtActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}