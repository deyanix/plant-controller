package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="measurement")
public class Measurement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="value")
    private int value;

    @ManyToOne @JoinColumn(name="plant_sensor.id")
    private Sensor plantSensor;
}