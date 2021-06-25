package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class teacher_assignment_page extends AppCompatActivity {

    String teacher_username;
    String current_course_code;
    String current_course_name;
    SharedPreferences sp;
    SharedPreferences course_code_sp;

    TextView course_name_view;

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayout ;
    public List<teacher_model_assignmentlist> assignment_lists ;
    teacher_adapter_assignmentlist adapter ;

    public FirebaseDatabase db = FirebaseDatabase.getInstance();
    public DatabaseReference root = db.getReference("teacher_uploadPdf");

    String teacher_assignment_topic;
    String teacher_assignment_duration;
    String name_merge ;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_page);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclebview
       // initData() ;
        initRecycleview() ;

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

    //recycle view
//    private void initData() {
//
//
//        assignment_lists.add(new teacher_model_assignmentlist("Incourse","1 hours 30 mins"));
//        assignment_lists.add(new teacher_model_assignmentlist("Final","2 hours 30 mins"));
//
//    }

    //recycle view
    private void initRecycleview() {

        assignment_lists = new ArrayList<>() ;
        recyclerView = findViewById(R.id.teacher_assignment_recycleview) ;
        linearLayout = new LinearLayoutManager(this) ;
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new teacher_adapter_assignmentlist(assignment_lists,this) ;
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String course_code = dataSnapshot.child("Course_code").getValue(String.class);

                    if(course_code.equals(current_course_code)) {

                        teacher_assignment_topic = dataSnapshot.child("Assignment_topic").getValue(String.class);
                        teacher_assignment_duration = dataSnapshot.child("Assignment_time").getValue(String.class);
                        name_merge = teacher_assignment_topic+course_code ;

                        assignment_lists.add(new teacher_model_assignmentlist(teacher_assignment_topic, teacher_assignment_duration));
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    
}