package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.MeasurementValue;
import com.plantcontroller.server.entities.Sensor;
import com.plantcontroller.server.entities.SensorState;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/sensors")
    public List<Sensor> all() {
        return sensorRepository.findAll();
    }

    @PostMapping("/sensors")
    public Sensor create(@RequestBody Sensor newSensor) {
        return sensorRepository.save(newSensor);
    }

    @GetMapping("/sensors/{id}")
    public Optional<Sensor> one(@PathVariable int id) {
        return sensorRepository.findById(id);
    }

    @PutMapping("/sensors/{id}")
    public Sensor update(@RequestBody Sensor newSensor, @PathVariable int id) {

        return sensorRepository.findById(id)
                .map(plantSensor -> {
                    plantSensor.setName(plantSensor.getName());
                    plantSensor.setUser(newSensor.getUser());
                    return sensorRepository.save(plantSensor);
                })
                .orElseGet(() -> {
                    newSensor.setId(id);
                    return sensorRepository.save(newSensor);
                });
    }

    @DeleteMapping("/plant-sensors/{id}")
    public void delete(@PathVariable int id) {
        sensorRepository.deleteById(id);
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
}
