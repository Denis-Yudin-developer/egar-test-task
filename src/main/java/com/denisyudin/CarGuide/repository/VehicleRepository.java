package com.denisyudin.CarGuide.repository;

import com.denisyudin.CarGuide.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
