package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.*;
import com.plantcontroller.server.errors.SensorNotFoundException;
import com.plantcontroller.server.repositories.MeasurementRepository;
import com.plantcontroller.server.repositories.SensorRepository;
import com.plantcontroller.server.services.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name="Sensor")
@RestController
public class SensorController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SensorRepository sensorRepository;
    private final SensorService sensorService;
    private final MeasurementRepository measurementRepository;

    public SensorController(SimpMessagingTemplate simpMessagingTemplate, SensorRepository sensorRepository, SensorService sensorService, MeasurementRepository measurementRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
        this.measurementRepository = measurementRepository;
    }

    @GetMapping("/sensors")
    public List<SensorInformation> getAllSensors() {
        List<Sensor> sensor = sensorRepository.findAll();
        List<SensorInformation> sensorInformation = new ArrayList<>();
        for (Sensor sensorElement : sensor) {
            sensorInformation.add(new SensorInformation(sensorElement.getId(), sensorElement.getDuration(), sensorElement.getMaxValue(), sensorElement.getName()));
        }
        return sensorInformation;
    }

    @PostMapping("/sensors/{id}/measurements")
    public Measurement saveMeasureValue(@RequestBody MeasurementValue newValue, @PathVariable int id) {
        Measurement measurement = new Measurement();
        measurement.setDate(LocalDateTime.now());
        measurement.setValue(newValue.getValue());
        measurement.setPlantSensor(sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id)));

        measurementRepository.save(measurement);
        simpMessagingTemplate.convertAndSend(
                String.format("/topic/sensors/%d/status", id),
                sensorService.getCurrentState(id));
        simpMessagingTemplate.convertAndSend(
                String.format("/topic/sensors/%d/history", id),
                measurementRepository.findLastHourBySensor(id));

        return measurement;
    }

    @GetMapping("/sensors/{id}/status")
    public SensorState getStatus(@PathVariable int id){
        return sensorService.getCurrentState(id);
    }

    @GetMapping("/sensors/{id}/measurements/last-week")
    @ResponseBody
    public List<IMeasureGroup> getAllFromLastWeek(@PathVariable int id) {
        return measurementRepository.findLastWeekBySensor(id);
    }

    @GetMapping("/sensors/{id}/measurements/last-hour")
    @ResponseBody
    public List<IMeasureGroup> getAllFromLastHour(@PathVariable int id) {
        return measurementRepository.findLastHourBySensor(id);
    }

    @MessageMapping("/sensors/{id}/status")
    public void getWsStatus(@DestinationVariable int id) {
        simpMessagingTemplate.convertAndSend(
                String.format("/topic/sensors/%d/status", id),
                sensorService.getCurrentState(id));
        simpMessagingTemplate.convertAndSend(
                String.format("/topic/sensors/%d/history", id),
                measurementRepository.findLastHourBySensor(id));
    }
}
