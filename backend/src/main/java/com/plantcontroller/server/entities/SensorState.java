package com.plantcontroller.server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorState {
    private boolean active;
    private double humidity;
    private boolean preferred;
}
