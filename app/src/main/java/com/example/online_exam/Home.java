package com.example.online_exam;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Home extends android.app.Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();


        if(fUser != null)
        {
            String showUser  = fUser.getDisplayName();
            System.out.println("\nDisplay Name=\n"+showUser);
           if(showUser.equals("Teacher")) {

               Intent intent = new Intent(Home.this, teacher_homepage.class);
               intent.putExtra("logging_check", "teacher_LoggedIn");

                startActivity(intent);
           }
           else {
               Intent intent = new Intent(Home.this, student_homepage.class);
               intent.putExtra("logging_check", "student_LoggedIn");

               startActivity(intent);
           }
        }
        else
        {
            startActivity(new Intent(Home.this, LoginActivity.class));
        }
    }

}
