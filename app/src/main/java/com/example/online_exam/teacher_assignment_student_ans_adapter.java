package com.example.online_exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.online_exam.student_adapter_assignmentlist.context;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class teacher_assignment_student_ans_adapter extends RecyclerView.Adapter<teacher_assignment_student_ans_adapter.ViewHolder> {

    private final List<teacher_assignment_student_anslist> ans_List;
    private Context context;

    public teacher_assignment_student_ans_adapter(List<teacher_assignment_student_anslist>ans_List, Context context) {

        this.ans_List=ans_List;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public teacher_assignment_student_ans_adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_student_answer_view_in_teacherpage,parent,false);
        return new teacher_assignment_student_ans_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull teacher_assignment_student_ans_adapter.ViewHolder holder, int position) {
        String student_name = ans_List.get(position).getAns_student_name() ;
        String student_username= ans_List.get(position).getAns_student_username() ;

        holder.setData(student_name,student_username) ;
    }

    @Override
    public int getItemCount() {
        return ans_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_student_name ;
        LinearLayout single_student_answer ;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            show_student_name = itemView.findViewById(R.id.student_answer_name) ;
            single_student_answer = itemView.findViewById(R.id.single_student_answer) ;
            //linearLayout = itemView.findViewById(R.id.single_student_answer) ;

        }

        public void setData(String student_name, String student_username) {
             System.out.println("student name"+ student_name);
             show_student_name.setText(student_name);

            @SuppressLint("RestrictedApi")
            SharedPreferences course_sp =getApplicationContext().getSharedPreferences("course_code_prefs",context.MODE_PRIVATE);
            String course_code = course_sp.getString("Teacher_Course_Code","");

            @SuppressLint("RestrictedApi")
            SharedPreferences assignment_topic_sp =getApplicationContext().getSharedPreferences("Assignment_topic_pref",context.MODE_PRIVATE);
            String assignment_topic = assignment_topic_sp.getString("Assignment_topic","");

            String merge = student_username+course_code+assignment_topic ;

             single_student_answer.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(context,teacher_answer_view.class);

                     intent.putExtra("merge_code",merge);
                     context.startActivity(intent);
                 }
             });

        }
    }
}
