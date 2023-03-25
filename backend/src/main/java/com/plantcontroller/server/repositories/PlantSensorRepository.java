package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.PlantSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantSensorRepository extends JpaRepository<PlantSensor,Integer> {
}
