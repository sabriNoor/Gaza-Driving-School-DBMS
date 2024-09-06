package com.example.dvs;

import java.util.Date;

public class adminTable {
    String ssn,fname,lname,gender;
    Date dateofbirth;
    String phonenumber,email,password;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public adminTable(String ssn, String fname, String lname, String gender, Date dateofbirth, String phonenumber, String email, String password) {
        this.ssn = ssn;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
    }
}
