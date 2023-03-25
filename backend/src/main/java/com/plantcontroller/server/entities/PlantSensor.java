package com.plantcontroller.server.entities;

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
    private int id;
    private String plantSensorName;
    @ManyToOne @JoinColumn(name="user.id")
    private User user;
}
