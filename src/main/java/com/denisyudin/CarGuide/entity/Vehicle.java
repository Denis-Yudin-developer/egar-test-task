package com.denisyudin.CarGuide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "make")
    private String make;
    @Column(name = "model")
    private String model;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @OneToOne
    @JoinColumn(name = "registration_number_id", referencedColumnName = "id")
    private RegistrationNumber registrationNumber;
    @ManyToOne
    @JoinColumn(name = "transport_type_id", referencedColumnName = "id")
    private TransportType transportType;
    @Column(name = "year")
    private Integer year;
    @Column(name = "trailer_available")
    private Boolean isTrailerAvailable;

    public Vehicle(Long id) {
        this.id = id;
    }
}
