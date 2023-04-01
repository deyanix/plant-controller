package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "SELECT * FROM measurement WHERE DATE IN (SELECT MAX(DATE) FROM measurement WHERE plant_sensor_id = :sensorId)", nativeQuery = true)
    Measurement findLastByPlantSensor(int sensorId);
}