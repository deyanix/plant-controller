package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "plant_sensor.id")
    private Sensor plantSensor;

    public double getPercentageValue() {
        double val = Math.max(getValue() - plantSensor.getMinValue(), 0);
        double percent = val / (plantSensor.getMaxValue() - plantSensor.getMinValue());

        return Math.round(percent * 10000) / 100d;
    }

    public boolean isActive(LocalDateTime date) {
        return Math.abs(ChronoUnit.SECONDS.between(date, getDate())) <= plantSensor.getDuration();
    }
}
