package com.plantcontroller.server.errors;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(int id) {
        super("Could not find sensor " + id);
    }
}
