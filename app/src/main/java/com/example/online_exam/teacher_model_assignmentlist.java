package com.example.online_exam;

public class teacher_model_assignmentlist {


    private  String teacher_assignment_topic;

    private  String teacher_assignment_duration ;

    public void teacher_assignment_add(){

    }
    public teacher_model_assignmentlist(String teacher_assignment_topic, String teacher_assignment_duration) {
        this.teacher_assignment_topic = teacher_assignment_topic;

        this.teacher_assignment_duration = teacher_assignment_duration;
    }

    public String getTeacher_assignment_topic() {
        return teacher_assignment_topic;
    }
    public void setTeacher_assignment_topic(String teacher_assignment_topic) {
        this.teacher_assignment_topic = teacher_assignment_topic ;
    }

    public String getTeacher_assignment_duration() {
        return teacher_assignment_duration;
    }
    public void setTeacher_assignment_duration(String teacher_assignment_duration) {
        this.teacher_assignment_duration = teacher_assignment_duration;
    }
}
