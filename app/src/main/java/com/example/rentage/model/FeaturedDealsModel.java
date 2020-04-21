package com.example.rentage.model;

public class FeaturedDealsModel {
    private String featuredImage;
    private String featuredName;
    private String featuredPrice;

    public FeaturedDealsModel(String featuredImage, String featuredName, String featuredPrice) {
        this.featuredImage = featuredImage;
        this.featuredName = featuredName;
        this.featuredPrice = featuredPrice;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedName() {
        return featuredName;
    }

    public void setFeaturedName(String featuredName) {
        this.featuredName = featuredName;
    }

    public String getFeaturedPrice() {
        return featuredPrice;
    }

    public void setFeaturedPrice(String featuredPrice) {
        this.featuredPrice = featuredPrice;
    }
}
