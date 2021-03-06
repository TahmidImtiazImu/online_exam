package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.example.online_exam.Register.TAG;
import static com.example.online_exam.student_adapter_assignmentlist.context;

public class student_answer_submit_page extends AppCompatActivity {

    TextView student_assignment_topic, student_assignment_duration ;
    ImageView student_loading_ques ;
    ImageView student_browse_pdf, student_ans_pdf,student_sub_cancel ;
    TextView ans_submit ;
    StorageReference storageReference ;
    DatabaseReference databaseReference ;
    Uri student_answer_url ;
    Button  hand_in,submitted,time_out;
    public String student_course_code ;
    public String student_username ;
    public String assignment_topic ;
    public String is_handed_in = "false";
    public String Answer_Url ;
    public String unique_answer_upload;
    public String node ;
    public String pdf_file_name ;
    public String Unique_answer_upload;
    public String due_date ;
    public String due_time ;
    String date ;
    Date date1;
    Date date2 ;
    Date time1;
    Date time2;
    String Unique_ques_upload ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer_submit_page);
        this.setTitle("Answer submit");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent intent1 = new Intent(getApplicationContext(), student_assignment_page.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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
        hand_in = findViewById(R.id.hand_in) ;
        submitted = findViewById(R.id.submitted) ;
        time_out = findViewById(R.id.time_out) ;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("student_upload_answer") ;

        hand_in.setBackgroundResource(R.drawable.round_bg);

        student_assignment_topic.setText(assignment_topic);
        student_assignment_duration.setText(assignment_duration);

        SharedPreferences sp =getApplicationContext().getSharedPreferences("student_course_prefs",context.MODE_PRIVATE);
        student_course_code = sp.getString("Student_Course_Code","");

        SharedPreferences user_sp =getApplicationContext().getSharedPreferences("UserPrefs",context.MODE_PRIVATE);
        student_username = user_sp.getString("UserName","");


        Unique_answer_upload = student_username + student_course_code + assignment_topic ;
        System.out.println("student username  " + student_username);
        System.out.println(" student course code " + student_course_code);
        System.out.println(" topic " + assignment_topic);
        System.out.println("unique node    "+Unique_answer_upload);

        Unique_ques_upload = student_course_code + assignment_topic ;

        FirebaseDatabase database = FirebaseDatabase.getInstance() ;
        DatabaseReference myref= database.getReference("teacher_uploadPdf").child(Unique_ques_upload);

        myref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                Date today_date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.format(today_date);
                System.out.println(date);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String time = sdf.format(new Date());

                due_date = dataSnapshot.child("Due_date").getValue(String.class) ;
                due_time = dataSnapshot.child("Due_time").getValue(String.class) ;



                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy") ;
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm") ;

                try {
                    time1 = format2.parse(time) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    time2 = format2.parse(due_time) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("TAG","Due date" + due_time) ;
                Log.d("TAG","real date" + time) ;

                try {
                    date1 = format1.parse(date) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = format1.parse(due_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(date1.compareTo(date2) > 0)
                {
                    hand_in.setVisibility(View.VISIBLE);
                    ans_submit.setVisibility(View.GONE);
                    hand_in.setVisibility(View.GONE);
                    submitted.setVisibility(View.GONE);
                    time_out.setVisibility(View.VISIBLE);

                }
                else if(date1.compareTo(date2)==0){
                    if(time1.compareTo(time2)>0) {
                        hand_in.setVisibility(View.VISIBLE);
                        ans_submit.setVisibility(View.GONE);
                        hand_in.setVisibility(View.GONE);
                        submitted.setVisibility(View.GONE);
                        time_out.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        ans_submit.setEnabled(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint({"SimpleDateFormat", "SetTextI18n", "ResourceAsColor"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    pdf_file_name = dataSnapshot.child("pdf_file_name").getValue(String.class) ;

                    if(Unique_answer_upload.equals(pdf_file_name)) {

                        node = dataSnapshot.child("Student_answer_url").getValue(String.class) ;
//                        System.out.println(pdf_file_name);
//                        System.out.println(node);
                        String handed = dataSnapshot.child("is_handed_in").getValue(String.class) ;
                        System.out.println(handed);
                        assert handed != null;
                        if(handed.equals("true")){
                            student_sub_cancel.setVisibility(View.INVISIBLE);
                            student_ans_pdf.setVisibility(View.VISIBLE);
                            ans_submit.setVisibility(View.GONE);
                            hand_in.setVisibility(View.GONE);
                            submitted.setVisibility(View.VISIBLE);
                            time_out.setVisibility(View.GONE);

                            return;
                        }
                        else{
                            student_sub_cancel.setVisibility(View.VISIBLE);
                            ans_submit.setVisibility(View.VISIBLE);
                            student_ans_pdf.setVisibility(View.VISIBLE);
                            student_browse_pdf.setVisibility(View.INVISIBLE);
                            ans_submit.setEnabled(false);
                            hand_in.setVisibility(View.VISIBLE);
                            hand_in.setEnabled(true);
                            return;
                        }
//                       student_sub_cancel.setVisibility(View.INVISIBLE);
//                       student_ans_pdf.setVisibility(View.VISIBLE);
//                       ans_submit.setEnabled(false);
//                       student_browse_pdf.setVisibility(View.INVISIBLE);
//                       hand_in.setVisibility(View.VISIBLE);
//                       hand_in.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        student_loading_ques.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),student_question_viewpdf.class) ;
            intent.putExtra("ques_url",ques_pdf_url);
            startActivity(intent);
        });

        student_sub_cancel.setOnClickListener(v -> {
            student_sub_cancel.setVisibility(View.INVISIBLE);
            student_ans_pdf.setVisibility(View.INVISIBLE);
            student_browse_pdf.setVisibility(View.VISIBLE);
            ans_submit.setEnabled(false);
        });

        student_browse_pdf.setOnClickListener(v ->
                Dexter.withContext(getApplicationContext())
                        .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent() ;
                                intent.setType("application/pdf/*") ;
                                intent.setAction(Intent.ACTION_GET_CONTENT) ;
                                startActivityForResult(Intent.createChooser(intent,"Select a file"),12);
                                ans_submit.setEnabled(true);
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
        ans_submit.setOnClickListener(v -> process_upload(student_answer_url,is_handed_in));

        student_ans_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String node = dataSnapshot.child("pdf_file_name").getValue(String.class) ;
                            if(Unique_answer_upload.equals(node)) {
                                String pdf_url = dataSnapshot.child("Student_answer_url").getValue(String.class) ;


                                Intent intent = new Intent(context,student_answer_view.class) ;
                                intent.putExtra("my_answer",pdf_url) ;
                                context.startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        hand_in.setOnClickListener(v -> {
            student_sub_cancel.setVisibility(View.INVISIBLE);
            ans_submit.setVisibility(View.GONE);


            is_handed_in="true" ;
            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("student_upload_answer");
            HashMap<Object, Object> hashMap = new HashMap<>() ;
          //  Map<String, Object> hashMap = new HashMap<String,Object>();
            hashMap.put("Student_answer_url","") ;
            hashMap.put("pdf_file_name","") ;
            hashMap.put("is_handed_in","");
            databaseReference2.child(pdf_file_name).setValue(hashMap) ;
            hashMap.put("Student_answer_url",node) ;
            hashMap.put("pdf_file_name",pdf_file_name) ;
            hashMap.put("is_handed_in",is_handed_in);
//            System.out.println("handed in"+is_handed_in);
//            System.out.println("imu"+pdf_file_name);

            databaseReference2.child(pdf_file_name).setValue(hashMap);

            hand_in.setVisibility(View.GONE);
            submitted.setVisibility(View.VISIBLE);
            time_out.setVisibility(View.GONE);




        });


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

    private void process_upload(Uri student_answer_url,String Is_handed_in) {
        ProgressDialog pd = new ProgressDialog(this);
        if(Is_handed_in.equals("false")) {
            pd.setTitle("File uploading ...");
            pd.show();
            StorageReference reference = storageReference.child("Ans_upload/"+ UUID.randomUUID().toString()) ;
            reference.putFile(student_answer_url)
                    .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        Answer_Url = Objects.requireNonNull(uri).toString() ;
                        unique_answer_upload = student_username + student_course_code + assignment_topic ;

                        HashMap<String, String> hashMap = new HashMap<>() ;
                        hashMap.put("Student_answer_url",Answer_Url) ;

                        hashMap.put("pdf_file_name",unique_answer_upload);

                        hashMap.put("is_handed_in",Is_handed_in);

                        databaseReference.child(unique_answer_upload).setValue(hashMap);

                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"File uploaded",Toast.LENGTH_LONG).show();

                        student_sub_cancel.setVisibility(View.VISIBLE);
                        student_ans_pdf.setVisibility(View.VISIBLE);
                        hand_in.setVisibility(View.VISIBLE);



//                    student_browse_pdf.setVisibility(View.INVISIBLE);
                    }))
                    .addOnProgressListener(tasksnapshot -> {
                        if(is_handed_in=="false") {
                            float percent = 100 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount();
                            pd.setMessage("Uploaded :" + (int) percent + "%");
                        }

                    });
        }
        else
        {
//            String is_handed_in = "true" ;
//            HashMap<String, String> hashMap = new HashMap<>() ;
//
//            hashMap.put("Student_answer_url",node) ;
//            hashMap.put("pdf_file_name",pdf_file_name) ;
//            hashMap.put("is_handed_in",is_handed_in);
//
//
//            databaseReference.child(unique_answer_upload).setValue(hashMap);
//            pd.setTitle("File submitting ...");
//            pd.show();
//            pd.dismiss();
        }

    }

}