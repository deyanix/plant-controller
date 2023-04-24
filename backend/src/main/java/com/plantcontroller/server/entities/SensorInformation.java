package com.plantcontroller.server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorInformation {
    private int id;
    private int duration;
    private int maxValue;
    private String name;
}
