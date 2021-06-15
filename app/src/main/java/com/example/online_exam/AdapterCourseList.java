package com.example.online_exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


//git testing

public class AdapterCourseList extends RecyclerView.Adapter<AdapterCourseList.ViewHolder> {

    private final List<ModelCourseList>coursesList;
    private Context context;

    public AdapterCourseList(List<ModelCourseList>coursesList, Context context) {

        this.coursesList=coursesList;
        this.context=context;
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


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView Individual_course_Name;
        private final TextView Individual_course_Code;
        private LinearLayout single_course;
        private Color color;

        SharedPreferences sharedPreferences;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Individual_course_Name=itemView.findViewById(R.id.individual_course_Name);
            Individual_course_Code=itemView.findViewById(R.id.individual_course_code);

            single_course= itemView.findViewById(R.id.single_course_display);

        }

        public void setData(String course_name, String course_code) {

            Individual_course_Name.setText(course_name);
            Individual_course_Code.setText(course_code);

            sharedPreferences = context.getSharedPreferences("course_code_prefs", context.MODE_PRIVATE);

            SharedPreferences.Editor course_code_editor = sharedPreferences.edit();

            single_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //System.out.println(course_name+"  "+course_code);

                    //single_course.setBackgroundColor();

                    course_code_editor.putString("Teacher_Course_Code", course_code);
                    course_code_editor.commit();

                    course_code_editor.putString("Teacher_Course_Name",course_name);
                    course_code_editor.commit();

                    Toast.makeText(context, course_name+"  "+course_code, Toast.LENGTH_SHORT).show();

                    context.startActivity(new Intent(context,teacher_assignment_page.class));

                    //
                }
            });
        }

    }
}