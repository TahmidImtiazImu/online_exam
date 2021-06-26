package com.example.online_exam;

public class student_helper {

    private String courseCode;
    private String currentUser;
    private String student_name;

    public student_helper(String courseCode, String currentUser, String student_name) {
        this.courseCode = courseCode;
        this.currentUser = currentUser;
        this.student_name = student_name;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }


}
