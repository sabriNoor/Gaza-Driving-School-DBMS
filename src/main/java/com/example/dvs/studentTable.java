package com.example.dvs;

import java.util.Date;

public class studentTable {
    String SSN,Fname,Mname,Lname,phone,gender,email,password;
    Date birthdate;


    public studentTable(String SSN, String fname, String mname, String lname,String gender, Date birthdate, String phone, String email, String password) {
        this.SSN = SSN;
        Fname = fname;
        Mname = mname;
        Lname = lname;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}