package com.plantcontroller.serwer.controllers;

import com.plantcontroller.serwer.entities.PlantSensor;
import com.plantcontroller.serwer.repositories.PlantSensorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlantSensorController {
    private final PlantSensorRepository repository;

    PlantSensorController(PlantSensorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/plant_sensors")
    public List<PlantSensor> all() {
        return repository.findAll();
    }

    @PostMapping("/plant_sensors")
    public PlantSensor getNewPlantSensor(@RequestBody PlantSensor newPlantSensor) {
        return repository.save(newPlantSensor);
    }

    @GetMapping("/plant_sensors/{id}")
    public Optional<PlantSensor> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/plant_sensors/{id}")
    public PlantSensor replacePlantSensor(@RequestBody PlantSensor newPlantSensor, @PathVariable int id) {

        return repository.findById(id)
                .map(plantSensor -> {
                    plantSensor.setPlantSensorName(plantSensor.getPlantSensorName());
                    plantSensor.setUser(newPlantSensor.getUser());
                    return repository.save(plantSensor);
                })
                .orElseGet(() -> {
                    newPlantSensor.setId(id);
                    return repository.save(newPlantSensor);
                });
    }

    @DeleteMapping("/plant_sensors/{id}")
    public void deletePlantSensor(@PathVariable int id) {
        repository.deleteById(id);
    }
}
