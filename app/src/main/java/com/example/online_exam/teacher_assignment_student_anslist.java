package com.example.online_exam;

public class teacher_assignment_student_anslist {

    public teacher_assignment_student_anslist(String ans_student_name, String ans_student_username, String student_ans_count) {
        this.ans_student_name = ans_student_name;
        this.ans_student_username = ans_student_username;
        this.student_ans_count = student_ans_count;
    }

    String ans_student_name , ans_student_username,student_ans_count;

    public String getStudent_ans_count() {
        return student_ans_count;
    }

    public void setStudent_ans_count(String student_ans_count) {
        this.student_ans_count = student_ans_count;
    }

    public String getAns_student_name() {
        return ans_student_name;
    }

    public void setAns_student_name(String ans_student_name) {
        this.ans_student_name = ans_student_name;
    }

    public String getAns_student_username() {
        return ans_student_username;
    }

    public void setAns_student_username(String ans_student_username) {
        this.ans_student_username = ans_student_username;
    }



}
