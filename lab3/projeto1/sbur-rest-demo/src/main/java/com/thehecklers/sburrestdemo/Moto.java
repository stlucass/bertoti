package com.thehecklers.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Moto {
    @Id
    private String id;
    private String brand;
    private String model;
    private int year;
    private int displacement;
    private String color;
    private double price;
    private String category;

    public Moto() {
    }

    @JsonCreator
    public Moto(@JsonProperty("id") String id,
                @JsonProperty("brand") String brand,
                @JsonProperty("model") String model,
                @JsonProperty("year") int year,
                @JsonProperty("displacement") int displacement,
                @JsonProperty("color") String color,
                @JsonProperty("price") double price,
                @JsonProperty("category") String category) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.displacement = displacement;
        this.color = color;
        this.price = price;
        this.category = category;
    }

    public Moto(String brand, String model, int year, int displacement, String color, double price, String category) {
        this(UUID.randomUUID().toString(), brand, model, year, displacement, color, price, category);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
