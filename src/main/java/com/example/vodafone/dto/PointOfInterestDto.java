package com.example.vodafone.dto;

public class PointOfInterestDto {

    private String name;
    private double latitude;
    private double longitude;
    private int requestCount;

    public PointOfInterestDto() {}

    public PointOfInterestDto(String name, double latitude, double longitude, int requestCount) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestCount = requestCount;
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

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

}

