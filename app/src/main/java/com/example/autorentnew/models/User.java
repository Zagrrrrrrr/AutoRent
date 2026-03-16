package com.example.autorentnew.models;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String licenseNumber;
    private String email;
    private String phone;

    public User(int id, String login, String password, String role,
                String firstName, String lastName, String middleName,
                String birthDate, String licenseNumber, String email, String phone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.licenseNumber = licenseNumber;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public String getBirthDate() { return birthDate; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}