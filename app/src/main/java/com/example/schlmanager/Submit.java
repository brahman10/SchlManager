package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Submit extends AppCompatActivity {

    EditText etName , etAssign , eturl ;
    Spinner spinnerAssign;
    ImageButton upload;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        etName=findViewById(R.id.name1);
        etAssign = findViewById(R.id.etAssign);
        eturl = findViewById(R.id.etUrl);
        spinnerAssign = findViewById(R.id.spinnerAssign);
        upload = findViewById(R.id.UploadAssign);


        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals("Name"))
                        etName.setText(data.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name  = etName.getText().toString();
                final String assign  = etAssign.getText().toString();
                final String url  = eturl.getText().toString();
                final String teacher  = spinnerAssign.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {

                    etName.setError("Enter Your Name");
                }
                else if (TextUtils.isEmpty(assign)) {

                    etName.setError("Enter Assignment Name");
                }
                else if (TextUtils.isEmpty(url)) {

                    etName.setError("Enter Assignment URL");
                }
                else if (teacher.equalsIgnoreCase("Choose Teacher")) {

                    etName.setError("Choose Faculty Name");
                }
                else
                {
                    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Submitted Assignment").child(teacher);
                    Submission submission = new Submission(name,assign,teacher,url);
                    String uploadId=mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(submission);
                    Toast.makeText(Submit.this,"Uploaded Successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (Submit.this , MainActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
}