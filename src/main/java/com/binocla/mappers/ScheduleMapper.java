package com.binocla.mappers;

import com.binocla.entities.PlaceEntity;
import com.binocla.entities.ScheduleEntity;
import com.binocla.models.ScheduleRequestDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ScheduleMapper {

    public ScheduleEntity toEntity(ScheduleRequestDto scheduleRequestDto) {
        var scheduleEntity = new ScheduleEntity();
        updateEntityFromDto(scheduleRequestDto, scheduleEntity);
        return scheduleEntity;
    }

    public ScheduleRequestDto toDto(ScheduleEntity scheduleEntity) {
        var scheduleRequestDto = new ScheduleRequestDto();
        scheduleRequestDto.setPlaceId(scheduleEntity.getPlaceEntity().getId());
        scheduleRequestDto.setDockName(scheduleEntity.getDockName());
        scheduleRequestDto.setShipName(scheduleEntity.getShipName());
        scheduleRequestDto.setRouteName(scheduleEntity.getRouteName());
        scheduleRequestDto.setBerthPosition(scheduleEntity.getBerthPosition());
        scheduleRequestDto.setStartDateUtc(scheduleEntity.getStartDateUtc());
        scheduleRequestDto.setEndDateUtc(scheduleEntity.getEndDateUtc());
        scheduleRequestDto.setArrivalTimeMinutes(scheduleEntity.getArrivalTimeMinutes());
        scheduleRequestDto.setDepartureTimeMinutes(scheduleEntity.getDepartureTimeMinutes());
        scheduleRequestDto.setDockingDuration(scheduleEntity.getDockingDuration());
        scheduleRequestDto.setCanceledShipName(scheduleEntity.getCanceledShipName());
        scheduleRequestDto.setChangeType(scheduleEntity.getChangeType());
        scheduleRequestDto.setScheduleStartDate(scheduleEntity.getScheduleStartDate());
        scheduleRequestDto.setScheduleEndDate(scheduleEntity.getScheduleEndDate());
        scheduleRequestDto.setDockingArrival(scheduleEntity.getDockingArrival());
        scheduleRequestDto.setDeparture(scheduleEntity.getDeparture());
        return scheduleRequestDto;
    }

    public List<ScheduleRequestDto> toDtoList(List<ScheduleEntity> scheduleEntities) {
        var list = new ArrayList<ScheduleRequestDto>();
        for (var e : scheduleEntities) {
            list.add(toDto(e));
        }
        return list;
    }

    public void updateEntityFromDto(ScheduleRequestDto scheduleRequestDto, ScheduleEntity scheduleEntity) {
        scheduleEntity.setDockName(scheduleRequestDto.getDockName());
        scheduleEntity.setShipName(scheduleRequestDto.getShipName());
        scheduleEntity.setRouteName(scheduleRequestDto.getRouteName());
        scheduleEntity.setBerthPosition(scheduleRequestDto.getBerthPosition());
        scheduleEntity.setStartDateUtc(scheduleRequestDto.getStartDateUtc());
        scheduleEntity.setEndDateUtc(scheduleRequestDto.getEndDateUtc());
        scheduleEntity.setArrivalTimeMinutes(scheduleRequestDto.getArrivalTimeMinutes());
        scheduleEntity.setDepartureTimeMinutes(scheduleRequestDto.getDepartureTimeMinutes());
        scheduleEntity.setDockingDuration(scheduleRequestDto.getDockingDuration());
        scheduleEntity.setCanceledShipName(scheduleRequestDto.getCanceledShipName());
        scheduleEntity.setChangeType(scheduleRequestDto.getChangeType());
        scheduleEntity.setScheduleStartDate(scheduleRequestDto.getScheduleStartDate());
        scheduleEntity.setScheduleEndDate(scheduleRequestDto.getScheduleEndDate());
        scheduleEntity.setDockingArrival(scheduleRequestDto.getDockingArrival());
        scheduleEntity.setDeparture(scheduleRequestDto.getDeparture());
        PlaceEntity.findById(scheduleRequestDto.getPlaceId()).subscribe().with(x -> scheduleEntity.setPlaceEntity((PlaceEntity) x));
    }
}