package com.plantcontroller.server.entities;

import java.time.LocalDateTime;

public interface IMeasureGroup {
    LocalDateTime getGroupedDate();
    double getValue();
}
