package com.example.online_exam;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends android.app.Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        if(fUser != null)
        {
            startActivity(new Intent(Home.this, teacher_homepage.class));
        }
        else
        {
            startActivity(new Intent(Home.this, LoginActivity.class));
        }
    }

}
