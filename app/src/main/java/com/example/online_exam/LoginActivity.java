package com.example.online_exam;

//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//import org.jetbrains.annotations.NotNull;
//
//public class LoginActivity extends AppCompatActivity {
//
//    EditText logUser, logPass;
//    Button logInBtn;
//    TextView forgotPass, txtSignUp;
//    FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        logUser = findViewById(R.id.input_Username);
//        logPass = findViewById(R.id.input_Password);
//        logInBtn = findViewById(R.id.btnLogIn);
//        forgotPass = findViewById(R.id.txtForgotPassword);
//        txtSignUp = findViewById(R.id.txtSignUp);
//        mAuth = FirebaseAuth.getInstance();
//
//        txtSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SignUp.class));
//                finish();
//            }
//        });
//
//        logInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logInUser();
//            }
//        });
//    }
//
//    private void logInUser(){
//        String email = logUser.getText().toString(); //authentication should be done with username but here is Email
//        String pass = logPass.getText().toString();
//        if((!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() )){
//            if(!pass.isEmpty() ) {
//                mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Toast.makeText( LoginActivity.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, teacher_homepage.class));
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        Toast.makeText( LoginActivity.this, "EMail or Password is Incorrect!!", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            else{
//                logPass.setError("Empty Fields are not allowed");
//            }
//        }
//        else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            logUser.setError("Empty Fields are not allowed(Email)");
//            Toast.makeText( LoginActivity.this, "give a valid Email ID!!", Toast.LENGTH_LONG).show();
//        }
//    }
//}


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //TextInputLayout usernameinput, password_input,email_input ;
    EditText username,password,email ;
    RadioButton teacher,student;
    RadioGroup role ;
    Button log_in ;
    ProgressBar progressBar ;
    FirebaseAuth fauth ;

    Intent usernameIntent;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Log In");

        //usernameinput = (TextInputLayout)findViewById(R.id.usernameinput) ;
        //password_input = (TextInputLayout)findViewById(R.id.passwordinput) ;
        // email_input = (TextInputLayout)findViewById(R.id.emailinput);
        username = (EditText)findViewById(R.id.username) ;
        //email = (EditText)findViewById(R.id.email) ;
        password = (EditText)findViewById(R.id.password) ;
        //teacher = (RadioButton) findViewById(R.id.teacher) ;
        //student = (RadioButton) findViewById(R.id.student) ;
        log_in = (Button) findViewById(R.id.login) ;
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar) ;
        fauth = FirebaseAuth.getInstance() ;

        sp = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        log_in.setOnClickListener(v -> {
            String Userentername = Objects.requireNonNull(username.getText().toString()) ;
            // String Userenteremail = email_input.getEditText().getText().toString();

            String UserenterPassword = Objects.requireNonNull(password.getText().toString());

            if(Userentername.isEmpty()) {
                username.setError("Username is required");
                Toast.makeText(LoginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                return ;
            }
            if (UserenterPassword.isEmpty()) {
                password.setError("Password is required");
                Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
//            if(UserenterPassword.isEmpty()){
//                email.setError("Email is required");
//                Toast.makeText(Login_form.this, "Email is empty", Toast.LENGTH_SHORT).show();
//            }

            progressBar.setVisibility(View.VISIBLE);

//                fauth.signInWithEmailAndPassword(Userenteremail, UserenterPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // final String username = username.geteditText
//                            Toast.makeText(Login_form.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//                            Toast.makeText(Login_form.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            //finish();
//                        }
//                    }
//                });

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            Query checkuser = reference.orderByChild("Username").equalTo(Userentername) ;

            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        username.setError(null);
                        //username.setErrorEnabled(false);
                        String passdb = dataSnapshot.child(Userentername).child("Password").getValue(String.class) ;
                        assert passdb != null;
                        if(passdb.equals(UserenterPassword)){

                            username.setError(null);
                            //usernameinput.setErrorEnabled(false);

                            SharedPreferences.Editor editor = sp.edit();

                            String Role = dataSnapshot.child(Userentername).child("Role").getValue(String.class) ;
                            assert Role != null;
                            if(Role.equals("Teacher"))    {
                                usernameIntent = new Intent(LoginActivity.this ,teacher_homepage.class);
                                startActivity(usernameIntent);

                                editor.putString("UserName", Userentername);
                                editor.commit();
                                //usernameIntent.putExtra("currentUsername",Userentername);

                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            }
                            else    {
                                usernameIntent = new Intent(LoginActivity.this,student_homepage.class);
                                startActivity(usernameIntent);

                                editor.putString("UserName", Userentername);
                                editor.commit();
                                //usernameIntent.putExtra("currentUsername",Userentername);

                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            password.setError("Wrong Password");
                        }
                    }
                    else
                    {
                        username.setError("No such User exist");
                        username.requestFocus() ;
                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });




        });
    }
    public void lettheuserloggedin(View view){

    }

    public void btn_login(View view) {

    }

    public void btn_RegisterForm(View view) {
        startActivity(new Intent(getApplicationContext(),Register.class));
    }


}