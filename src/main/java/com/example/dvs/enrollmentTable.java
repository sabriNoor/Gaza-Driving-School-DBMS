package com.example.dvs;

import java.util.Date;

public class enrollmentTable {
    Integer enrollmentid;
    private String status,vehicleid,studentid,instructorid ;
    Date enrollmentdate,completiondate;

    Integer courseid;

    public enrollmentTable(Integer enrollmentid, String status, String vehicleid, String studentid, String instructorid, Date enrollmentdate, Date completiondate, Integer courseid) {
        this.enrollmentid = enrollmentid;
        this.status = status;
        this.vehicleid = vehicleid;
        this.studentid = studentid;
        this.instructorid = instructorid;
        this.enrollmentdate = enrollmentdate;
        this.completiondate = completiondate;
        this.courseid = courseid;
    }

    public Integer getEnrollmentid() {
        return enrollmentid;
    }

    public void setEnrollmentid(Integer enrollmentid) {
        this.enrollmentid = enrollmentid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(String instructorid) {
        this.instructorid = instructorid;
    }

    public Date getEnrollmentdate() {
        return enrollmentdate;
    }

    public void setEnrollmentdate(Date enrollmentdate) {
        this.enrollmentdate = enrollmentdate;
    }

    public Date getCompletiondate() {
        return completiondate;
    }

    public void setCompletiondate(Date completiondate) {
        this.completiondate = completiondate;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }
}



