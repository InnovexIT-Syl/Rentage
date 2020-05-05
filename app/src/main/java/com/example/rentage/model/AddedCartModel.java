package com.example.rentage.model;

public class AddedCartModel {
    private String id;
    private int cartImage;
    private String cartModelName;
    private String cost;
    private String quantity;
    private String totalCost;

    public AddedCartModel(){

    }
    public AddedCartModel(String id, int cartImage, String cartModelName, String cost, String quantity, String totalCost) {
        this.id = id;
        this.cartImage = cartImage;
        this.cartModelName = cartModelName;
        this.cost = cost;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCartImage() {
        return cartImage;
    }

    public void setCartImage(int cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartModelName() {
        return cartModelName;
    }

    public void setCartModelName(String cartModelName) {
        this.cartModelName = cartModelName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
