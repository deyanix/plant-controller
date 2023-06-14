package com.plantcontroller.server.services;

import com.plantcontroller.server.entities.Measurement;
import com.plantcontroller.server.entities.Sensor;
import com.plantcontroller.server.entities.SensorState;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SensorService {
	private final SensorRepository sensorRepository;
	private final MeasurementRepository measurementRepository;

	public SensorService(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
		this.sensorRepository = sensorRepository;
		this.measurementRepository = measurementRepository;
	}

	public SensorState getCurrentState(int id) {
		SensorState sensorState = new SensorState();
		Sensor sensor = sensorRepository.findById(id)
				.orElseThrow(() -> new SensorNotFoundException(id));
		Measurement measurement = measurementRepository.findLastBySensor(sensor.getId());
		measurement.setPlantSensor(sensor);
		sensorState.setActive(measurement.isActive(LocalDateTime.now()));
		sensorState.setHumidity(measurement.getPercentageValue());
		sensorState.setPreferred(measurement.isPreferred());
		return sensorState;
	}

}
