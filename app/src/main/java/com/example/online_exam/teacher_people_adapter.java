package com.example.online_exam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class teacher_people_adapter extends RecyclerView.Adapter<teacher_people_adapter.ViewHolder> {

    private final List<teacher_people_student_namelist> student_nameList;

    public teacher_people_adapter(List<teacher_people_student_namelist> student_nameList, teacher_assignment_people teacher_assignment_people) {

        this.student_nameList=student_nameList;

    }

    @NonNull
    @Override
    public teacher_people_adapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_assignment_sinlge_student_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  teacher_people_adapter.ViewHolder holder, int position) {
        String Student_name = student_nameList.get(position).getStudent_name();
        String Student_user_name= student_nameList.get(position).getStudent_user_name();

        holder.setData(Student_name,Student_user_name);
    }

    @Override
    public int getItemCount() {
        return student_nameList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView student_name;
        TextView student_count ;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.student_name) ;
            student_count = itemView.findViewById(R.id.student_count) ;
        }

        public void setData(String Student_name, String Student_user_name) {
            student_name.setText(Student_name);

        }
    }


}
