package com.example.online_exam;

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

public class student_adapter_assignmentlist extends RecyclerView.Adapter<student_adapter_assignmentlist.ViewHolder> {

    private final List<student_model_assignmentlist> assignment_list ;
    public static Context context ;
    public student_adapter_assignmentlist(List<student_model_assignmentlist>assignment_list,Context context) {
        this .assignment_list = assignment_list ;
        this.context = context ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_assignment_layout,parent,false) ;
        return new student_adapter_assignmentlist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  student_adapter_assignmentlist.ViewHolder holder, int position) {
        String assignment_topic = assignment_list.get(position).getStudent_assignment_topic();
        String assignment_duration = assignment_list.get(position).getStudent_assignment_duration();
        String ques_url = assignment_list.get(position).getStudent_ques_url() ;
        holder.setData(assignment_topic,assignment_duration,ques_url) ;
    }

    @Override
    public int getItemCount() {
        return assignment_list.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Student_assignment_topic ;
        private LinearLayout single_assignment ;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            Student_assignment_topic = itemView.findViewById(R.id.student_assignment_topic_name) ;

            single_assignment = itemView.findViewById(R.id.student_single_assignment_display) ;
        }

        public void setData(String student_assignment_topic, String student_assignment_duration, String student_ques_url) {

            Student_assignment_topic.setText(student_assignment_topic);



            single_assignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,student_answer_submit_page.class) ;
                    intent.putExtra("Assignment_topic",student_assignment_topic) ;
                    intent.putExtra("Assignment_time",student_assignment_duration);
                    intent.putExtra("ques_url",student_ques_url) ;

                    context.startActivity(intent);
                }
            });
        }
    }
}
