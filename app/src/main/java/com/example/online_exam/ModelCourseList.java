package com.example.online_exam;

public class ModelCourseList {

    private String individual_course_Name;
    private String individual_course_Code;

    public ModelCourseList() {

    }

    public ModelCourseList(String individual_course_Name, String individual_course_Code){

        this.individual_course_Name=individual_course_Name;
        this.individual_course_Code=individual_course_Code;
    }

    public String getIndividual_course_Name() {
        return individual_course_Name;
    }

    public void setIndividual_course_Name(String individual_course_Name) {
        this.individual_course_Name = individual_course_Name;
    }

    public String getIndividual_course_Code() {
        return individual_course_Code;
    }

    public void setIndividual_course_Code(String individual_course_Code) {
        this.individual_course_Code = individual_course_Code;
    }
}