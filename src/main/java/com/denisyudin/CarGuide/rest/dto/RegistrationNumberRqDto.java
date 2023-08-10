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
public class RegistrationNumberRqDto {
    @Nonnull
    private String make;
    @Nonnull
    private String model;
    @Nonnull
    private String category;
    @Nonnull
    private String registrationNumber;
    @Nonnull
    private String transportType;
    @Pattern(regexp = "^(true|false)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Неправильно указано значение")
    private String isTrailerAvailable;
}
