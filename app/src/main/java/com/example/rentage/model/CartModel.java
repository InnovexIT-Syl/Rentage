package com.example.rentage.model;

public class CartModel {
    private int cartImage;
    private String cartName;
    private String cartQuantity;

    public CartModel(int cartImage, String cartName, String cartQuantity) {
        this.cartImage = cartImage;
        this.cartName = cartName;
        this.cartQuantity = cartQuantity;
    }

    public int getCartImage() {
        return cartImage;
    }

    public void setCartImage(int cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
