package com.example.dvs;

import java.util.Date;

public class costTable {
    private Integer costid;
    private Date date;
    private String description,vehicle_id,instructor_id,costtype;
    private Double amount;

    public Integer getCostid() {
        return costid;
    }

    public void setCostid(Integer costid) {
        this.costid = costid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(String instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getCosttype() {
        return costtype;
    }

    public void setCosttype(String costtype) {
        this.costtype = costtype;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public costTable(Integer costid,String costtype,String description, Double amount, String vehicle_id, String instructor_id, Date date) {
        this.costid = costid;
        this.date = date;
        this.description = description;
        this.vehicle_id = vehicle_id;
        this.instructor_id = instructor_id;
        this.costtype = costtype;
        this.amount = amount;
    }
}
