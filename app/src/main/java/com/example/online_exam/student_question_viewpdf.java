package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class student_question_viewpdf extends AppCompatActivity {

    WebView pdf_view ;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_viewpdf);


        String ques_pdf_url = getIntent().getStringExtra("ques_url");
        try {
            ques_pdf_url= URLEncoder.encode(ques_pdf_url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(ques_pdf_url);
        pdf_view = findViewById(R.id.teacher_ques_view_pdf) ;


        pdf_view = new WebView(this);
        setContentView(pdf_view);
        pdf_view.getSettings().setJavaScriptEnabled(true);
        pdf_view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + ques_pdf_url);


    }
}