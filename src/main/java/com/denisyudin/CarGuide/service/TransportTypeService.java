package com.denisyudin.CarGuide.service;

import com.denisyudin.CarGuide.entity.TransportType;
import lombok.NonNull;

import java.util.List;

public interface TransportTypeService {
    List<TransportType> getAll();
    TransportType getById(@NonNull Long id);
}
