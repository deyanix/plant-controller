package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "SELECT * FROM MEASUREMENT WHERE DATE IN (SELECT MAX(DATE) FROM MEASUREMENT WHERE PLANT_SENSOR_ID=1)", nativeQuery = true)
    Measurement findLastDateBySensor(Sensor sensor);
}