package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.core.Tag;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class teacher_question_viewpdf extends AppCompatActivity {

    WebView pdf_view ;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_question_viewpdf);
        this.setTitle("Question");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        String ques_pdf_url = getIntent().getStringExtra("pdf_file_url");
        try {
            ques_pdf_url=URLEncoder.encode(ques_pdf_url,"UTF-8");
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