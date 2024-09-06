package com.example.dvs;

public class courseTable {
    Integer id;
    private String name,vehicleType,duration,decription;
    Double cost_lesson;
    Integer number;

    public courseTable(Integer id, String name, String vehicleType, String duration, String decription, Double cost_lesson) {
        this.id = id;
        this.name = name;
        this.vehicleType = vehicleType;
        this.duration = duration;
        this.decription = decription;
        this.cost_lesson = cost_lesson;
    }

    public courseTable(Integer id, String name, String vehicleType, String duration, String decription, Double cost_lesson, Integer number) {
        this.id = id;
        this.name = name;
        this.vehicleType = vehicleType;
        this.duration = duration;
        this.decription = decription;
        this.cost_lesson = cost_lesson;
        this.number = number;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Double getCost_lesson() {
        return cost_lesson;
    }

    public void setCost_lesson(Double cost_lesson) {
        this.cost_lesson = cost_lesson;
    }
}
