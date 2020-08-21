package com.example.schlmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText mEmail,mPassword,mUsername,mConfirmPassword,etClass;
    Button mSignupbtn;
    TextView mloginbtn;

    DatabaseReference mDatabaseref;
    String userID;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mEmail=findViewById(R.id.etEmailLogin);
        mPassword=findViewById(R.id.etPasswordLogin);
        mSignupbtn=(Button)findViewById(R.id.btnsignup);
        mloginbtn=(TextView)findViewById(R.id.txtlogin);
        etClass = findViewById(R.id.etClass);
        //pbar=(ProgressBar)findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        mUsername=findViewById(R.id.etName);
        mConfirmPassword=findViewById(R.id.etcnfrmPasswordLogin);


        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }
        mSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                final String username=mUsername.getText().toString();
                final String cnfrmpassword=mConfirmPassword.getText().toString();

                if (email.isEmpty()){
                    mEmail.setError("Email is Required");
                    mEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    mPassword.setError("Password is Required");
                    mPassword.requestFocus();
                    return;
                }

                if (password.length()<6) {
                    mPassword.setError("Password must be of length 6");
                    mPassword.requestFocus();
                    return;
                }

                if (cnfrmpassword.isEmpty()) {
                    mConfirmPassword.setError("Password is Required");
                    mConfirmPassword.requestFocus();
                    return;
                }

                if (!password.equals(cnfrmpassword)) {
                    mConfirmPassword.setError("Password must be same as above");
                    mConfirmPassword.requestFocus();
                    return;
                }
                //pbar.setVisibility(View.VISIBLE);

                //register the user in firebase
                if ((!(email.isEmpty() && password.isEmpty()))&&password.equals(cnfrmpassword)) {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //
                                FirebaseUser fuser=fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupActivity.this,"verification email has been sent",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignupActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                userID=fAuth.getCurrentUser().getUid();
                                mDatabaseref = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                                Map<String,Object> user=new HashMap<>();
                                user.put("Name",username);
                                user.put("Email",email);
                                user.put("class",etClass.getText().toString());

                                mDatabaseref.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onSuccess :+ user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure :" + e.toString());
                                    }
                                });

                            } else {
                                Toast.makeText(SignupActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Error in connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}