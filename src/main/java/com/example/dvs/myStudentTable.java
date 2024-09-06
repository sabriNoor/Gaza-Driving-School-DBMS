package com.example.dvs;

public class myStudentTable {
    int enrollid,courseid,countlesson;
    String ssn;
    String fname;
    String lname;
    String phone;
    String coursename;

    public myStudentTable(int enrollid, int courseid, String coursename, int countlesson, String ssn, String fname, String lname, String phone) {
        this.enrollid = enrollid;
        this.courseid = courseid;
        this.coursename = coursename;
        this.countlesson = countlesson;
        this.ssn = ssn;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public int getEnrollid() {
        return enrollid;
    }

    public void setEnrollid(int enrollid) {
        this.enrollid = enrollid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getCountlesson() {
        return countlesson;
    }

    public void setCountlesson(int countlesson) {
        this.countlesson = countlesson;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
