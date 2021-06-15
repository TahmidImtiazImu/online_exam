package com.example.online_exam;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class teacher_assignment_add extends AppCompatActivity {

    String teacher_username;

    EditText select_file,assignment_topic;
    Button upload_file ;

    StorageReference storageReference ;
    DatabaseReference databaseReference ;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_add);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment");

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        teacher_username = sp.getString("UserName", "");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation) ;

        bottomNavigationView.setSelectedItemId(R.id.add_assignment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.add_assignment:

                        return true;
                    case R.id.people:
                        startActivity(new Intent(getApplicationContext(),teacher_assignment_people.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),teacher_assignment_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        select_file = findViewById(R.id.select_file) ;
        assignment_topic = findViewById(R.id.assignment_topic) ;
        upload_file = findViewById(R.id.upload_file) ;

        storageReference = FirebaseStorage.getInstance().getReference() ;
        databaseReference = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf");

        upload_file.setEnabled(false);

        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  selectPdf();
            }
        });

    }

//    private void selectPdf() {
//        Intent intent = new Intent() ;
//        intent.setType("application/pdf") ;
//        intent.setAction(Intent.ACTION_GET_CONTENT) ;
//        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECTED"),12);
//
//    }
//    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//      @Override
//      public void onActivityResult(int requestCode, int resultcode, @javax.annotation.Nullable Intent data) {
//          teacher_assignment_add.super.onActivityResult(requestCode,resultcode,data);
//
//
//      }
//  });


//   @Override
//    protected  void onActivityResult(int requestCode, int resultcode, @Nullable Intent data){
//        super.onActivityResult(requestCode,requestCode,data);
//
//   }
}