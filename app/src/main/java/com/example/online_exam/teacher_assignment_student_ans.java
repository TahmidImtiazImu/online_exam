package com.example.online_exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacher_assignment_student_ans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_assignment_student_ans extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    public List<teacher_assignment_student_anslist> ans_List;
    teacher_assignment_student_ans_adapter adapter;

    public FirebaseDatabase db = FirebaseDatabase.getInstance();
    public DatabaseReference root = db.getReference("joined_courses");

    SharedPreferences course_code_sp ;

    String course_code ;
    String current_user ;
    String student_name ;


    public teacher_assignment_student_ans() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teacher_assignment_student_ans.
     */
    // TODO: Rename and change types and number of parameters
    public static teacher_assignment_student_ans newInstance(String param1, String param2) {
        teacher_assignment_student_ans fragment = new teacher_assignment_student_ans();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState) ;
        View view ;
        view = inflater.inflate(R.layout.fragment_teacher_assignment_student_ans, container, false);

        ans_List= new ArrayList<>();

        recyclerView=view.findViewById(R.id.all_student_answers);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new teacher_assignment_student_ans_adapter(ans_List, getContext());
        recyclerView.setAdapter(adapter);

        course_code_sp = getApplicationContext().getSharedPreferences("course_code_prefs", Context.MODE_PRIVATE);
        final String  Course_code = course_code_sp.getString("Teacher_Course_Code","");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    course_code = dataSnapshot.child("courseCode").getValue(String.class);

                    assert course_code != null;
                    if(course_code.equals(Course_code)) {
//                        System.out.println(course_code);
//                        System.out.println(Course_code);
                        student_name = dataSnapshot.child("student_name").getValue(String.class);
                        current_user= dataSnapshot.child("currentUser").getValue(String.class);

//                        System.out.println(current_user);
//                        System.out.println(student_name);
                        ans_List.add(new teacher_assignment_student_anslist( student_name,current_user));
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view ;
    }
}