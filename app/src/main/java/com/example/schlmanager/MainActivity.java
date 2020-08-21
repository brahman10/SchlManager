package com.example.schlmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.schlmanager.Fragments.AssignmentFragment;
import com.example.schlmanager.Fragments.DashboardFragment;
import com.example.schlmanager.Fragments.ExploreFragment;
import com.example.schlmanager.Fragments.HomeFragment;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class MainActivity extends AppCompatActivity {

    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tapBarMenu= findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuButtonClick();
            }
        });
        findViewById(R.id.home_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();
            }
        });
        findViewById(R.id.work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new ExploreFragment()).commit();
            }
        });
        findViewById(R.id.assignment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new AssignmentFragment()).commit();
            }
        });
        findViewById(R.id.dashboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new DashboardFragment()).commit();
            }
        });
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();

        }
    }
    private void onMenuButtonClick(){
        tapBarMenu.toggle();
    }
}