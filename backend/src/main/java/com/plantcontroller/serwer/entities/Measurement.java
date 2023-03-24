package com.plantcontroller.serwer.entities;

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
    private int measureId;
    private String measureDate;
    private int measureValue;
    @ManyToOne @JoinColumn(name="plantSensorId")
    private PlantSensor plantSensor;
}