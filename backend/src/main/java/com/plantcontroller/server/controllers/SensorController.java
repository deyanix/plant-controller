package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.*;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.plantcontroller.server.utilities.Utilities;

@Tag(name="Sensor")
@RestController
public class SensorController {
    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;

    public SensorController(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
    }

    @PostMapping("/sensors/{id}/measurements")
    public Measurement saveMeasureValue(@RequestBody MeasurementValue newValue, @PathVariable int id) {
        Measurement measurement = new Measurement();
        measurement.setDate(LocalDateTime.now());
        measurement.setValue(newValue.getValue());
        measurement.setPlantSensor(sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id)));

        return measurementRepository.save(measurement);
    }

    @GetMapping("/sensors/{id}/status")
    public SensorState getStatus(@PathVariable int id){
        SensorState sensorState = new SensorState();
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));
        Measurement measurement = measurementRepository.findLastByPlantSensor(sensor.getId());

        sensorState.setActive(Utilities.isActive(LocalDateTime.now(),measurement.getDate(),sensor.getDuration()));
        sensorState.setHumidity(Utilities.getPercentageValue(measurement.getValue(),sensor.getMaxValue()));

        return sensorState;
    }

    @GetMapping("/sensors/{id}/measurements")
    @ResponseBody
    public List<IMeasureGroup> getAll(@PathVariable int id) {
        return measurementRepository.findAllById(id);
    }
}
