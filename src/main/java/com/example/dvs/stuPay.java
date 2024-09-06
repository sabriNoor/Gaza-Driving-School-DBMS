package com.example.dvs;

import java.util.Date;

public class stuPay {

    Integer payId;
    Double amount;
    Date date;
    String tansId,method;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTansId() {
        return tansId;
    }

    public void setTansId(String tansId) {
        this.tansId = tansId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public stuPay(Integer payId, Double amount, Date date, String method, String tansId) {
        this.payId = payId;
        this.amount = amount;
        this.date = date;
        this.tansId = tansId;
        this.method = method;
    }
}
