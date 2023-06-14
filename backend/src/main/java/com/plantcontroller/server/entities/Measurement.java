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

    public boolean isPreferred() {
        return plantSensor.isInverted() ?
                plantSensor.getPreferredValue() >= value :
                plantSensor.getPreferredValue() <= value;
    }

    public double getPercentageValue() {
        double val = Math.max(getValue() - plantSensor.getMinValue(), 0);
        double percent = val / (plantSensor.getMaxValue() - plantSensor.getMinValue());
        double result = Math.round(percent * 10000) / 100d;
        return plantSensor.isInverted() ? 100 - result : result;
    }

    public boolean isActive(LocalDateTime date) {
        return Math.abs(ChronoUnit.SECONDS.between(date, getDate())) <= plantSensor.getDuration();
    }
}
