package com.example.vehicles.services;

import com.example.vehicles.dto.VehicleDTO;
import com.example.vehicles.entities.Vehicle;
import com.example.vehicles.entities.VehicleStatus;
import com.example.vehicles.mappers.VehicleMapper;
import com.example.vehicles.repositories.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        if (vehicleDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle payload is required");
        }

        if (vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A vehicle with this license plate already exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toDTO(savedVehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found with id: " + id));
        return vehicleMapper.toDTO(vehicle);
    }

    @Override
    public VehicleDTO updateVehicleStatus(Long id, VehicleStatus status) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        vehicle.setStatus(status);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(updatedVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        vehicleRepository.deleteById(id);
    }
}