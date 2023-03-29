package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sensor")
public class Sensor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 50)
    private String name;

    @Column(name="max_value")
    private int maxValue;

    @ManyToOne @JoinColumn(name="user.id")
    private User user;
}
