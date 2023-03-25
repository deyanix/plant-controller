package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Measurement {
    @Id @GeneratedValue
    private int id;
    private String measureDate;
    private int measureValue;
    @ManyToOne @JoinColumn(name="plantSensor.id")
    private PlantSensor plantSensor;
}