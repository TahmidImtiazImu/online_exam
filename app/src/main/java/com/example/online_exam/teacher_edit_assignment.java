package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class teacher_edit_assignment extends AppCompatActivity {

    String unique_assignment ;
    DatabaseReference firebaseDatabase ,fb,firebase;
    public String old_assignment_topic, old_assignment_time,old_ques_url ,old_Date,old_time;
    String edited_topic,edited_time,edited_url,Edit_date,Edit_time ;
    EditText assignment_topic,assignment_duration;
    ImageView assignment_ques,edit_ques ;
    TextView unsaved_changes , edit_upload;
    Button save_changes ;
    public String course_code,pdf_file_name;
    Uri edited_ques_uri ;
    EditText edit_time,edit_date ;
    DatePickerDialog picker;
    TimePickerDialog Picker;
    StorageReference storageReference ;
    String Unique_assignment ;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_assignment);
        this.setTitle("Edit");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Unique_assignment = getIntent().getStringExtra("Unique_assignment") ;

        assignment_topic = findViewById(R.id.edit_assignment_topic) ;
        assignment_duration = findViewById(R.id.edit_assignment_duration) ;
        assignment_ques = findViewById(R.id.edit_ques_pdf) ;
        unsaved_changes = findViewById(R.id.you_have_unsaved_changes) ;
        save_changes = findViewById(R.id.save_changes) ;
        edit_ques = findViewById(R.id.edit_view) ;
        edit_upload = findViewById(R.id.edit_upload_text);
        edit_date = findViewById(R.id.edit_date) ;
        edit_time = findViewById(R.id.edit_time) ;
        storageReference = FirebaseStorage.getInstance().getReference() ;



        firebase = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf") ;
        firebaseDatabase = firebase.child(Unique_assignment) ;
        fb = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf") ;

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {


                old_assignment_topic = Objects.requireNonNull(datasnapshot.child("Assignment_topic").getValue()).toString();
                old_assignment_time = Objects.requireNonNull(datasnapshot.child("Assignment_time").getValue()).toString();
                course_code = Objects.requireNonNull(datasnapshot.child("Course_code").getValue()).toString() ;
                pdf_file_name = Objects.requireNonNull(datasnapshot.child("pdf_file_name").getValue()).toString() ;
                old_ques_url = Objects.requireNonNull(datasnapshot.child("pdf_file_url").getValue()).toString();
                edited_url = old_ques_url ;
                old_Date = Objects.requireNonNull(datasnapshot.child("Due_date").getValue()).toString();
                old_time = Objects.requireNonNull(datasnapshot.child("Due_time").getValue()).toString() ;


                old_ques_url = Objects.requireNonNull(datasnapshot.child("pdf_file_url").getValue()).toString();

                assignment_topic.setText(old_assignment_topic);
                assignment_duration.setText(old_assignment_time);
                edit_date.setText(old_Date);
                edit_time.setText(old_time);

                assignment_ques.setOnClickListener(v -> {
                    Intent intent  =new Intent(teacher_edit_assignment.this,teacher_question_viewpdf.class) ;
                    intent.putExtra("pdf_file_url",old_ques_url) ;
                    startActivity(intent);
                });

                edit_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                        picker = new DatePickerDialog(teacher_edit_assignment.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        edit_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        picker.show();
                    }

                });
                edit_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                        int minutes = cldr.get(Calendar.MINUTE);
                        // time picker dialog
                        Picker = new TimePickerDialog(teacher_edit_assignment.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                        edit_time.setText(sHour + ":" + sMinute);
                                    }
                                }, hour, minutes, true);
                        Picker.show();
                    }
                });
                edit_time.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        unsaved_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.VISIBLE);
                    }
                });

                edit_date.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        unsaved_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.VISIBLE);
                    }
                });
                assignment_topic.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        unsaved_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.VISIBLE);
                    }
                });
                assignment_duration.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        unsaved_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.VISIBLE);
                    }
                });
                edit_upload.setEnabled(false);
                edit_ques.setOnClickListener(v ->
                        Dexter.withContext(getApplicationContext())
                                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        Intent intent = new Intent() ;
                                        intent.setType("application/pdf/*") ;
                                        intent.setAction(Intent.ACTION_GET_CONTENT) ;
                                        startActivityForResult(Intent.createChooser(intent,"Select a file"),12);
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        permissionToken.continuePermissionRequest();
                                    }
                                }).check()

                );
                System.out.println("url" + edited_ques_uri);
                edit_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unsaved_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.VISIBLE);
                        process_upload(edited_ques_uri);
                    }
                });
                save_changes.setOnClickListener(v -> {

                    edited_topic = assignment_topic.getText().toString() ;
                    edited_time = assignment_duration.getText().toString() ;
                    Edit_date = edit_date.getText().toString();
                    Edit_time = edit_time.getText().toString();

                    if(edited_time.isEmpty()){
                        assignment_topic.setError("Topic is required");
                        Toast.makeText(getApplicationContext(),"Topic can not be empty",Toast.LENGTH_SHORT).show() ;
                    }
                    if(edited_time.isEmpty()){
                        assignment_duration.setError("Duration is required");
                        Toast.makeText(getApplicationContext(),"Topic can not be empty",Toast.LENGTH_SHORT).show() ;
                    }
                    unique_assignment = course_code + edited_topic ;
//                    System.out.println("EDITE URL "+edited_url) ;
//                    System.out.println("UNIQUE "+unique_assignment);
                    HashMap<String,Object> hashMap = new HashMap<>() ;
                    hashMap.put("Assignment_time",edited_time) ;
                    hashMap.put("Assignment_topic",edited_topic) ;
                    hashMap.put("Course_code",course_code) ;
                    hashMap.put("pdf_file_name",pdf_file_name) ;
                    hashMap.put("pdf_file_url",edited_url) ;
                    hashMap.put("Due_date",Edit_date) ;
                    hashMap.put("Due_time",Edit_time);
                    fb.child(unique_assignment).updateChildren(hashMap) ;

                    if(!old_assignment_topic.equals(edited_topic)) {
                        hashMap.put("Assignment_time", "");
                        hashMap.put("Assignment_topic", "");
                        hashMap.put("Course_code", "");
                        hashMap.put("pdf_file_name", "");
                        hashMap.put("pdf_file_url", "");
                        hashMap.put("Due_date", "");
                        hashMap.put("Due_time", "");
                        firebaseDatabase.updateChildren(hashMap);
                        //firebase.child(Unique_assignment).removeValue();
                    }
                    startActivity(new Intent(teacher_edit_assignment.this,teacher_assignment_page.class));
                    Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_SHORT).show() ;

                });

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            assert data != null;
            System.out.println("hello1");
            edited_ques_uri = data.getData() ;
            edit_upload.setEnabled(true);
            System.out.println("url"+edited_ques_uri);

        }
    }
    private void process_upload(Uri Edited_ques_uri) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File uploading ...");
        pd.show();

        StorageReference reference = storageReference.child("upload/"+ UUID.randomUUID().toString()) ;
        reference.putFile(Edited_ques_uri)
                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {

                    edited_url = Objects.requireNonNull(uri).toString() ;


                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"File uploaded",Toast.LENGTH_LONG).show();



//                    student_browse_pdf.setVisibility(View.INVISIBLE);
                }))
                .addOnProgressListener(tasksnapshot -> {

                    float percent = 100 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount();
                    pd.setMessage("Uploaded :" + (int) percent + "%");

                });
    }


}