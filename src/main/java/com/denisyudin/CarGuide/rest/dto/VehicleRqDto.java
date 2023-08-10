package com.denisyudin.CarGuide.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRqDto {
    private String make;
    private String model;
    private String categoryName;
    private String registrationNumber;
    private Long transportTypeId;
    private Integer manufactureYear;
    @Pattern(regexp = "^(true|false)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Неправильно указано значение")
    private Boolean isTrailerAvailable;
}
