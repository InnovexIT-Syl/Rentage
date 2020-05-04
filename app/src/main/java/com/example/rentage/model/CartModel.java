package com.example.rentage.model;

public class CartModel {
    String cartTitle;
    String cartImage;
    String cartQuantity;
    String id;
    String cartOrderByEmail;
    String cartOrderById;

    public CartModel(String cartTitle, String cartImage, String cartQuantity, String id, String cartOrderByEmail, String cartOrderById) {
        this.cartTitle = cartTitle;
        this.cartImage = cartImage;
        this.cartQuantity = cartQuantity;
        this.id = id;
        this.cartOrderByEmail = cartOrderByEmail;
        this.cartOrderById = cartOrderById;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
