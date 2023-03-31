package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "SELECT * FROM measurement WHERE DATE IN (SELECT MAX(DATE) FROM measurement WHERE plant_sensor_id = :sensor_id)",
            nativeQuery = true)
    Measurement findMeasurementByPlantSensor(@Param("sensor_id") int sensorId);
}