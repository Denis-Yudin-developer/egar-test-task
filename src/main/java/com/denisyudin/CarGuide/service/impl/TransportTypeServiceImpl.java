package com.denisyudin.CarGuide.service.impl;

import com.denisyudin.CarGuide.entity.TransportType;
import com.denisyudin.CarGuide.repository.TransportTypeRepository;
import com.denisyudin.CarGuide.rest.exceptions.NotFoundException;
import com.denisyudin.CarGuide.service.TransportTypeService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportTypeServiceImpl implements TransportTypeService {
    private final TransportTypeRepository transportTypeRepository;
    private final String TRANSPORT_TYPE_NOT_FOUND = "Тип транспортного средства с id=%s не найден";
    @Override
    public List<TransportType> getAll() {
        return transportTypeRepository.findAll();
    }

    @Override
    @Transactional
    public TransportType getById(@NonNull Long id) {
        return transportTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(TRANSPORT_TYPE_NOT_FOUND, id)));
    }
}
