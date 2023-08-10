package com.denisyudin.CarGuide.utils;

import com.denisyudin.CarGuide.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
public class IdComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        return v1.getId().compareTo(v2.getId());
    }
}
