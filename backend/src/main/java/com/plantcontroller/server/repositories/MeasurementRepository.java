package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
}
