package com.denisyudin.CarGuide.service.impl;

import com.denisyudin.CarGuide.entity.RegistrationNumber;
import com.denisyudin.CarGuide.repository.RegistrationNumberRepository;
import com.denisyudin.CarGuide.rest.exceptions.BadRequestException;
import com.denisyudin.CarGuide.service.RegistrationNumberService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationNumberServiceImpl implements RegistrationNumberService {
    private final RegistrationNumberRepository registrationNumberRepository;
    private final String REGISTRATION_NUMBER_ALREADY_EXISTED = "Данный регистрационный знак уже существует";

    @Override
    @Transactional
    public RegistrationNumber create(@NonNull String registrationNumber) {
        isAlreadyExists(registrationNumber);
        return registrationNumberRepository.save(new RegistrationNumber(registrationNumber));
    }
    @Override
    public void isAlreadyExists(String registrationNumber) {
        Optional.of(registrationNumber)
                .flatMap(registrationNumberRepository::findByRegistrationNumber)
                .ifPresent(found -> {
                    throw new BadRequestException(REGISTRATION_NUMBER_ALREADY_EXISTED);
                });
    }
}
