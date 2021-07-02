package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.online_exam.student_adapter_assignmentlist.context;

public class student_assignment_page extends AppCompatActivity {

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayout ;
    public List<student_model_assignmentlist> assignment_lists ;
    student_adapter_assignmentlist adapter ;

    public FirebaseDatabase db = FirebaseDatabase.getInstance();
    public DatabaseReference root = db.getReference("teacher_uploadPdf");

    String student_assignment_topic;
    String student_assignment_duration;
    String ques_pdf_url;
    String name_merge ;
    String student_course_code;
    TextView course_name;
    String student_course_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment_page);
        course_name = findViewById(R.id.student_current_courseName) ;
        this.setTitle("Assignments");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initRecycleview() ;
    }

    private void initRecycleview() {
        assignment_lists = new ArrayList<>() ;
        recyclerView = findViewById(R.id.student_assignment_recycleview) ;
        linearLayout = new LinearLayoutManager(this) ;
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new student_adapter_assignmentlist(assignment_lists,this) ;
        recyclerView.setAdapter(adapter);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sp =getApplicationContext().getSharedPreferences("student_course_prefs",context.MODE_PRIVATE);
                student_course_code = sp.getString("Student_Course_Code","");
                student_course_name = sp.getString("Student_Course_Name","");
                course_name.setText(student_course_name);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                       String course_code = dataSnapshot.child("Course_code").getValue(String.class);

                        if(course_code.equals(student_course_code)) {
                            student_assignment_topic = dataSnapshot.child("Assignment_topic").getValue(String.class);
                            student_assignment_duration = dataSnapshot.child("Assignment_time").getValue(String.class);
                            ques_pdf_url = dataSnapshot.child("pdf_file_url").getValue(String.class);


                            assignment_lists.add(new student_model_assignmentlist(student_assignment_topic, student_assignment_duration, ques_pdf_url,student_course_code));

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