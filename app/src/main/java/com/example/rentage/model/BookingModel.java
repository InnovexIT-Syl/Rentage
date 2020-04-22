package com.example.rentage.model;

public class BookingModel {
    private String id;
    private int bookingImage ;
    private int bookingTitle;
    private int bookingDeails;

    public BookingModel(String id, int bookingImage, int bookingTitle, int bookingDeails) {
        this.id = id;
        this.bookingImage = bookingImage;
        this.bookingTitle = bookingTitle;
        this.bookingDeails = bookingDeails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBookingImage() {
        return bookingImage;
    }

    public void setBookingImage(int bookingImage) {
        this.bookingImage = bookingImage;
    }

    public int getBookingTitle() {
        return bookingTitle;
    }

    public void setBookingTitle(int bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public int getBookingDeails() {
        return bookingDeails;
    }

    public void setBookingDeails(int bookingDeails) {
        this.bookingDeails = bookingDeails;
    }
}
