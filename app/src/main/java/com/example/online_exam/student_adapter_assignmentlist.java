package com.example.online_exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class student_adapter_assignmentlist extends RecyclerView.Adapter<student_adapter_assignmentlist.ViewHolder> {

    private final List<student_model_assignmentlist> assignment_list ;
    public static Context context ;
    public  String Teacher_name;
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
        String course_code = assignment_list.get(position).getStudent_course_code();

        holder.setData(assignment_topic,assignment_duration,ques_url,course_code) ;
    }

    @Override
    public int getItemCount() {
        return assignment_list.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Student_assignment_topic ;
        private final TextView teacher_name ;
        private LinearLayout single_assignment ;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            Student_assignment_topic = itemView.findViewById(R.id.student_assignment_topic_name) ;
            teacher_name = itemView.findViewById(R.id.course_teacher_name) ;

            single_assignment = itemView.findViewById(R.id.student_single_assignment_display) ;
        }

        public void setData(String student_assignment_topic, String student_assignment_duration, String student_ques_url,String course_code) {


           DatabaseReference databaseReference;
           databaseReference = FirebaseDatabase.getInstance().getReference("courses").child(course_code);
           databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                   String Teacher_name= snapshot.child("teacherEnter_name").getValue().toString() ;
                   System.out.println("teacher name"+Teacher_name);
                   teacher_name.setText(Teacher_name);
               }

               @Override
               public void onCancelled(@NonNull @NotNull DatabaseError error) {

               }
           });
           String unique_assignment = course_code + student_assignment_topic;
           DatabaseReference databaseReference1 ;
           databaseReference1 = FirebaseDatabase.getInstance().getReference("teacher_uploadPdf").child(unique_assignment);
           databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
               @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
               @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
               @Override
               public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                   String due_date = Objects.requireNonNull(snapshot.child("Due_date").getValue()).toString() ;
                   String due_time = Objects.requireNonNull(snapshot.child("Due_time").getValue()).toString() ;
                   Date date1 = null;
                   Date date2 = null;
                   Date time1 = null;
                   Date time2 = null;
                   Date today_date = new Date();
                   @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                   String date = format.format(today_date);
                   System.out.println(date);

                   @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                   String time = sdf.format(new Date());

                   @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy") ;
                   @SuppressLint("SimpleDateFormat") SimpleDateFormat format2 = new SimpleDateFormat("HH:mm") ;

                   try {
                       time1 = format2.parse(time) ;
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   try {
                       time2 = format2.parse(due_time) ;
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   Log.d("TAG","Due date" + due_time) ;
                   Log.d("TAG","real date" + time) ;

                   try {
                       date1 = format1.parse(date) ;
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   try {
                       date2 = format1.parse(due_date);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }

                   assert date1 != null;
                   if(date1.compareTo(date2) < 0)
                   {
                       single_assignment.setBackgroundColor(Color.parseColor("#0000FF"));
                   }
                   else if(date1.compareTo(date2)==0){
                       assert time1 != null;
                       if(time1.compareTo(time2)<=0) {
                           single_assignment.setBackgroundColor(Color.parseColor("#0000FF"));

                       }
                   }
               }

               @Override
               public void onCancelled(@NonNull @NotNull DatabaseError error) {

               }
           });

            //teacher_name.setText(course_code);
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
