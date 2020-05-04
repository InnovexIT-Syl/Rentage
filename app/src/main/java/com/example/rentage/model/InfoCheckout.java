package com.example.rentage.model;

public class InfoCheckout {
    String id;
    String name;
    String address;
    String city;
    String country;
    String postal_code;
    String email_or_mobile;

    public InfoCheckout(String id, String name, String address, String city, String country, String postal_code, String email_or_mobile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.email_or_mobile = email_or_mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getEmail_or_mobile() {
        return email_or_mobile;
    }

    public void setEmail_or_mobile(String email_or_mobile) {
        this.email_or_mobile = email_or_mobile;
    }
}
