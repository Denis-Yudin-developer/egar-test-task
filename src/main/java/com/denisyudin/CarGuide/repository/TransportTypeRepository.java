package com.denisyudin.CarGuide.repository;

import com.denisyudin.CarGuide.entity.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long> {
}
