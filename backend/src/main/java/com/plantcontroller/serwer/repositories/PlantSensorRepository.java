package com.plantcontroller.serwer.repositories;

import com.plantcontroller.serwer.entities.PlantSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantSensorRepository extends JpaRepository<PlantSensor,Integer> {
}
