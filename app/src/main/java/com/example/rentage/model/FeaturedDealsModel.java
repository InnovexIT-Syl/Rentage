package com.example.rentage.model;

public class FeaturedDealsModel {
    private String imageUrl;
    private String title;
    private String price;
    private String id;

    public FeaturedDealsModel(){

    }

    public FeaturedDealsModel(String imageUrl, String title, String price, String id) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
