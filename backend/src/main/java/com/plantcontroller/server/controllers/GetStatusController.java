package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.Sensor;
import com.plantcontroller.server.entities.Status;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "GetSensorStatus")
@RestController
public class GetStatusController {

    SensorRepository sensorRepository;
    MeasurementRepository measurementRepository;

    public GetStatusController(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    private static double getPercentageValue(int value, int maxValue) {
        return (double)value / (double) maxValue;
    }

    @GetMapping("/sensors/{id}/status")
    public Status getStatus(@PathVariable int id){
        Status status = new Status();
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));
        Measurement measurement = measurementRepository.findLastDateBySensor(sensor);

        status.setSensorStatus(sensor.isStatus());
        status.setValue(getPercentageValue(measurement.getValue(),sensor.getMaxValue()));

        return status;
    }
}
