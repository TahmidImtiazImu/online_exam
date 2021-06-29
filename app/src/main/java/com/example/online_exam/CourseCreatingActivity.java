package com.example.online_exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Objects;

public class CourseCreatingActivity extends AppCompatActivity {

    private String course_name;
    private String course_code;
    private boolean isCreated = false;
    String teacher_username;
    Boolean is_there_course = false;

    FirebaseDatabase CourseRootNode = FirebaseDatabase.getInstance();
    DatabaseReference CourseReference = CourseRootNode.getReference("courses");
    //DatabaseReference retrieved_courseReference;

    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_creating);
        this.setTitle("Add course");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");

        createButton = findViewById(R.id.new_create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText courseName = (EditText) findViewById(R.id.Course_name);
                EditText courseCode = (EditText) findViewById(R.id.Course_code);

                if( courseName.getText().toString().length() == 0 ) {
                    courseName.setError("Course name is required!");
                }
                else if(courseCode.getText().toString().length() < 10) {

                    if (courseCode.getText().toString().length() == 0) {

                        courseCode.setError("Course code is required!");
                        Toast.makeText(getApplicationContext(), "Give a 10 digit code", Toast.LENGTH_SHORT).show();
                    }

                    else if(courseCode.getText().toString().length() < 10) {

                        courseCode.setError("Too short!");
                        Toast.makeText(getApplicationContext(), "Give a 10 digit code", Toast.LENGTH_SHORT).show();
                    }
                }

                else {

                    course_name = courseName.getText().toString();
                    course_code = courseCode.getText().toString();

                    isCreated = true;

                    Query checked_query = CourseReference.orderByChild("courseCode").equalTo(course_code);

                    checked_query.addValueEventListener(new ValueEventListener() {
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
                                hashMap.put("courseCode",course_code);
                                hashMap.put("courseName",course_name);
                                hashMap.put("currentUser", teacher_username);

                                CourseReference.child(course_code).setValue(hashMap);

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


                }
            }
        });

    }

}