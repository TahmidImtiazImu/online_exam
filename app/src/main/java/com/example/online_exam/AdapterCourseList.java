package com.example.online_exam;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//git testing

public class AdapterCourseList extends RecyclerView.Adapter<AdapterCourseList.ViewHolder> {

    private final List<ModelCourseList>coursesList;

    public AdapterCourseList(List<ModelCourseList>coursesList) {

        this.coursesList=coursesList;
    }

    @NonNull
    @Override
    public AdapterCourseList.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCourseList.ViewHolder holder, int position) {

        String course_name = coursesList.get(position).getIndividual_course_Name();
        String course_code = coursesList.get(position).getIndividual_course_Code();

        holder.setData(course_name,course_code);

    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView Individual_course_Name;
        private final TextView Individual_course_Code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Individual_course_Name=itemView.findViewById(R.id.individual_course_Name);
            Individual_course_Code=itemView.findViewById(R.id.individual_course_code);

        }

        public void setData(String course_name, String course_code) {

            Individual_course_Name.setText(course_name);
            Individual_course_Code.setText(course_code);
        }
    }
}