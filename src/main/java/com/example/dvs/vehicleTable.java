package com.example.dvs;

import java.sql.Date;

public class vehicleTable {
   private String licenseplate;
    private String make;
    private String model;
    private String insurancepolicynumber;
    private String fule_type;
    private String transmission_type;
    private String covarage_type;

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    private String vtype;
    private int year;
    Double coverageamount;
   private Date effectivedate;

    public String getCovarage_type() {
        return covarage_type;
    }

    public void setCovarage_type(String covarage_type) {
        this.covarage_type = covarage_type;
    }

    public vehicleTable(String licenseplate, String make, String model,int year, String fule_type, String transmission_type,String insurancepolicynumber,String covarage_type,  Double  coverageamount, Date effectivedate,String vtype) {
        this.licenseplate = licenseplate;
        this.make = make;
        this.model = model;
        this.insurancepolicynumber = insurancepolicynumber;
        this.fule_type = fule_type;
        this.transmission_type = transmission_type;
        this.year = year;
        this.coverageamount = coverageamount;
        this.effectivedate = effectivedate;
        this.covarage_type=covarage_type;
        this.vtype=vtype;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInsurancepolicynumber() {
        return insurancepolicynumber;
    }

    public void setInsurancepolicynumber(String insurancepolicynumber) {
        this.insurancepolicynumber = insurancepolicynumber;
    }

    public String getFule_type() {
        return fule_type;
    }

    public void setFule_type(String fule_type) {
        this.fule_type = fule_type;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public void setTransmission_type(String transmission_type) {
        this.transmission_type = transmission_type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public  Double  getCoverageamount() {
        return coverageamount;
    }

    public void setCoverageamount( Double  coverageamount) {
        this.coverageamount = coverageamount;
    }

    public Date getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(Date effectivedate) {
        this.effectivedate = effectivedate;
    }
}
