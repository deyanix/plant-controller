package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="measurement")
public class Measurement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date")
    private String date;

    @Column(name="value",precision = 5, scale = 2)
    private BigDecimal value;

    @ManyToOne @JoinColumn(name="plant_sensor.id")
    private PlantSensor plantSensor;
}