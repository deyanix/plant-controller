package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.MeasurementValue;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "PostMeasurementValue")
@RestController
public class PostMeasurementValueController {

    SensorRepository sensorRepository;
    MeasurementRepository measurementRepository;

    PostMeasurementValueController(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
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
}
