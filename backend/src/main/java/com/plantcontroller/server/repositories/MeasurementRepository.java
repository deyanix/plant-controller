package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.IMeasureGroup;
import com.plantcontroller.server.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "SELECT * FROM measurement WHERE DATE IN (SELECT MAX(DATE) FROM measurement WHERE plant_sensor_id = :sensorId)", nativeQuery = true)
    Measurement findLastBySensor(int sensorId);

    @Query(value= """
            SELECT
                ROUND(
                    (
                        GREATEST((AVG(m.value) - (SELECT min_value FROM sensor WHERE id = :sensorId)), 0) / 
                        (SELECT (max_value - min_value) FROM sensor WHERE id = :sensorId)
                    ) * 10000
                )/100 AS value,
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
    List<IMeasureGroup> findLastWeekBySensor(int sensorId);

    @Query(value= """
            SELECT
                ROUND(
                    (
                        GREATEST((AVG(m.value) - (SELECT min_value FROM sensor WHERE id = :sensorId)), 0) / 
                        (SELECT (max_value - min_value) FROM sensor WHERE id = :sensorId)
                    ) * 10000
                )/100 AS value,
                DATE_FORMAT(m.date, '%Y-%m-%dT%H:%i:%s') AS groupedDate
            FROM
                measurement AS m
            WHERE
                plant_sensor_id = :sensorId AND m.date >= DATE_SUB(NOW(), INTERVAL 1 HOUR)
            GROUP BY
                groupedDate
            ORDER BY
                groupedDate DESC;
            """,
            nativeQuery = true)
    List<IMeasureGroup> findLastHourBySensor(int sensorId);
}
