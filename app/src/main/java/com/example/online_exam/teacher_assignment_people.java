package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.example.online_exam.student_adapter_assignmentlist.context;

public class teacher_assignment_people extends AppCompatActivity {

    public String teacher_username;
    TextView teacher_name ;
    RecyclerView student_name_list ;
    public String Teacher_name;
    public  String Course_code ;
    public  String Student_user_name ;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_people);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");

        teacher_name  = findViewById(R.id.teacher_name) ;

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");

        SharedPreferences course_sp =getApplicationContext().getSharedPreferences("student_course_prefs",context.MODE_PRIVATE);
        String Course_code = course_sp.getString("Student_Course_Code","");

        //teacher_name.setText(teacher_username);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference("users");

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("joined_courses") ;

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
        root.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Teacher_username = dataSnapshot.child("Username").getValue(String.class);

                    if(Objects.equals(Teacher_username, teacher_username)){
                        Teacher_name = dataSnapshot.child("Enter_name").getValue(String.class) ;
                        teacher_name.setText(Teacher_name) ;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Student_course_code = dataSnapshot.child("courseCode").getValue(String.class);
                    System.out.println(Course_code);
                    //System.out.println(Student_course_code);
                    if(Objects.equals(Student_course_code,Course_code)){
                        Student_user_name = dataSnapshot.child("courseUser").getValue(String.class) ;
                        System.out.println(Student_user_name);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }
}