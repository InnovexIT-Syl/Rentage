package com.example.rentage.model;

public class CartModel {
    String id;
    String cartTitle;
    String cartImage;
    String cartQuantity;
    String cartOrderByEmail;
    String cartOrderById;
    String singleCost;
    String totalCost;

    public CartModel(){

    }

    public CartModel(String id, String cartTitle, String cartImage, String cartQuantity, String cartOrderByEmail, String cartOrderById, String singleCost, String totalCost) {
        this.id = id;
        this.cartTitle = cartTitle;
        this.cartImage = cartImage;
        this.cartQuantity = cartQuantity;
        this.cartOrderByEmail = cartOrderByEmail;
        this.cartOrderById = cartOrderById;
        this.singleCost = singleCost;
        this.totalCost = totalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartOrderByEmail() {
        return cartOrderByEmail;
    }

    public void setCartOrderByEmail(String cartOrderByEmail) {
        this.cartOrderByEmail = cartOrderByEmail;
    }

    public String getCartOrderById() {
        return cartOrderById;
    }

    public void setCartOrderById(String cartOrderById) {
        this.cartOrderById = cartOrderById;
    }

    public String getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(String singleCost) {
        this.singleCost = singleCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
