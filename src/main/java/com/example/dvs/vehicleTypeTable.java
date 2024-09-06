package com.example.dvs;

public class vehicleTypeTable {
    String type,thetype;
    Integer numvehicle,numCourse;

    public vehicleTypeTable(String type, String thetype, Integer numvehicle, Integer numCourse) {
        this.type = type;
        this.thetype = thetype;
        this.numvehicle = numvehicle;
        this.numCourse = numCourse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThetype() {
        return thetype;
    }

    public void setThetype(String thetype) {
        this.thetype = thetype;
    }

    public Integer getNumvehicle() {
        return numvehicle;
    }

    public void setNumvehicle(Integer numvehicle) {
        this.numvehicle = numvehicle;
    }

    public Integer getNumCourse() {
        return numCourse;
    }

    public void setNumCourse(Integer numCourse) {
        this.numCourse = numCourse;
    }
}
