package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class student_homepage extends AppCompatActivity {

    Button logout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);
        getSupportActionBar().setTitle("Student_Homepage");
        logout = (Button)findViewById(R.id.logout) ;

    }
    public void btn_logout(View view) {
        startActivity(new Intent(getApplicationContext(),Login_form.class));
    }

}