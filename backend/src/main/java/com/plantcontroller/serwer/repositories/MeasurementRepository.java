package com.plantcontroller.serwer.repositories;

import com.plantcontroller.serwer.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
}
