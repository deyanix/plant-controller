package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.IMeasureGroup;
import com.plantcontroller.server.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "SELECT * FROM measurement WHERE DATE IN (SELECT MAX(DATE) FROM measurement WHERE plant_sensor_id = :sensorId)", nativeQuery = true)
    Measurement findLastByPlantSensor(int sensorId);

    @Query(value= """
            SELECT
                ROUND(
                    (AVG(m.value) / 
                    (SELECT max_value FROM sensor WHERE id = :sensorId)) 
                    * 100
                ) AS value,
                DATE_FORMAT(m.date, '%Y-%m-%dT%H:00:00') AS groupedDate
            FROM
                measurement AS m
            WHERE
                plant_sensor_id = :sensorId AND m.date >= DATE_SUB(NOW(), INTERVAL 1 WEEK)
            GROUP BY
                groupedDate
            ORDER BY
                groupedDate DESC;
            """,
            nativeQuery = true)
    List<IMeasureGroup> findAllById(int sensorId);
}
