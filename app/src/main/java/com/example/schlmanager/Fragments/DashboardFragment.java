package com.example.schlmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.schlmanager.DoubtActivity;
import com.example.schlmanager.LoginActivity;
import com.example.schlmanager.R;
import com.example.schlmanager.Submit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    FirebaseAuth mAuth ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dashboard, container, false);

        ImageView imageView = v.findViewById(R.id.profileimage);
        final TextView textName = v.findViewById(R.id.name);
        TextView textEmail = v.findViewById(R.id.email);
        TextView logout = v.findViewById(R.id.logout);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals("Name"))
                        textName.setText(data.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(user.getPhotoUrl()!=null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(imageView);
        }
        else
            imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);

        //textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        v.findViewById(R.id.subassign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Submit.class));
            }
        });
        v.findViewById(R.id.askdoubts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DoubtActivity.class));
            }
        });
        return v;
    }
}