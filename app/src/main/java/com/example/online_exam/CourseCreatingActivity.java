package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_exam.course_helper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

//here my comment

public class CourseCreatingActivity extends AppCompatActivity {

    private String course_name;
    private String course_code;
    private boolean isCreated = false;
    String teacher_username;

    FirebaseDatabase CourseRootNode;
    DatabaseReference CourseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_creating);
        this.setTitle("Add course");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");
    }

    public void create_button_clicked(View view) {

        EditText courseName = (EditText) findViewById(R.id.Course_name);
        EditText courseCode = (EditText) findViewById(R.id.Course_code);

        if( courseName.getText().toString().length() == 0 ) {
            courseName.setError("Course name is required!");
        }
        else if(courseCode.getText().toString().length() < 10) {

            if (courseCode.getText().toString().length() == 0) {

                courseCode.setError("Course code is required!");
                Toast.makeText(this, "Give a 10 digit code", Toast.LENGTH_SHORT).show();
            }

            else if(courseCode.getText().toString().length() < 10) {

                courseCode.setError("Too short!");
                Toast.makeText(this, "Give a 10 digit code", Toast.LENGTH_SHORT).show();
            }
        }

        else {

            course_name = courseName.getText().toString();
            course_code = courseCode.getText().toString();

            isCreated = true;

            CourseRootNode = FirebaseDatabase.getInstance();
            CourseReference = CourseRootNode.getReference("courses");

            course_helper courseHelper = new course_helper(course_name,course_code,teacher_username);

            CourseReference.child(course_code).setValue(courseHelper);

            Toast.makeText(this, "New course has been created", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(),teacher_homepage.class));

        }
    }
}