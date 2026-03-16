package com.example.autorentnew.models;

public class CarStat {
    private String brand;
    private String model;
    private int bookingCount;

    public CarStat() {
    }

    public CarStat(String brand, String model, int bookingCount) {
        this.brand = brand;
        this.model = model;
        this.bookingCount = bookingCount;
    }

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getBookingCount() { return bookingCount; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setBookingCount(int bookingCount) { this.bookingCount = bookingCount; }

    public String getCarName() {
        return brand + " " + model;
    }
}