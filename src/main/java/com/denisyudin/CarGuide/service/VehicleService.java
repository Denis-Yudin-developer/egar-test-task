package com.denisyudin.CarGuide.service;

import com.denisyudin.CarGuide.rest.dto.VehicleRqDto;
import com.denisyudin.CarGuide.rest.dto.VehicleRsDto;

import jakarta.annotation.Nonnull;

import java.util.List;

public interface VehicleService {
    VehicleRsDto create(@Nonnull VehicleRqDto vehicleRqDto);
    List<VehicleRsDto> getAll();
    VehicleRsDto getById(Long id);
    VehicleRsDto update(@Nonnull Long id, @Nonnull VehicleRqDto vehicleRqDto);
    List<VehicleRsDto> searchVehicles(VehicleRqDto vehicleRqDto);
}
