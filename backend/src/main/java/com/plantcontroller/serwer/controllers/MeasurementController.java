package com.plantcontroller.serwer.controllers;

import com.plantcontroller.serwer.entities.Measurement;
import com.plantcontroller.serwer.repositories.MeasurementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MeasurementController {
    private final MeasurementRepository repository;

    MeasurementController(MeasurementRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/measurements")
    public List<Measurement> all() {
        return repository.findAll();
    }

    @PostMapping("/measurements")
    public Measurement getNewMeasurement(@RequestBody Measurement newMeasurement) {
        return repository.save(newMeasurement);
    }

    @GetMapping("/measurements/{id}")
    public Optional<Measurement> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/measurements/{id}")
    public Measurement replaceMeasurement(@RequestBody Measurement newMeasurement, @PathVariable int id) {

        return repository.findById(id)
                .map(measurement -> {
                    measurement.setMeasureDate(newMeasurement.getMeasureDate());
                    measurement.setMeasureValue(newMeasurement.getMeasureValue());
                    measurement.setPlantSensor(newMeasurement.getPlantSensor());
                    return repository.save(measurement);
                })
                .orElseGet(() -> {
                    newMeasurement.setMeasureId(id);
                    return repository.save(newMeasurement);
                });
    }

    @DeleteMapping("/measurements/{id}")
    public void deleteMeasurement(@PathVariable int id) {
        repository.deleteById(id);
    }
}
