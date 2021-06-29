package com.example.online_exam;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class teacher_adapter_assignmentlist extends RecyclerView.Adapter<teacher_adapter_assignmentlist.ViewHolder> {
    public List<teacher_model_assignmentlist> assignment_list ;
    public static  Context context ;
    DatabaseReference databaseReference ;
    public teacher_adapter_assignmentlist(List<teacher_model_assignmentlist>assignment_list,Context context) {
        this .assignment_list = assignment_list ;
        this.context = context ;

    }
    @SuppressLint("RestrictedApi")
    SharedPreferences course_sp = AuthUI.getApplicationContext().getSharedPreferences("course_code_prefs",context.MODE_PRIVATE);

    public  Boolean ui_deleted = false ;

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
        String course_code = course_sp.getString("Teacher_Course_Code","") ;
        String unique_assignment = course_code + assignment_topic ;

        holder.setData(assignment_topic,assignment_duration) ;
    }

    @Override
    public int getItemCount() {

        return assignment_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Teacher_assignment_topic ;
        private final TextView Teacher_assignment_duration ;
        private LinearLayout single_assignment ;
        ImageView edit_menu ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        Teacher_assignment_topic = itemView.findViewById(R.id.teacher_Assignment_topic_name) ;
        Teacher_assignment_duration = itemView.findViewById(R.id.teacher_assignment_duration_real) ;



        single_assignment = itemView.findViewById(R.id.teacher_single_assignment_display) ;
        }

        public void setData(String teacher_assignment_topic, String teacher_assignment_duration) {

              Teacher_assignment_topic.setText(teacher_assignment_topic);
              Teacher_assignment_duration.setText(teacher_assignment_duration);


            SharedPreferences sharedPreferences = context.getSharedPreferences("Assignment_topic_pref", context.MODE_PRIVATE);

            SharedPreferences.Editor  Assignment_topic_editor= sharedPreferences.edit();

                single_assignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Intent intent = new Intent(context,teacher_assignment_pageview.class);
                    Assignment_topic_editor.putString("Assignment_topic",teacher_assignment_topic);
                    Assignment_topic_editor.commit();

                    context.startActivity(new Intent(context,teacher_assignment_pageview.class));


                }
            });
        }
    }
    public void deleted(){

    }
    //coursecode+assignment1
}
