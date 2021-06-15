package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class teacher_assignment_page extends AppCompatActivity {

    String teacher_username;
    String current_course_code;
    String current_course_name;
    SharedPreferences sp;
    SharedPreferences course_code_sp;

    TextView course_name_view;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_page);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        course_name_view=findViewById(R.id.current_courseName);

        sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");

        course_code_sp = getApplicationContext().getSharedPreferences("course_code_prefs", Context.MODE_PRIVATE);

        current_course_code = course_code_sp.getString("Teacher_Course_Code", "");

        current_course_name = course_code_sp.getString("Teacher_Course_Name", "");

        course_name_view.setText(current_course_name);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation) ;

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull  MenuItem menuItem) {
               switch(menuItem.getItemId()) {
                   case R.id.add_assignment:

                       System.out.println(current_course_code);
                       System.out.println(current_course_name);
                       startActivity(new Intent(getApplicationContext(),teacher_assignment_add.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.people:
                       startActivity(new Intent(getApplicationContext(),teacher_assignment_people.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.dashboard:
                       return true;
               }
               return false;
           }
       });
    }
}