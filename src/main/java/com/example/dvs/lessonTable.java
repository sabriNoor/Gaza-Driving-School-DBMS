package com.example.dvs;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class lessonTable {
    private int lessonId,courseId,enrolId;
    private String vehicleId,issn,stud_ssn;
    private Date lessonDate;
    private Time lessonTime;


    public lessonTable(int lessonId, Date lessonDate,Time lessonTime,String issn, int courseId, String vehicleId,int enrolId,String stud_ssn) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.enrolId = enrolId;
        this.vehicleId = vehicleId;
        this.issn = issn;
        this.lessonDate = lessonDate;
        this.lessonTime = lessonTime;
        this.stud_ssn = stud_ssn;
    }
    public lessonTable(int lessonId, int courseId, String vehicleId, Date lessonDate, Time lessonTime) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.vehicleId = vehicleId;
        this.lessonDate = lessonDate;
        this.lessonTime = lessonTime;
    }


    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getEnrolId() {
        return enrolId;
    }

    public void setEnrolId(int enrolId) {
        this.enrolId = enrolId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Date lessonDate) {
        this.lessonDate = lessonDate;
    }

    public Time getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Time lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getStud_ssn() {
        return stud_ssn;
    }
}
