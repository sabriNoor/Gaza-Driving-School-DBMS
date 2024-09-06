package com.example.dvs;

import javafx.beans.binding.BooleanExpression;

public class viewFin {
    private Integer enrollmentId;
    private Integer lessonCount;
    private Double costPerLesson;
    private Double paymentAmount;
    private Double amountToPay;
    private String stdu_name;

    public String getStdu_name() {
        return stdu_name;
    }

    public void setStdu_name(String stdu_name) {
        this.stdu_name = stdu_name;
    }
// Add getters and setters here


    public viewFin(Integer enrollmentId, Integer lessonCount, Double costPerLesson, Double paymentAmount, Double amountToPay,String stdu_name) {
        this.enrollmentId = enrollmentId;
        this.lessonCount = lessonCount;
        this.costPerLesson = costPerLesson;
        this.paymentAmount = paymentAmount;
        this.amountToPay = amountToPay;
        this.stdu_name = stdu_name;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    public Double getCostPerLesson() {
        return costPerLesson;
    }

    public void setCostPerLesson(Double costPerLesson) {
        this.costPerLesson = costPerLesson;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }


}
