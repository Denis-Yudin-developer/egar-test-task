package com.denisyudin.CarGuide.service.impl;

import com.denisyudin.CarGuide.entity.Category;
import com.denisyudin.CarGuide.entity.RegistrationNumber;
import com.denisyudin.CarGuide.entity.TransportType;
import com.denisyudin.CarGuide.entity.Vehicle;
import com.denisyudin.CarGuide.mapper.VehicleMapper;
import com.denisyudin.CarGuide.repository.CategoryRepository;
import com.denisyudin.CarGuide.repository.RegistrationNumberRepository;
import com.denisyudin.CarGuide.repository.TransportTypeRepository;
import com.denisyudin.CarGuide.repository.VehicleRepository;
import com.denisyudin.CarGuide.rest.dto.VehicleRqDto;
import com.denisyudin.CarGuide.rest.dto.VehicleRsDto;
import com.denisyudin.CarGuide.rest.exceptions.BadRequestException;
import com.denisyudin.CarGuide.rest.exceptions.NotFoundException;
import com.denisyudin.CarGuide.service.CategoryService;
import com.denisyudin.CarGuide.service.RegistrationNumberService;
import com.denisyudin.CarGuide.service.TransportTypeService;
import com.denisyudin.CarGuide.service.VehicleService;
import com.denisyudin.CarGuide.utils.IdComparator;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final RegistrationNumberService registrationNumberService;
    private final TransportTypeService transportTypeService;
    private final CategoryService categoryService;
    private final String VEHICLE_NOT_FOUND = "Транспортное средство с id=%s не найдено";
    private final IdComparator idComparator;

    @Override
    public VehicleRsDto create(@NonNull  VehicleRqDto vehicleRqDto) {
        var transportTypeId = vehicleRqDto.getTransportTypeId();
        var registrationNumber = vehicleRqDto.getRegistrationNumber().trim();
        var categoryName = vehicleRqDto.getCategoryName().trim();

        TransportType foundTransportType;
        try {
            foundTransportType = transportTypeService.getById(transportTypeId);
            registrationNumberService.isAlreadyExists(registrationNumber);
        } catch (NotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        RegistrationNumber createdNumber = registrationNumberService.create(registrationNumber);

        Category createdCategory;
        if (categoryService.getByCategoryName(categoryName).isPresent()) {
            createdCategory = categoryService.getByCategoryName(categoryName).get();
        }
        else {
            createdCategory = categoryService.create(categoryName);
        }

        Vehicle toCreate = vehicleMapper.toEntity(vehicleRqDto);
        toCreate.setCategory(createdCategory);
        toCreate.setRegistrationNumber(createdNumber);
        toCreate.setTransportType(foundTransportType);

        Vehicle created = vehicleRepository.save(toCreate);
        return vehicleMapper.toDto(created);
    }

    @Override
    @Transactional
    public List<VehicleRsDto> getAll() {
        return vehicleRepository.findAll().stream().sorted(idComparator).map(vehicleMapper::toDto).toList();
    }

    @Override
    @Transactional
    public VehicleRsDto getById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(VEHICLE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public VehicleRsDto update(@NonNull Long id, @NonNull VehicleRqDto vehicleRqDto) {
        return vehicleRepository.findById(id)
                .map(src -> {
                    var newVehicle = vehicleMapper.toEntity(vehicleRqDto);
                    BeanUtils.copyProperties(newVehicle, src);
                    src.setId(id);
                    return src;
                })
                .map(vehicleMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(VEHICLE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public List<VehicleRsDto> searchVehicles(VehicleRqDto vehicleRqDto) {
        Vehicle searched = vehicleMapper.mapToSearchObject(vehicleRqDto);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withIgnoreCase(true)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Vehicle> example = Example.of(searched, exampleMatcher);
        return vehicleRepository.findAll(example).stream().sorted(idComparator).map(vehicleMapper::toDto).toList();
    }
}
