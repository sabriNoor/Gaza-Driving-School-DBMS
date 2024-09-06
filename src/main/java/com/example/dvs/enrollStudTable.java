package com.example.dvs;

import java.util.Date;

public class enrollStudTable {

    Integer enrollmentid;
    Double costPerLesson;

    public Double getCostPerLesson() {
        return costPerLesson;
    }

    public void setCostPerLesson(Double costPerLesson) {
        this.costPerLesson = costPerLesson;
    }

    Date enrollmentdate;
    String status,courseName,instructorName;

    public Integer getEnrollmentid() {
        return enrollmentid;
    }

    public void setEnrollmentid(Integer enrollmentid) {
        this.enrollmentid = enrollmentid;
    }

    public Date getEnrollmentdate() {
        return enrollmentdate;
    }

    public void setEnrollmentdate(Date enrollmentdate) {
        this.enrollmentdate = enrollmentdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public enrollStudTable(Integer enrollmentid, String status, String courseName, String instructorName, Date enrollmentdate,Double costPerLesson) {
        this.enrollmentid = enrollmentid;
        this.enrollmentdate = enrollmentdate;
        this.status = status;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.costPerLesson=costPerLesson;
    }
}
