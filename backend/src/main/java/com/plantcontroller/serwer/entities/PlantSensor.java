package com.plantcontroller.serwer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlantSensor {
    @Id @GeneratedValue
    private int Id;
    private String plantSensorName;
    @ManyToOne @JoinColumn(name="Id")
    private User user;
}
