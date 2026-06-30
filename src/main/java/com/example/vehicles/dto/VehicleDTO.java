package com.example.vehicles.dto;

import com.example.vehicles.entities.VehicleStatus;
import jakarta.validation.constraints.NotBlank;

public class VehicleDTO {
    private Long id;
    @NotBlank(message = "brand is required")
    private String brand;
    @NotBlank(message = "model is required")
    private String model;
    @NotBlank(message = "licensePlate is required")
    private String licensePlate;
    private VehicleStatus status;

    public VehicleDTO() {}

    public VehicleDTO(Long id, String brand, String model, String licensePlate, VehicleStatus status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
}