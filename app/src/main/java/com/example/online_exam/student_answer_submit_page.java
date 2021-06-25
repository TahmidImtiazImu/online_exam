package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import static com.example.online_exam.student_adapter_assignmentlist.context;

public class student_answer_submit_page extends AppCompatActivity {

    TextView student_assignment_topic, student_assignment_duration ;
    ImageView student_loading_ques ;
    ImageView student_browse_pdf, student_ans_pdf,student_sub_cancel ;

    StorageReference storageReference ;
    DatabaseReference databaseReference ;
    Uri student_answer_url ;
    Button ans_submit ;
    public String student_course_code ;
    public String student_username ;
    public String assignment_topic ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer_submit_page);

        assignment_topic = getIntent().getStringExtra("Assignment_topic");
        String assignment_duration = getIntent().getStringExtra("Assignment_time");
        String ques_pdf_url = getIntent().getStringExtra("ques_url") ;

        student_assignment_topic = findViewById(R.id.student_assignment_topic_name2) ;
        student_assignment_duration = findViewById(R.id.student_assignment_duration) ;
        student_loading_ques = findViewById(R.id.student_loading_ques) ;
        student_browse_pdf = findViewById(R.id.student_browse_answer) ;
        student_ans_pdf = findViewById(R.id.student_ans) ;
        student_sub_cancel = findViewById(R.id.cancel_icon);
        ans_submit = findViewById(R.id.ans_sub_button) ;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("student_upload_answer") ;

        student_assignment_topic.setText(assignment_topic);
        student_assignment_duration.setText(assignment_duration);

        SharedPreferences sp =getApplicationContext().getSharedPreferences("student_course_prefs",context.MODE_PRIVATE);
        student_course_code = sp.getString("Student_Course_Code","");

        SharedPreferences user_sp =getApplicationContext().getSharedPreferences("UserPrefs",context.MODE_PRIVATE);
        student_username = user_sp.getString("UserName","");



        student_loading_ques.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),student_question_viewpdf.class) ;
            intent.putExtra("ques_url",ques_pdf_url);
            startActivity(intent);
        });

        student_sub_cancel.setOnClickListener(v -> {
            student_sub_cancel.setVisibility(View.INVISIBLE);
            student_ans_pdf.setVisibility(View.INVISIBLE);
            student_browse_pdf.setVisibility(View.VISIBLE);
        });

        student_browse_pdf.setOnClickListener(v ->
                 Dexter.withContext(getApplicationContext())
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent() ;
                        intent.setType("application/pdf") ;
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
        ans_submit.setOnClickListener(v -> process_upload(student_answer_url));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK)
        {
            assert data != null;
            student_answer_url = data.getData() ;
            student_sub_cancel.setVisibility(View.VISIBLE);
            student_ans_pdf.setVisibility(View.VISIBLE);
            student_browse_pdf.setVisibility(View.INVISIBLE);
        }
    }

    private void process_upload(Uri student_answer_url) {

        ProgressDialog pd = new ProgressDialog(this) ;
        pd.setTitle("File uploading ...");
        pd.show() ;

        StorageReference reference = storageReference.child("Ans_upload/"+System.currentTimeMillis()+".pdf") ;
        reference.putFile(student_answer_url)
                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String Answer_Url = Objects.requireNonNull(uri).toString() ;
                    String unique_answer_upload = student_username + student_course_code + assignment_topic ;
                    HashMap<String, String> hashMap = new HashMap<>() ;
                    hashMap.put("Student_answer_url",Answer_Url) ;

                    hashMap.put("pdf_file_name",unique_answer_upload);

                    databaseReference.child(unique_answer_upload).setValue(hashMap);

                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"File uploaded",Toast.LENGTH_LONG).show();

                    student_sub_cancel.setVisibility(View.VISIBLE);
                    student_ans_pdf.setVisibility(View.VISIBLE);
                    student_browse_pdf.setVisibility(View.INVISIBLE);
                }))
                .addOnProgressListener(tasksnapshot -> {
                  float percent = 100*tasksnapshot.getBytesTransferred()/tasksnapshot.getTotalByteCount() ;
                  pd.setMessage("Uploaded :" + (int)percent +"%");

                });
    }

}