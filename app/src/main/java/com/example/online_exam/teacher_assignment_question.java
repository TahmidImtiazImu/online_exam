package com.example.online_exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import static com.example.online_exam.teacher_adapter_assignmentlist.context;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacher_assignment_question#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_assignment_question extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView pdf_logo ;
    ImageView edit_menu;
    TextView ass_topic,ass_time,cour_code,pdf_url;
    SharedPreferences course_code_sp ,assignemnt_topic_sp;
    DatabaseReference fb ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_assignment_question() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teacher_assignment_question.
     */
    // TODO: Rename and change types and number of parameters
    public static teacher_assignment_question newInstance(String param1, String param2) {
        teacher_assignment_question fragment = new teacher_assignment_question();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view ;
        view = inflater.inflate(R.layout.fragment_teacher_assignment_question, container, false);
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_teacher_assignment_question, container, false);

        pdf_logo = view.findViewById(R.id.pdf_logo) ;
        ass_time = view.findViewById(R.id.ques_assignment_time) ;
        ass_topic = view.findViewById(R.id.ques_assignment_topic) ;
        cour_code = view.findViewById(R.id.ques_course_code) ;
        pdf_url = view.findViewById(R.id.ques_pdf_file_url) ;
        edit_menu = view.findViewById(R.id.edit_and_delete) ;



        course_code_sp = getApplicationContext().getSharedPreferences("course_code_prefs", Context.MODE_PRIVATE);
        String course_code = course_code_sp.getString("Teacher_Course_Code","");

        assignemnt_topic_sp = getApplicationContext().getSharedPreferences("Assignment_topic_pref", Context.MODE_PRIVATE);

        String assignment_topic = assignemnt_topic_sp.getString("Assignment_topic","");


        String name_merge = course_code + assignment_topic ;
        System.out.println(course_code);
        System.out.println(name_merge);

        fb = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf").child(name_merge) ;

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                String assignment_topic = Objects.requireNonNull(datasnapshot.child("Assignment_topic").getValue()).toString() ;
                String assignment_time = Objects.requireNonNull(datasnapshot.child("Assignment_time").getValue()).toString() ;
                String course_code = Objects.requireNonNull(datasnapshot.child("Course_code").getValue()).toString() ;
                String Pdf_url = Objects.requireNonNull(datasnapshot.child("pdf_file_url").getValue()).toString() ;

                ass_time.setText(assignment_time) ;
                ass_topic.setText(assignment_topic) ;
                cour_code.setText(course_code) ;
                pdf_url.setText(Pdf_url) ;

                pdf_logo.setOnClickListener(v -> {

                    Intent intent = new Intent(context,teacher_question_viewpdf.class);
                    intent.putExtra("pdf_file_url",Pdf_url) ;
                    startActivity(intent);

                });
                edit_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(context,v) ;
                        popupMenu.getMenuInflater().inflate(R.menu.edit_menu,popupMenu.getMenu());
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @SuppressLint("NonConstantResourceId")
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId())
                                {
                                    case R.id.Edit_item:
                                        Intent intent = new Intent(context,teacher_edit_assignment.class);
                                        intent.putExtra("Unique_assignment",name_merge);
                                        context.startActivity(intent);
                                        break;
                                    case R.id.delete_item:
                                        HashMap<String,Object> data = new HashMap<>();

                                        data.put("Assignment_topic","");
                                        data.put("Assignment_time","");
                                        data.put("Course_code","IMU");
                                        data.put("pdf_file_name","");
                                        data.put("pdf_file_url","");
                                        fb.updateChildren(data) ;

                                        //myRef.child(user.getUser_id()).updateChildren(data);


                                        Intent intent2 = new Intent(context,teacher_assignment_page.class);
                                        context.startActivity(intent2);



                                }
                                return true ;
                            }
                        });

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



        return view ;
    }


}