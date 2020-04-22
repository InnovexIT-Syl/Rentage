package com.example.rentage.model;

public class FeaturedDealsModel {
    private int featuredImage;
    private String featuredName;
    private double featuredPrice;

    public FeaturedDealsModel(int featuredImage, String featuredName, double featuredPrice) {
        this.featuredImage = featuredImage;
        this.featuredName = featuredName;
        this.featuredPrice = featuredPrice;
    }

    public int getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(int featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedName() {
        return featuredName;
    }

    public void setFeaturedName(String featuredName) {
        this.featuredName = featuredName;
    }

    public double getFeaturedPrice() {
        return featuredPrice;
    }

    public void setFeaturedPrice(double featuredPrice) {
        this.featuredPrice = featuredPrice;
    }
}
