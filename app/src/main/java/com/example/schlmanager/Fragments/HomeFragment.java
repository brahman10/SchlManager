package com.example.schlmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schlmanager.DatesheetActivity;
import com.example.schlmanager.DoubtActivity;
import com.example.schlmanager.ExamActivity;
import com.example.schlmanager.R;
import com.example.schlmanager.SyllabusActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }


    FirebaseAuth mAuth ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        TextView name = v.findViewById(R.id.name);
        name.setText(user.getDisplayName());

        CardView Syllabus = v.findViewById(R.id.cardSyllabus);
        CardView datesheet = v.findViewById(R.id.cardDatesheet);
        CardView exam = v.findViewById(R.id.cardExam);
        CardView Doubt = v.findViewById(R.id.cardDoubt);
        datesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DatesheetActivity.class));
            }
        });
        Syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SyllabusActivity.class));
            }
        });
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExamActivity.class));
            }
        });
        Doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DoubtActivity.class));
            }
        });
        return  v;
    }
}