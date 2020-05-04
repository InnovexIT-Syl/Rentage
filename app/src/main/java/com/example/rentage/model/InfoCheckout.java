package com.example.rentage.model;

public class InfoCheckout {
    String id;
    String name;
    String address;
    String orderByEmail;
    String orderByID;
    String postal_code;
    String email_or_mobile;

    public InfoCheckout(String id, String name, String address, String orderByEmail, String orderByID, String postal_code, String email_or_mobile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.orderByEmail = orderByEmail;
        this.orderByID = orderByID;
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

    public String getOrderByEmail() {
        return orderByEmail;
    }

    public void setOrderByEmail(String orderByEmail) {
        this.orderByEmail = orderByEmail;
    }

    public String getOrderByID() {
        return orderByID;
    }

    public void setOrderByID(String orderByID) {
        this.orderByID = orderByID;
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
