package com.example.autorentnew.models;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double engineVolume;
    private String engineType;
    private double pricePerHour;
    private double pricePerDay;
    private double pricePerWeek;
    private boolean isAvailable;
    private String imageUrl;

    // Constructor
    public Car(int id, String brand, String model, int year, double engineVolume,
               String engineType, double pricePerHour, double pricePerDay,
               double pricePerWeek, boolean isAvailable, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engineVolume = engineVolume;
        this.engineType = engineType;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.pricePerWeek = pricePerWeek;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getEngineVolume() { return engineVolume; }
    public String getEngineType() { return engineType; }
    public double getPricePerHour() { return pricePerHour; }
    public double getPricePerDay() { return pricePerDay; }
    public double getPricePerWeek() { return pricePerWeek; }
    public boolean isAvailable() { return isAvailable; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setEngineVolume(double engineVolume) { this.engineVolume = engineVolume; }
    public void setEngineType(String engineType) { this.engineType = engineType; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public void setPricePerWeek(double pricePerWeek) { this.pricePerWeek = pricePerWeek; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}