package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.Sensor;
import com.plantcontroller.server.repositories.SensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name="Sensor")
@RestController
public class SensorController {
    private final SensorRepository repository;

    public SensorController(SensorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/sensors")
    public List<Sensor> all() {
        return repository.findAll();
    }

    @PostMapping("/sensors")
    public Sensor create(@RequestBody Sensor newSensor) {
        return repository.save(newSensor);
    }

    @GetMapping("/sensors/{id}")
    public Optional<Sensor> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/sensors/{id}")
    public Sensor update(@RequestBody Sensor newSensor, @PathVariable int id) {

        return repository.findById(id)
                .map(plantSensor -> {
                    plantSensor.setName(plantSensor.getName());
                    plantSensor.setUser(newSensor.getUser());
                    return repository.save(plantSensor);
                })
                .orElseGet(() -> {
                    newSensor.setId(id);
                    return repository.save(newSensor);
                });
    }

    @DeleteMapping("/plant-sensors/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}
