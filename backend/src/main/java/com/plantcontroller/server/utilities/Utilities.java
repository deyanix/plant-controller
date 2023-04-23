package com.plantcontroller.server.utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utilities {
    public static double getPercentageValue(int value, int maxValue) {
        return Math.round(((double)value / (double)maxValue) * 100);
    }

    public static boolean isActive(LocalDateTime now, LocalDateTime lastMeasurement, int duration) {
        return Math.abs(ChronoUnit.SECONDS.between(now, lastMeasurement)) <= duration;
    }
}
