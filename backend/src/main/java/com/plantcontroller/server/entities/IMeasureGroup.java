package com.plantcontroller.server.entities;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface IMeasureGroup {
    @Value("#{target.groupedDate}")
    LocalDateTime getDate();
    double getValue();
}
