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
}