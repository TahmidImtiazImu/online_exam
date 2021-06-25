package com.example.online_exam;

public class student_model_assignmentlist {
    String student_assignment_topic ;
    String student_assignment_duration ;
    String student_ques_url ;

    public student_model_assignmentlist(String student_assignment_topic, String student_assignment_duration, String student_ques_url) {
        this.student_assignment_topic = student_assignment_topic;
        this.student_assignment_duration = student_assignment_duration;
        this.student_ques_url = student_ques_url;
    }

    public String getStudent_assignment_topic() {
        return student_assignment_topic;
    }

    public void setStudent_assignment_topic(String student_assignment_topic) {
        this.student_assignment_topic = student_assignment_topic;
    }

    public String getStudent_assignment_duration() {
        return student_assignment_duration;
    }

    public void setStudent_assignment_duration(String student_assignment_duration) {
        this.student_assignment_duration = student_assignment_duration;
    }

    public String getStudent_ques_url() {
        return student_ques_url;
    }

    public void setStudent_ques_url(String student_ques_url) {
        this.student_ques_url = student_ques_url;
    }
}
