package com.example.online_exam;

public class reg_helper {

    String username,entername,email,gender,role,batch,year,semester ;
    public reg_helper(){}

    public reg_helper(String username, String entername, String email, String gender, String role, String batch, String year, String semester) {
        this.username = username;
        this.entername = entername;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.batch = batch;
        this.year = year;
        this.semester = semester;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }





}
