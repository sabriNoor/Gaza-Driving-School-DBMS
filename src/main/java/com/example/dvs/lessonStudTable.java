package com.example.dvs;

import java.sql.Time;
import java.util.Date;

public class lessonStudTable {

    Integer lessonid;
    Date lessondate;
    Time starttime;

    public Integer getLessonid() {
        return lessonid;
    }

    public void setLessonid(Integer lessonid) {
        this.lessonid = lessonid;
    }

    public Date getLessondate() {
        return lessondate;
    }

    public void setLessondate(Date lessondate) {
        this.lessondate = lessondate;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public lessonStudTable(Integer lessonid, Date lessondate, Time starttime) {
        this.lessonid = lessonid;
        this.lessondate = lessondate;
        this.starttime = starttime;
    }
}
