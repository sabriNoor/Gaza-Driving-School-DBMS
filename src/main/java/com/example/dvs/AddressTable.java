package com.example.dvs;

public class AddressTable {

    String city,street,housebuilding,ssn;

    public AddressTable(String city, String street, String housebuilding) {
        this.city = city;
        this.street = street;
        this.housebuilding = housebuilding;
    }

    public AddressTable(String city, String street, String housebuilding, String ssn) {
        this.city = city;
        this.street = street;
        this.housebuilding = housebuilding;
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHousebuilding() {
        return housebuilding;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHousebuilding(String housebuilding) {
        this.housebuilding = housebuilding;
    }
}
