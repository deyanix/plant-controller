package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.PlantSensor;
import com.plantcontroller.server.repositories.PlantSensorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name="PlantSensor")
@RestController
public class PlantSensorController {
    private final PlantSensorRepository repository;

    public PlantSensorController(PlantSensorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/plant-sensors")
    public List<PlantSensor> all() {
        return repository.findAll();
    }

    @PostMapping("/plant-sensors")
    public PlantSensor create(@RequestBody PlantSensor newPlantSensor) {
        return repository.save(newPlantSensor);
    }

    @GetMapping("/plant-sensors/{id}")
    public Optional<PlantSensor> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/plant-sensors/{id}")
    public PlantSensor update(@RequestBody PlantSensor newPlantSensor, @PathVariable int id) {

        return repository.findById(id)
                .map(plantSensor -> {
                    plantSensor.setName(plantSensor.getName());
                    plantSensor.setUser(newPlantSensor.getUser());
                    return repository.save(plantSensor);
                })
                .orElseGet(() -> {
                    newPlantSensor.setId(id);
                    return repository.save(newPlantSensor);
                });
    }

    @DeleteMapping("/plant-sensors/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}
