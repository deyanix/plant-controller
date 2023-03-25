package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.repositories.MeasurementRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Measurement")
@RestController
public class MeasurementController {
    private final MeasurementRepository repository;

    public MeasurementController(MeasurementRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/measurements")
    public List<Measurement> all() {
        return repository.findAll();
    }

    @PostMapping("/measurements")
    public Measurement create(@RequestBody Measurement newMeasurement) {
        return repository.save(newMeasurement);
    }

    @GetMapping("/measurements/{id}")
    public Optional<Measurement> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/measurements/{id}")
    public Measurement update(@RequestBody Measurement newMeasurement, @PathVariable int id) {

        return repository.findById(id)
                .map(measurement -> {
                    measurement.setDate(newMeasurement.getDate());
                    measurement.setValue(newMeasurement.getValue());
                    measurement.setPlantSensor(newMeasurement.getPlantSensor());
                    return repository.save(measurement);
                })
                .orElseGet(() -> {
                    newMeasurement.setId(id);
                    return repository.save(newMeasurement);
                });
    }

    @DeleteMapping("/measurements/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}
