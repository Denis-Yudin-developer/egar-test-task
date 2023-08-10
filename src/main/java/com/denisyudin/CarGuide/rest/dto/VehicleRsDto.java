package com.denisyudin.CarGuide.rest.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRsDto {
    @Nonnull
    private Long id;
    @Nonnull
    private String make;
    @Nonnull
    private String model;
    @Nonnull
    private String categoryName;
    @Nonnull
    private String registrationNumber;
    @Nonnull
    private String typeName;
    @Nonnull
    private Integer year;
    @Pattern(regexp = "^(true|false)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Неправильно указано значение")
    private String isTrailerAvailable;
}
