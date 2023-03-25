package com.plantcontroller.server.entities;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Tag(name = "Measurement")
public class Measurement {
    @Id @GeneratedValue
    private int id;
    private String date;
    private int value;
    @ManyToOne @JoinColumn(name="plantSensor.id")
    private PlantSensor plantSensor;
}