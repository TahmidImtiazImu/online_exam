package com.example.online_exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.example.online_exam.teacher_adapter_assignmentlist.context;

public class teacher_assignment_add extends AppCompatActivity {


    EditText select_file, assignment_topic, assignment_time;
    Button upload_file;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    public String teacher_username;
    SharedPreferences course_code_sp ;
    public  String current_course_code;

    EditText pick_a_date ;
    DatePickerDialog picker;
    String Pick_a_date ;

    TimePickerDialog Picker;
    EditText fixed_time;
    String fixed_a_time ;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_add);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


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

        pick_a_date = findViewById(R.id.pick_a_date) ;
        pick_a_date.setInputType(InputType.TYPE_NULL);

        pick_a_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(teacher_assignment_add.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                pick_a_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        fixed_time=(EditText) findViewById(R.id.enter_time);
        fixed_time.setInputType(InputType.TYPE_NULL);
        fixed_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                Picker = new TimePickerDialog(teacher_assignment_add.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                fixed_time.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                Picker.show();
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf");

        upload_file.setEnabled(false);

        select_file.setOnClickListener(v -> selectPdf());

    }
        private void selectPdf() {
        Intent intent = new Intent() ;
        intent.setType("application/pdf/*") ;
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

        StorageReference reference = storageReference.child("upload/"+ UUID.randomUUID().toString());
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri uri = uriTask.getResult() ;

                    String Assignment_topic = assignment_topic.getText().toString();
                    String Assignment_time = assignment_time.getText().toString();
                    Pick_a_date = pick_a_date.getText().toString();
                    fixed_a_time = fixed_time.getText().toString();
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
                        Toast.makeText(teacher_assignment_add.this, "Assignment Time is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(Pick_a_date.isEmpty()) {
                        pick_a_date.setError("Time is required");
                        Toast.makeText(teacher_assignment_add.this, "Date is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(fixed_a_time.isEmpty()) {
                        fixed_time.setError("Time is required");
                        Toast.makeText(teacher_assignment_add.this, "Time is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                   // putPdf putPdf = new putPdf(select_file.getText().toString(), Objects.requireNonNull(uri).toString());
                    HashMap<String, String> hashMap = new HashMap<>() ;
                    hashMap.put("Assignment_topic",Assignment_topic) ;
                    hashMap.put("Assignment_time",Assignment_time) ;
                    hashMap.put("pdf_file_name",unique_assignment);
                    hashMap.put("pdf_file_url",Url) ;
                    hashMap.put("Course_code",current_course_code) ;
                    hashMap.put("Due_date",Pick_a_date) ;
                    hashMap.put("Due_time",fixed_a_time) ;
                    databaseReference.child(unique_assignment).setValue(hashMap);
                    Toast.makeText(teacher_assignment_add.this,"File uploaded",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context,teacher_assignment_page.class));
                    progressDialog.dismiss();

                }).addOnProgressListener(snapshot -> {
                           double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                           progressDialog.setMessage("FIle uploaded..."+(int)progress+"%");
                });
    }
}


