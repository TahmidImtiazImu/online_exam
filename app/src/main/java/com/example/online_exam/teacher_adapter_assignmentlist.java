package com.example.online_exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class teacher_adapter_assignmentlist extends RecyclerView.Adapter<teacher_adapter_assignmentlist.ViewHolder> {
    private final List<teacher_model_assignmentlist> assignment_list ;
    private  Context context ;
    public teacher_adapter_assignmentlist(List<teacher_model_assignmentlist>assignment_list,Context context) {
        this .assignment_list = assignment_list ;
        this.context = context ;
    }

    @NonNull
    @Override
    public teacher_adapter_assignmentlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_assignment_layout,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  teacher_adapter_assignmentlist.ViewHolder holder, int position) {

        String assignment_topic = assignment_list.get(position).getTeacher_assignment_topic();
        String assignment_duration = assignment_list.get(position).getTeacher_assignment_duration();

        holder.setData(assignment_topic,assignment_duration) ;
    }

    @Override
    public int getItemCount() {

        return assignment_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Teacher_assignment_topic ;
        private final TextView Teacher_assignment_duration ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        Teacher_assignment_topic = itemView.findViewById(R.id.teacher_Assignment_topic_name) ;
        Teacher_assignment_duration = itemView.findViewById(R.id.teacher_assignment_duration_real) ;
        }

        public void setData(String teacher_assignment_topic, String teacher_assignment_duration) {

              Teacher_assignment_topic.setText(teacher_assignment_topic);
              Teacher_assignment_duration.setText(teacher_assignment_duration);
        }
    }
    //coursecode+assignment1
}
