package com.example.vodafone.dto;

import java.util.Objects;

public class LocationRequestDto {

    private double latitude;
    private double longitude;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationRequestDto)) return false;
        LocationRequestDto that = (LocationRequestDto) o;
        return Double.compare(that.getLatitude(), getLatitude()) == 0 && Double.compare(that.getLongitude(), getLongitude()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "LocationRequestDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
