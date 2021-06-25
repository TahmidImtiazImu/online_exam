package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class student_answer_submit_page extends AppCompatActivity {

    TextView student_assignment_topic, student_assignment_duration ;
    ImageView student_loading_ques ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer_submit_page);

        String assignment_topic = getIntent().getStringExtra("Assignment_topic");
        String assignment_duration = getIntent().getStringExtra("Assignment_time");
        String ques_pdf_url = getIntent().getStringExtra("ques_url") ;

        student_assignment_topic = findViewById(R.id.student_assignment_topic_name2) ;
        student_assignment_duration = findViewById(R.id.student_assignment_duration) ;
        student_loading_ques = findViewById(R.id.student_loading_ques) ;

        student_assignment_topic.setText(assignment_topic);
        student_assignment_duration.setText(assignment_duration);

        student_loading_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),student_question_viewpdf.class) ;
                intent.putExtra("ques_url",ques_pdf_url);
                startActivity(intent);
            }
        });


    }
}