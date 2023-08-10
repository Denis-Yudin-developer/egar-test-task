package com.denisyudin.CarGuide.repository;

import com.denisyudin.CarGuide.entity.RegistrationNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationNumberRepository extends JpaRepository<RegistrationNumber, Long> {
    Optional<RegistrationNumber> findByRegistrationNumber(String registrationNumber);
}
