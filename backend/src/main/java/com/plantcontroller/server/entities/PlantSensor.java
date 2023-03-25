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
@Tag(name="PlantSensor")
public class PlantSensor {
    @Id @GeneratedValue
    private int id;
    private String name;
    @ManyToOne @JoinColumn(name="user.id")
    private User user;
}
