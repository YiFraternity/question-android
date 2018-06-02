package com.example.hsx.myapplication;

public class UserInfo {

    private String username;
    private String email;
    private String gender;
    private String grade;
    private String mobile;

    public UserInfo(String username,String email,String gender, String grade,String mobile){
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.grade = grade;
    }

    public String getUsername(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }
    public String getGender(){
        return this.gender;
    }
    public String getMobile(){
        return this.mobile;
    }
    public String getGrade(){
        return this.grade;
    }
}
