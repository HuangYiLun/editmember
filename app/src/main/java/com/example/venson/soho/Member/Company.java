package com.example.venson.soho.Member;

public class Company {
    private int companyId;
    private int uniformNumber;
    private String name;
    private double latitude;
    private double longitude;
    public Company(int companyId, int uniformNumber, String name, double latitude, double longitude) {
        super();
        this.companyId = companyId;
        this.uniformNumber = uniformNumber;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Company() {
    }

    public int getCompanyId() {
        return companyId;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public int getUniformNumber() {
        return uniformNumber;
    }
    public void setUniformNumber(int uniformNumber) {
        this.uniformNumber = uniformNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
