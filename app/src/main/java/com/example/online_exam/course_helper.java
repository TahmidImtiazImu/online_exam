package com.example.online_exam;

public class course_helper {

    private String courseName, courseCode, currentUser;

    public course_helper() {
    }
    public course_helper(String courseCode, String currentUser) {
        //this.courseName = courseName;
        this.courseCode = courseCode;
        this.currentUser = currentUser;
    }

    public course_helper(String courseName, String courseCode, String currentUser) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.currentUser = currentUser;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
}
