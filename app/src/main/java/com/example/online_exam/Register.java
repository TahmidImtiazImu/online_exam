//coooc-v git
        package com.example.online_exam;

//import androidx.annotation.NonNull;
//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Objects;

import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.internal.PickFirstLoadBalancerProvider;
//import java.util.Objects;

//import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText enter_name,email,password,confirm_password,batch,academic_year,current_semester,username;
    TextInputLayout batchinput,academicinput,semesterinput,userinput;
    RadioGroup student_teacher ,male_female;
    RadioButton male,female,teacher,student;
    Button register,go_to_login;
    ProgressBar progressbar ;
    FirebaseAuth fbase ;
   // FirebaseDatabase db = FirebaseDatabase.getInstance() ;
    //DatabaseReference reference = db.getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register Form");

        enter_name = (EditText)findViewById(R.id.enter_name) ;
        username = (EditText)findViewById(R.id.user_name) ;
        email = (EditText)findViewById(R.id.email) ;
        password = (EditText)findViewById(R.id.password) ;
        confirm_password = (EditText)findViewById(R.id.confirm_password) ;
        batch = (EditText)findViewById(R.id.batch) ;
        userinput = (TextInputLayout)findViewById(R.id.usernameinput) ;
        batchinput = (TextInputLayout)findViewById(R.id.batchinput) ;
        academic_year = (EditText)findViewById(R.id.academic_year) ;
        academicinput = (TextInputLayout) findViewById(R.id.academicinput) ;
        current_semester  = (EditText)findViewById(R.id.current_semester) ;
        semesterinput = (TextInputLayout)findViewById(R.id.semesterinput) ;
        student_teacher = (RadioGroup)findViewById(R.id.student_teacher) ;
        male = (RadioButton)findViewById(R.id.male) ;
        female = (RadioButton)findViewById(R.id.female) ;
        male_female = (RadioGroup)findViewById(R.id.male_female) ;
        teacher = (RadioButton)findViewById(R.id.teacher) ;
        student = (RadioButton)findViewById(R.id.student) ;
        register = (Button)findViewById(R.id.register) ;
        go_to_login = (Button)findViewById(R.id.go_to_login) ;
        progressbar = (ProgressBar)findViewById(R.id.simpleProgressBar) ;
        fbase = FirebaseAuth.getInstance() ;
        
//        go_to_login.setOnClickListener(v -> {
//            batchinput.setVisibility(View.VISIBLE);
//        });




        register.setOnClickListener(v -> {
            //Validating data
            String Username = username.getText().toString() ;
            String Enter_name = enter_name.getText().toString() ;
            String Email = email.getText().toString() ;
            String Password = password.getText().toString() ;
            String Confirm_password = confirm_password.getText().toString() ;
            String Batch = batch.getText().toString() ;
            String Academic_year = academic_year.getText().toString() ;
            String Current_semester = current_semester.getText().toString() ;
            String Male = male.getText().toString() ;
            String Female = female.getText().toString() ;
            String Teacher = teacher.getText().toString() ;
            String Student = student.getText().toString() ;


            if(Enter_name.isEmpty())
            {
                enter_name.setError("Name is required");
                Toast.makeText(Register.this, "name is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Username.isEmpty())
            {
                username.setError("Username is required");
                Toast.makeText(Register.this, "username is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Email.isEmpty())
            {
                email.setError("Email is required");
                Toast.makeText(Register.this, "Email is empty", Toast.LENGTH_SHORT).show();

                return;
            }
            if(Password.isEmpty())
            {
                password.setError("Password is required");
                Toast.makeText(Register.this, "Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Confirm_password.isEmpty())
            {
                confirm_password.setError("Confirm Password is required");
                Toast.makeText(Register.this, "Please confirm your password", Toast.LENGTH_SHORT).show();

                return;
            }
            if(!Password.equals(Confirm_password))
            {
                confirm_password.setError("Password doesn't match");
                Toast.makeText(Register.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!male.isChecked() && !female.isChecked())
            {
                Toast.makeText(Register.this, "Please Select your gender", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!teacher.isChecked() && !student.isChecked())
            {
                Toast.makeText(Register.this, "Please Select your role", Toast.LENGTH_SHORT).show();
                return;
            }


            if(student.isChecked() && Batch.isEmpty())
            {
                batch.setError("Batch is required");
                Toast.makeText(Register.this, "Batch is empty", Toast.LENGTH_SHORT).show();

                return;
            }
            if(student.isChecked() && Academic_year.isEmpty())
            {
                academic_year.setError("Academic Year is required");
                Toast.makeText(Register.this, "Academic year is empty", Toast.LENGTH_SHORT).show();

                return;
            }
            if(student.isChecked() && Current_semester.isEmpty())
            {
                current_semester.setError("Current Semester is required");
                Toast.makeText(Register.this, "Current Semester is empty", Toast.LENGTH_SHORT).show();
                return ;
            }
            progressbar.setVisibility(View.VISIBLE);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            Query checkuser = reference.orderByChild("Username").equalTo(Username) ;

            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        username.setError("This username is already taken");
                        return;
                    }
                    else
                    {
                        fbase.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                //userid = Objects.requireNonNull(fbase.getCurrentUser()).getUid() ;
                                HashMap<String,Object> usermap = new HashMap<>() ;

                                usermap.put("Username",Username) ;
                                usermap.put("Enter_name", Enter_name);
                                usermap.put("Email",Email) ;
                                usermap.put("Password",Password) ;
                                if(male.isChecked()) usermap.put("Gender",Male) ;
                                if(female.isChecked()) usermap.put("Gender",Female) ;
                                if(teacher.isChecked()) usermap.put("Role",Teacher) ;
                                if(student.isChecked()) {usermap.put("Role",Student) ;
                                    usermap.put("Batch",Batch) ;
                                    usermap.put("Academic_year",Academic_year) ;
                                    usermap.put("Current_semester",Current_semester) ;
                                }

                                reference.child(Username).setValue(usermap);


                                //startActivity(new Intent(getApplicationContext(),Login_form.class));
                            }
                            else {
                                Toast.makeText(Register.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//            fbase.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(task -> {
//                if(task.isSuccessful()){
//                    Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
//                    //userid = Objects.requireNonNull(fbase.getCurrentUser()).getUid() ;
//                    HashMap<String,Object> usermap = new HashMap<>() ;
//
//                    usermap.put("Username",Username) ;
//                    usermap.put("Enter_name", Enter_name);
//                    usermap.put("Email",Email) ;
//                    usermap.put("Password",Password) ;
//                    if(male.isChecked()) usermap.put("Gender",Male) ;
//                    if(female.isChecked()) usermap.put("Gender",Female) ;
//                    if(teacher.isChecked()) usermap.put("Role",Teacher) ;
//                    if(student.isChecked()) {usermap.put("Role",Student) ;
//                        usermap.put("Batch",Batch) ;
//                        usermap.put("Academic_year",Academic_year) ;
//                        usermap.put("Current_semester",Current_semester) ;
//                    }
//
//                    reference.child(Username).setValue(usermap);
//
//
//                    //startActivity(new Intent(getApplicationContext(),Login_form.class));
//                }
//                else {
//                    Toast.makeText(Register.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });


        });


    }

    public void onClickedteacherbtn(View view) {
        batchinput.setVisibility(View.GONE);
        academicinput.setVisibility(View.GONE);
        semesterinput.setVisibility(View.GONE);
    }


    public void onClickedstudentbtn(View view) {
        batchinput.setVisibility(View.VISIBLE);
        academicinput.setVisibility(View.VISIBLE);
        semesterinput.setVisibility(View.VISIBLE);
    }

    public void btn_RegisterForm(View view) {
        startActivity(new Intent(getApplicationContext(),Login_form.class));
    }
}