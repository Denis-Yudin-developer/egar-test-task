package com.denisyudin.CarGuide.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "registration_numbers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    public RegistrationNumber(Long id) {
        this.id = id;
    }

    public RegistrationNumber(String registrationNumber) {this.registrationNumber = registrationNumber;}
}
