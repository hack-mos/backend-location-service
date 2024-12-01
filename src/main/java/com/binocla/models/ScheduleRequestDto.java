package com.binocla.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ScheduleRequestDto {
    private Integer placeId;
    private String dockName;
    private String shipName;
    private String routeName;
    private String berthPosition;
    private LocalDateTime startDateUtc;
    private LocalDateTime endDateUtc;
    private Integer arrivalTimeMinutes;
    private Integer departureTimeMinutes;
    private Integer dockingDuration;
    private String canceledShipName;
    private String changeType;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private LocalTime dockingArrival;
    private LocalTime departure;
}
