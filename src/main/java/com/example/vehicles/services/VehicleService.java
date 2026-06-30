package com.example.vehicles.services;

import com.example.vehicles.dto.VehicleDTO;
import com.example.vehicles.entities.VehicleStatus;
import java.util.List;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    List<VehicleDTO> getAllVehicles();
    VehicleDTO getVehicleById(Long id);
    VehicleDTO updateVehicleStatus(Long id, VehicleStatus status);
    void deleteVehicle(Long id);
}