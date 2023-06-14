package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sensor")
public class Sensor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="duration")
    private int duration;

    @Column(name="name", length = 50)
    private String name;

    @Column(name="min_value")
    private int minValue;

    @Column(name="max_value")
    private int maxValue;

    @Column(name="preferred_value")
    private int preferredValue;

    @Column(name="inverted")
    private boolean inverted;

    @ManyToOne @JoinColumn(name="user.id")
    private User user;
}
