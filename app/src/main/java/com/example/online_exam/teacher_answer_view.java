package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.util.Objects;

public class teacher_answer_view extends AppCompatActivity {

    WebView ans_view ;
    public String answer_Pdf_url ;
    public String merge_code ;
    public String pdf_file_name ;
    DatabaseReference fb;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_answer_view);

        merge_code = getIntent().getStringExtra("merge_code") ;
        System.out.println( "marge"+merge_code);

        fb = FirebaseDatabase.getInstance().getReference("student_upload_answer").child(merge_code) ;
        String text = url_retrieve() ;

        System.out.println("print it " + text);


    }

    @SuppressLint("SetJavaScriptEnabled")
    public String url_retrieve() {
       // fb = FirebaseDatabase.getInstance().getReference("student_upload_answer") ;

       fb.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                pdf_file_name = datasnapshot.child("pdf_file_name").getValue(String.class);

               System.out.println("pdf_name "+ pdf_file_name);
               answer_Pdf_url = datasnapshot.child("Student_answer_url").getValue(String.class);
               System.out.println("Answer url  " +answer_Pdf_url);

           try {
            answer_Pdf_url= URLEncoder.encode(answer_Pdf_url,"UTF-8");
           } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }

              ans_view = findViewById(R.id.teacher_ans_view_pdf) ;


             ans_view = new WebView(getApplicationContext());
             setContentView(ans_view);
             ans_view.getSettings().setJavaScriptEnabled(true);
             ans_view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + answer_Pdf_url);
           }

           @Override
           public void onCancelled(@NonNull  DatabaseError error) {

           }
       });
        System.out.println("print it "+answer_Pdf_url);

       return  answer_Pdf_url ;
    }
}