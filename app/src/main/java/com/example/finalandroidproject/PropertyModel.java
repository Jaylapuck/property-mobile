package com.example.finalandroidproject;

public class PropertyModel {

    private int id;
    private String name;
    private String address;
    private String city;
    private String region;
    private String description;

    public PropertyModel(int id, String name, String address, String city, String region, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.region = region;
        this.description = description;
    }

    public PropertyModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;

    }
}
