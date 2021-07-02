package com.example.online_exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class CourseCreatingActivity extends AppCompatActivity {

    private String course_name;
    private String course_code;
    private boolean isCreated = false;
    String teacher_username;
    String teacher_name;
    Boolean is_there_course = false;

    FirebaseDatabase CourseRootNode = FirebaseDatabase.getInstance();
    DatabaseReference CourseReference = CourseRootNode.getReference("courses");
    //DatabaseReference retrieved_courseReference;

    Button createButton;
    EditText courseName;
    EditText courseCode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_creating);
        this.setTitle("Add course");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");

        DatabaseReference new_ref = FirebaseDatabase.getInstance().getReference("users");
//        teacher_name = new_ref.child(teacher_username).getRef().toString();

        new_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                teacher_name = snapshot.child(teacher_username).child("Enter_name").getValue(String.class);

                //System.out.println("test name : " + teacher_name);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        createButton = findViewById(R.id.new_create);

        courseName = (EditText) findViewById(R.id.Course_name);
        courseCode = (EditText) findViewById(R.id.Course_code);

        createButton.setOnClickListener(v -> {
            String CourseName = courseName.getText().toString();
            String CourseCode = courseCode.getText().toString() ;

            if(CourseName.isEmpty()) {
                courseName.setError("Course name is required!");
                return ;
            }
            if(CourseCode.length()< 10) {

                if(CourseCode.isEmpty()) {

                    courseCode.setError("Course code is required!");
                }


                else {
                    courseCode.setError("Too short!");
                }
                Toast.makeText(getApplicationContext(), "Give a 10 digit code", Toast.LENGTH_SHORT).show();
                return ;
            }

//                course_name = courseName.getText().toString();
//                course_code = courseCode.getText().toString();

            isCreated = true;

            Query checked_query = CourseReference.orderByChild("courseCode").equalTo(CourseCode);

            checked_query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        courseCode.setError("This course code has been used");
                        Toast.makeText(getApplicationContext(), "Try another course Code", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        //course_helper courseHelper = new course_helper(course_name,course_code,teacher_username);

                        HashMap<String, Object> hashMap;

                        hashMap = new HashMap<>();
                        hashMap.put("courseCode",CourseCode);
                        hashMap.put("courseName",CourseName);
                        hashMap.put("currentUser", teacher_username);
                        hashMap.put("teacherEnter_name", teacher_name);

                        CourseReference.child(CourseCode).setValue(hashMap);

                        startActivity(new Intent(getApplicationContext(),teacher_homepage.class));


                        Toast.makeText(getApplicationContext(), "New course has been created", Toast.LENGTH_SHORT).show();

                        //finish();

//                                startActivity(new Intent(getApplicationContext(),teacher_homepage.class));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.w("CHAT_LOG", "Failed to read value.", error.toException());
                }
            });



        });

    }

}