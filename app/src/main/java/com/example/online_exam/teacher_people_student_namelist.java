package com.example.online_exam;

public class teacher_people_student_namelist {
    String student_name ;
    String student_user_name ;
    String name_count;

    public teacher_people_student_namelist(String student_name, String student_user_name, String name_count) {
        this.student_name = student_name;
        this.student_user_name = student_user_name;
        this.name_count = name_count;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_user_name() {
        return student_user_name;
    }

    public void setStudent_user_name(String student_user_name) {
        this.student_user_name = student_user_name;
    }

    public String getName_count() {
        return name_count;
    }

    public void setName_count(String name_count) {
        this.name_count = name_count;
    }








}
