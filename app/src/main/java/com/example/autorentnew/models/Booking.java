package com.example.autorentnew.models;



public class Booking {
    private int id;
    private int carId;
    private int userId;
    private String startDate;
    private String endDate;
    private double totalPrice;
    private String status;
    private String createdAt;
    private String carBrand;
    private String carModel;

    public Booking(int id, int carId, int userId, String startDate, String endDate,
                   double totalPrice, String status, String createdAt) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getCarId() { return carId; }
    public int getUserId() { return userId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public String getCarBrand() { return carBrand; }
    public String getCarModel() { return carModel; }

    public void setCarBrand(String carBrand) { this.carBrand = carBrand; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
}