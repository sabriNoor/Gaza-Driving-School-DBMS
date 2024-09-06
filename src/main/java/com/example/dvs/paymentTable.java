package com.example.dvs;

import java.util.Date;

public class paymentTable {
    private Integer paymentid,enrollment_id;
    private Double amount;
    private Date paymentdate;
    private String paymentmethod,transactionid,stu_name;

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public paymentTable(Integer paymentid, Integer enrollment_id, Double amount, Date paymentdate, String paymentmethod, String transactionid, String stu_name) {
        this.paymentid = paymentid;
        this.enrollment_id = enrollment_id;
        this.amount = amount;
        this.paymentdate = paymentdate;
        this.paymentmethod = paymentmethod;
        this.transactionid = transactionid;
        this.stu_name=stu_name;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public Integer getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(Integer enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
}