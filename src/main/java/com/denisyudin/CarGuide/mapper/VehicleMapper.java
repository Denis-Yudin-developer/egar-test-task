package com.denisyudin.CarGuide.mapper;

import com.denisyudin.CarGuide.entity.Vehicle;
import com.denisyudin.CarGuide.repository.RegistrationNumberRepository;
import com.denisyudin.CarGuide.repository.TransportTypeRepository;
import com.denisyudin.CarGuide.rest.dto.VehicleRqDto;
import com.denisyudin.CarGuide.rest.dto.VehicleRsDto;
import com.denisyudin.CarGuide.service.CategoryService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehicleMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    private final RegistrationNumberRepository registrationNumberRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final CategoryService categoryService;
    private final HashMap<Boolean, String> trailerValueMap = new HashMap<>();


    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(VehicleRqDto.class, Vehicle.class)
                .addMappings(mapping -> mapping.skip(Vehicle::setId))
                .setPostConverter(toEntityPostConverter());
        modelMapper.createTypeMap(Vehicle.class, VehicleRsDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getCategory().getCategoryName(), VehicleRsDto::setCategoryName);
                    mapping.map(src -> src.getRegistrationNumber().getRegistrationNumber(), VehicleRsDto::setRegistrationNumber);
                    mapping.map(src -> src.getTransportType().getTypeName(), VehicleRsDto::setTypeName);
                })
                .setPostConverter(toDtoPostConverter());
    }

    public Vehicle toEntity(VehicleRqDto vehicleRqDto) {
        return modelMapper.map(vehicleRqDto, Vehicle.class);
    }

    public VehicleRsDto toDto(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleRsDto.class);
    }

    private Converter<VehicleRqDto, Vehicle> toEntityPostConverter() {
        return context -> {
            var vehicleSrc = context.getSource();
            var vehicleDst = context.getDestination();

            Optional.of(vehicleSrc.getRegistrationNumber().trim())
                    .flatMap(registrationNumberRepository::findByRegistrationNumber)
                    .ifPresent(vehicleDst::setRegistrationNumber);

            Optional.of(vehicleSrc.getTransportTypeId())
                    .flatMap(transportTypeRepository::findById)
                    .ifPresent(vehicleDst::setTransportType);

            if (!vehicleSrc.getCategoryName().isEmpty()) {
                var toFind = vehicleSrc.getCategoryName();
                categoryService.getByCategoryName(toFind).ifPresent(vehicleDst::setCategory);
            }

            return vehicleDst;
        };
    }

    private Converter<Vehicle, VehicleRsDto> toDtoPostConverter() {
        trailerValueMap.put(true, "Да");
        trailerValueMap.put(false, "Нет");
        return context -> {
            var vehicleSrc = context.getSource();
            var vehicleDst = context.getDestination();
            var trailerAvailableKey = vehicleSrc.getIsTrailerAvailable();

            if (trailerValueMap.containsKey(trailerAvailableKey)) {
                vehicleDst.setIsTrailerAvailable(trailerValueMap.get(trailerAvailableKey));
            }
            return vehicleDst;
        };
    }

    public Vehicle mapToSearchObject(VehicleRqDto vehicleRqDto) {
        Vehicle vehicle = changeEmptyStingToNull(vehicleRqDto);

        Optional.of(vehicleRqDto.getRegistrationNumber().trim())
                .flatMap(registrationNumberRepository::findByRegistrationNumber)
                .ifPresent(vehicle::setRegistrationNumber);

        Optional.ofNullable(vehicleRqDto.getTransportTypeId())
                .flatMap(transportTypeRepository::findById)
                .ifPresent(vehicle::setTransportType);

        Optional.ofNullable(vehicleRqDto.getManufactureYear())
                .ifPresent(vehicle::setYear);

        Optional.ofNullable(vehicleRqDto.getIsTrailerAvailable())
                .ifPresent(vehicle::setIsTrailerAvailable);

        return vehicle;
    }

    private Vehicle changeEmptyStingToNull(VehicleRqDto vehicleRqDto) {
        Vehicle mapped = new Vehicle();
        if (!vehicleRqDto.getMake().isEmpty()){
            mapped.setMake(vehicleRqDto.getMake());
        }
        if (!vehicleRqDto.getModel().isEmpty()) {
            mapped.setModel(vehicleRqDto.getModel());
        }
        if (!vehicleRqDto.getCategoryName().isEmpty()) {
            var toFind = vehicleRqDto.getCategoryName();
            categoryService.getByCategoryName(toFind).ifPresent(mapped::setCategory);
        }
        return mapped;
    }
}
