package com.denisyudin.CarGuide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transport_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    public TransportType(Long id) {
        this.id = id;
    }
}
