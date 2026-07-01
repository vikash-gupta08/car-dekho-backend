package com.car.dekho.model;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private double price;
    private String primaryUseCategory;
    private String safetyRating;
    private String fuelType;
    private double fuelEfficiency;
    private boolean luxuryFeatures;

    protected Car() {}

    public Car(String name, String type, double price, String primaryUseCategory,
               String safetyRating, String fuelType, double fuelEfficiency, boolean luxuryFeatures) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.primaryUseCategory = primaryUseCategory;
        this.safetyRating = safetyRating;
        this.fuelType = fuelType;
        this.fuelEfficiency = fuelEfficiency;
        this.luxuryFeatures = luxuryFeatures;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getPrimaryUseCategory() { return primaryUseCategory; }
    public String getSafetyRating() { return safetyRating; }
    public String getFuelType() { return fuelType; }
    public double getFuelEfficiency() { return fuelEfficiency; }
    public boolean isLuxuryFeatures() { return luxuryFeatures; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setPrimaryUseCategory(String primaryUseCategory) { this.primaryUseCategory = primaryUseCategory; }
    public void setSafetyRating(String safetyRating) { this.safetyRating = safetyRating; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setFuelEfficiency(double fuelEfficiency) { this.fuelEfficiency = fuelEfficiency; }
    public void setLuxuryFeatures(boolean luxuryFeatures) { this.luxuryFeatures = luxuryFeatures; }
}
