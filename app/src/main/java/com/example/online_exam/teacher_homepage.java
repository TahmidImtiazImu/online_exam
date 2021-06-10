package com.example.online_exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class teacher_homepage extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelCourseList>coursesList;
    AdapterCourseList adapter;
    Button logout ;

    String Course_name;
    String Course_code;

    int course_count;

    String[] courseNameArray, courseCodeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homepage);
        // this.setTitle("Homepage");
        getSupportActionBar().setTitle("Teacher_Homepage");
        logout = (Button)findViewById(R.id.logout) ;

        initData();
        initRecyclerView();

    }

    private void new_created_course() {

        boolean isItHere = getIntent().getBooleanExtra("isThereCourse",false);

        if(isItHere == true) {

            Course_name = getIntent().getStringExtra("passed_course_name");
            Course_code = getIntent().getStringExtra("passed_course_code");

            courseNameArray[course_count] = Course_name;
            courseCodeArray[course_count] = Course_code;

            coursesList.add(new ModelCourseList(Course_name,Course_code));
        }

    }

    private void initData() {

        coursesList = new ArrayList<>();

        courseNameArray = new String[100];
        courseCodeArray = new String[100];

        //coursesList.add(new ModelCourseList("CSE","4321"));

        int travers = 0;

        course_count = 0;

        while(courseNameArray[travers] != null) {

            travers++;
            course_count++;

            coursesList.add(new ModelCourseList(courseNameArray[travers],courseCodeArray[travers]));
        }

        new_created_course();

    }

    private void initRecyclerView() {

        recyclerView=findViewById(R.id.coursesRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new AdapterCourseList(coursesList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.course_create:
                Toast.makeText(this, "Create you course", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),CourseCreatingActivity.class));
                return true;

            case R.id.inst:
                Toast.makeText(this, "Instruction selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),InstructActivity.class));
                return true;

            case R.id.log:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
    public void btn_logout(View view) {
        startActivity(new Intent(getApplicationContext(),Login_form.class));
    }

    public void btn_assignment(View view) {
        startActivity(new Intent(getApplicationContext(),teacher_assignment_page.class));
    }

}

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class teacher_homepage extends AppCompatActivity {
//
//    Button logout ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teacher_homepage);
//        getSupportActionBar().setTitle("Teacher_Homepage");
//        logout = (Button)findViewById(R.id.logout) ;
//
//
//    }
//
//    public void btn_logout(View view) {
//        startActivity(new Intent(getApplicationContext(),Login_form.class));
//    }
//
//    public void btn_assignment(View view) {
//        startActivity(new Intent(getApplicationContext(),teacher_assignment_page.class));
//    }
//}