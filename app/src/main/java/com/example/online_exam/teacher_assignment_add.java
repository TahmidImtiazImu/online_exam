package com.example.online_exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Objects;

public class teacher_assignment_add extends AppCompatActivity {


    EditText select_file, assignment_topic, assignment_time;
    Button upload_file;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    public String teacher_username;
    SharedPreferences course_code_sp ;
    public  String current_course_code;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_add);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");

        course_code_sp = getApplicationContext().getSharedPreferences("course_code_prefs", Context.MODE_PRIVATE);

        current_course_code = course_code_sp.getString("Teacher_Course_Code", "");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.add_assignment);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_assignment:

                    return true;
                case R.id.people:
                    startActivity(new Intent(getApplicationContext(), teacher_assignment_people.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.dashboard:
                    startActivity(new Intent(getApplicationContext(), teacher_assignment_page.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        select_file = findViewById(R.id.select_file);
        assignment_topic = findViewById(R.id.assignment_topic);
        assignment_time = findViewById(R.id.time_duration) ;
        upload_file = findViewById(R.id.upload_file);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf");

        upload_file.setEnabled(false);

        select_file.setOnClickListener(v -> selectPdf());

    }
        private void selectPdf() {
        Intent intent = new Intent() ;
        intent.setType("application/pdf") ;
        intent.setAction(Intent.ACTION_GET_CONTENT) ;
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECTED"),12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            upload_file.setEnabled(true);
            select_file.setText(data.getDataString().substring(data.getDataString()
            .lastIndexOf("/")+1));

            upload_file.setOnClickListener(v -> uploadPDFFile(data.getData()));
        }
    }
    private void uploadPDFFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this) ;
        progressDialog.setTitle("File is loading....");
        progressDialog.show() ;

        StorageReference reference = storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete()) ;
                    Uri uri = uriTask.getResult() ;

                    String Assignment_topic = assignment_topic.getText().toString();
                    String Assignment_time = assignment_time.getText().toString();
                    String name = select_file.getText().toString();
                    String Url = Objects.requireNonNull(uri).toString() ;
                    String unique_assignment = current_course_code+Assignment_topic;
                    if(Assignment_topic.isEmpty()) {
                        assignment_topic.setError("Topic name is required");
                        Toast.makeText(teacher_assignment_add.this, "Topic name is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(Assignment_time.isEmpty()) {
                        assignment_time.setError("Time is required");
                        Toast.makeText(teacher_assignment_add.this, "Time is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                   // putPdf putPdf = new putPdf(select_file.getText().toString(), Objects.requireNonNull(uri).toString());
                    HashMap<String, String> hashMap = new HashMap<>() ;
                    hashMap.put("Assignment_topic",Assignment_topic) ;
                    hashMap.put("Assignment_time",Assignment_time) ;
                    hashMap.put("pdf_file_name",name);
                    hashMap.put("pdf_file_url",Url) ;
                    hashMap.put("Course_code",current_course_code) ;
                    databaseReference.child(unique_assignment).setValue(hashMap);
                    Toast.makeText(teacher_assignment_add.this,"File uploaded",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }).addOnProgressListener(snapshot -> {
                           double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                           progressDialog.setMessage("FIle uploaded..."+(int)progress+"%");
                });
    }
}


