package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class JoinCourseActivity extends AppCompatActivity {

    EditText CourseCode;
    String student_username;
    String course_code;
    String merged_key;
    String checked_code;
    Boolean is_there_course;

    FirebaseDatabase CourseRootNode;
    DatabaseReference CourseReference;
    DatabaseReference new_root;

    FirebaseDatabase new_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_course);
        this.setTitle("Join Courses");

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        student_username = sp.getString("UserName", "");

        CourseCode = (EditText) findViewById(R.id.requested_code);

        new_db = FirebaseDatabase.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    public void join_course_clicked(View view) {

        is_there_course = false;

        course_code = CourseCode.getText().toString();
        merged_key = student_username + course_code;

        if(course_code.length() == 0) {

            CourseCode.setError("Enter Course code");
            Toast.makeText(this, "No course code have been entered",Toast.LENGTH_SHORT).show();

        }
        else {

            new_root = new_db.getReference("courses");

            Query checked_query = new_root.orderByChild("courseCode").equalTo(course_code);

            checked_query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()) {

                        CourseRootNode = FirebaseDatabase.getInstance();
                        CourseReference = CourseRootNode.getReference("joined_courses");

                        course_helper courseHelper = new course_helper(course_code,student_username);

                        CourseReference.child(merged_key).setValue(courseHelper);

                        Toast.makeText(getApplicationContext(),"Joined successfully" ,Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),student_homepage.class));
                    }
                    else {
                        //Toast.makeText(getApplicationContext(), "No course found!",Toast.LENGTH_SHORT).show();

                        CourseCode.setError("No such Course exist");
                        CourseCode.requestFocus() ;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}