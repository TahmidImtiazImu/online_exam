package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;

    EditText regUser, regName, regEmail, regPass, regConfirmPass, regInstitution;
    Button signUpBtn, signUpBtnS,backToLoginBtn , backToLoginBtnS;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tabLayout=findViewById(R.id.tab_layout);
        pager2=findViewById(R.id.view_pager2);

//        //under fragment teacher
//        regUser = findViewById(R.id.regUser);
//        regName = findViewById(R.id.regName);
//        regEmail = findViewById(R.id.regEmail);
//        regPass = findViewById(R.id.regPassword);
//        regConfirmPass = findViewById(R.id.regConfirmPassword);
//        regInstitution = findViewById(R.id.regInstitution);
//        signUpBtn = findViewById(R.id.btnSignUpTeacher);
//        signUpBtnS = findViewById(R.id.btnSignUpStudent);
//        backToLoginBtn = findViewById(R.id.backToLogin);
//        backToLoginBtnS = findViewById(R.id.backToLoginStudent);

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        FragmentManager fm= getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Teacher"));
        tabLayout.addTab(tabLayout.newTab().setText("Student"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

//        signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createUser();
//            }
//        });
//        signUpBtnS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createUser();
//            }
//        });
    }

    private void createUser(){
        String email = regEmail.getText().toString();
        String pass = regPass.getText().toString();
        if((!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() )){
            if(!pass.isEmpty() ) {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        Toast.makeText(SignUp.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(SignUp.this, "Registration Error", Toast.LENGTH_SHORT);
                    }
                });
            }
            else{
                regPass.setError("Empty Fields are not allowed");
            }
        }
        else if(email.isEmpty()){
            regEmail.setError("Empty Fields are not allowed");
        }
    }
}