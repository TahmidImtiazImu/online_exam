package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class student_answer_view extends AppCompatActivity {

    WebView pdf_view ;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer_view);

        String answer_pdf_url = getIntent().getStringExtra("my_answer");
        try {
            answer_pdf_url= URLEncoder.encode(answer_pdf_url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(answer_pdf_url);
        pdf_view = findViewById(R.id.student_ans_view_pdf) ;


        pdf_view = new WebView(this);
        setContentView(pdf_view);
        pdf_view.getSettings().setJavaScriptEnabled(true);
        pdf_view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + answer_pdf_url);
    }
}