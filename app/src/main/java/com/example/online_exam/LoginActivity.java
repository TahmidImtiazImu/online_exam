package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.example.online_exam.Register.TAG;

public class LoginActivity extends AppCompatActivity {

    Dialog myDialog;

    TextInputLayout usernameinput, password_input,email_input ;
    EditText username,password ;
    TextView txtSignUp, txtForgetPassword;
    //RadioButton teacher,student;
    RadioGroup role ;
    Button log_in, resendBtn;
    ProgressBar progressBar ;
    FirebaseAuth fauth ;
    FirebaseUser fUser;

    Intent usernameIntent;

    SharedPreferences sp;

    String _EMAIL, _PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Log In"); //maybe smth wrong

        usernameinput = (TextInputLayout)findViewById(R.id.layoutUser) ;
        password_input = (TextInputLayout)findViewById(R.id.layoutPass) ;
        // email_input = (TextInputLayout)findViewById(R.id.emailinput);
        //username = (EditText)findViewById(R.id.username) ;
        //email = (EditText)findViewById(R.id.email) ;
        //password = (EditText)findViewById(R.id.password) ;
        //teacher = (RadioButton) findViewById(R.id.teacher) ;
        //student = (RadioButton) findViewById(R.id.student) ;
        txtSignUp = findViewById(R.id.txtSignUp);
        txtForgetPassword = findViewById(R.id.txtForgotPassword);
        log_in = (Button) findViewById(R.id.btnLogin) ;
        resendBtn = findViewById(R.id.resend);
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar) ;
        fauth = FirebaseAuth.getInstance() ;
        //fUser = fauth.getCurrentUser();
        myDialog = new Dialog(this);

        sp = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);


        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });


        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                finish();
            }
        });

        log_in.setOnClickListener(v -> {
            String Userentername = Objects.requireNonNull(usernameinput.getEditText()).getText().toString() ;
            // String Userenteremail = email_input.getEditText().getText().toString();

            String UserenterPassword = Objects.requireNonNull(password_input.getEditText()).getText().toString();

            if(Userentername.isEmpty()) {
                usernameinput.setError("Username is required");

                Toast.makeText(LoginActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();

                Toast.makeText(LoginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();

                return ;
            }
            if (UserenterPassword.isEmpty()) {
                password_input.setError("Password is required");
                Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            }


            progressBar.setVisibility(View.VISIBLE);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            Query checkuser = reference.orderByChild("Username").equalTo(Userentername) ;

            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        usernameinput.setError(null);
                        usernameinput.setErrorEnabled(false);

                        _EMAIL = dataSnapshot.child(Userentername).child("Email").getValue(String.class);
                        _PASSWORD = dataSnapshot.child(Userentername).child("Password").getValue(String.class);

                        fauth.signInWithEmailAndPassword(_EMAIL, UserenterPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    fUser = fauth.getCurrentUser();
                                    if(fUser.isEmailVerified()){
                                        SharedPreferences.Editor editor = sp.edit();
                                        String Role = dataSnapshot.child(Userentername).child("Role").getValue(String.class);
                                        assert Role != null;
                                        if (Role.equals("Teacher")) {
                                            usernameIntent = new Intent(LoginActivity.this, teacher_homepage.class);
                                            startActivity(usernameIntent);

                                            editor.putString("UserName", Userentername);
                                            editor.commit();
                                            //usernameIntent.putExtra("currentUsername",Userentername);

                                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            finish();

                                        } else {
                                            usernameIntent = new Intent(LoginActivity.this, student_homepage.class);
                                            startActivity(usernameIntent);

                                            editor.putString("UserName", Userentername);
                                            editor.commit();
                                            //usernameIntent.putExtra("currentUsername",Userentername);

                                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                    }

                                    else{
                                        usernameinput.setError("Verification Pending");
                                        usernameinput.requestFocus();
                                        ShowPopUp();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                                else
                                {
                                    password_input.setError("Wrong Password");
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

//                        fUser = fauth.getCurrentUser();
//                        if(fUser != null)
//                        {
//                            String fUserEmail = fUser.getEmail();
//                            String fUserName = fUser.getUid();
//                            System.out.println("Email is: " + fUserEmail+ fUserName);
//                        }
//                        if (fUser.isEmailVerified()) {
//                            //Not Verified
////                        if(!fUser.isEmailVerified()) {
////                            ShowPopUp();
////                            System.out.println("isEmailVerified");
////                            Toast.makeText(LoginActivity.this, "Email Verifying", Toast.LENGTH_SHORT).show();
////                        }
//
//                            String passdb = dataSnapshot.child(Userentername).child("Password").getValue(String.class);
//                            assert passdb != null;
//                            if (passdb.equals(UserenterPassword)) {
//
//                                usernameinput.setError(null);
//                                usernameinput.setErrorEnabled(false);
//
//                                SharedPreferences.Editor editor = sp.edit();
//
//                                String Role = dataSnapshot.child(Userentername).child("Role").getValue(String.class);
//                                assert Role != null;
//                                if (Role.equals("Teacher")) {
//                                    usernameIntent = new Intent(LoginActivity.this, teacher_homepage.class);
//                                    startActivity(usernameIntent);
//
//                                    editor.putString("UserName", Userentername);
//                                    editor.commit();
//                                    //usernameIntent.putExtra("currentUsername",Userentername);
//
//                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    usernameIntent = new Intent(LoginActivity.this, student_homepage.class);
//                                    startActivity(usernameIntent);
//
//                                    editor.putString("UserName", Userentername);
//                                    editor.commit();
//                                    //usernameIntent.putExtra("currentUsername",Userentername);
//
//                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//
//                                }
                            }
//                    else {
//                                password_input.setError("Wrong Password");
//                            }
//                        } else {
//                            usernameinput.setError("Verification Pending");
//                            usernameinput.requestFocus();
//                            ShowPopUp();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
                    else
                    {
                        usernameinput.setError("No Such User Exist");
                        usernameinput.requestFocus() ;
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });

        });

    }
    public void ShowPopUp(){
        System.out.println("showPopUp Called!");
        Button resendBtn;
        myDialog.setContentView(R.layout.activity_popup);
        resendBtn = myDialog.findViewById(R.id.btnResend);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser fUser = fauth.getCurrentUser();
                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Verification Email Sent!", Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d( TAG, "onFailure:Email not Sent" + e.getMessage() );
                    }
                });
            }
        });
    }

}