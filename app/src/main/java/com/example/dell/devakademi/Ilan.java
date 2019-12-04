package com.example.dell.devakademi;

import java.io.Serializable;

public class Ilan implements Serializable {

    private String id;
    private String title;
    private String description;
    private String city;
    private String town;

    public Ilan() {

    }

    public Ilan(String id, String title, String description, String city, String town) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.city = city;
        this.town = town;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
