package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor,Integer> {
}
