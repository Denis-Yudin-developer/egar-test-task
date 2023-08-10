package com.denisyudin.CarGuide.service;

import com.denisyudin.CarGuide.entity.RegistrationNumber;
import lombok.NonNull;

public interface RegistrationNumberService {
    RegistrationNumber create(@NonNull String registrationNumber);
    void isAlreadyExists(String registrationNumber);
}
