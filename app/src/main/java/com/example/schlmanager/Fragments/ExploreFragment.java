package com.example.schlmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schlmanager.DataAdapter;
import com.example.schlmanager.R;
import com.example.schlmanager.Upload;
import com.example.schlmanager.WebActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment {


    public ExploreFragment() {
        // Required empty public constructor
    }

    ListView listView;
    // adapter class object
    DataAdapter adapter;
    private List<Upload> UploadList;

    private DatabaseReference mDatabaseref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_assignment, container, false);

        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();
        listView = v.findViewById(R.id.listview);
        UploadList = new ArrayList<>();
        final ProgressBar progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String classs = dataSnapshot.child("Class").getValue().toString();
                DatabaseReference classref = FirebaseDatabase.getInstance().getReference().child("Announcements").child(classs);
                classref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        for(DataSnapshot placeSnapshot : dataSnapshot1.getChildren())
                        {
                            Upload places = placeSnapshot.getValue(Upload.class);
                            UploadList.add(places);
                        }
                        //Toast.makeText(getActivity(),UploadList.toString(),Toast.LENGTH_SHORT).show();
                        adapter = new DataAdapter(getActivity(),UploadList);
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
                int itemposition = position;
                Upload upload = (Upload) listView.getItemAtPosition(position);
                String name = upload.getName();
                String url = upload.getUrl();
                String dop = upload.getDop();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("Name", name);
                intent.putExtra("Url", url);
                intent.putExtra("dop", dop);
                startActivity(intent);
            }
        });



        return v;
    }


}