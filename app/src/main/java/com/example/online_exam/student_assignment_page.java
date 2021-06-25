package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment_page);

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

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {



                        student_assignment_topic = dataSnapshot.child("Assignment_topic").getValue(String.class);
                        student_assignment_duration = dataSnapshot.child("Assignment_time").getValue(String.class);
                        ques_pdf_url = dataSnapshot.child("pdf_file_url").getValue(String.class);

                        assignment_lists.add(new student_model_assignmentlist(student_assignment_topic, student_assignment_duration,ques_pdf_url));


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}