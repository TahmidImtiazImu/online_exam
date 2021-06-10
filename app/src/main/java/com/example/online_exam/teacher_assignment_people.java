package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class teacher_assignment_people extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_people);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation) ;

        bottomNavigationView.setSelectedItemId(R.id.people);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.add_assignment:
                        startActivity(new Intent(getApplicationContext(),teacher_assignment_add.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.people:

                        return true;
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),teacher_assignment_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}