package com.binocla.services;

import com.binocla.entities.ScheduleEntity;
import com.binocla.mappers.ScheduleMapper;
import com.binocla.models.ScheduleRequestDto;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@ApplicationScoped
public class ScheduleService {
    @Inject
    ScheduleMapper scheduleMapper;

    @WithTransaction
    public Uni<ScheduleRequestDto> createSchedule(ScheduleRequestDto scheduleRequestDto) {
        return scheduleMapper.toEntity(scheduleRequestDto).<ScheduleEntity>persistAndFlush()
                .onItem()
                .transform(x -> scheduleMapper.toDto(x));
    }

    @WithTransaction
    public Uni<ScheduleRequestDto> updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {
        return ScheduleEntity.findById(id)
                .onItem().ifNotNull().transformToUni(scheduleEntity -> {
                    scheduleMapper.updateEntityFromDto(scheduleRequestDto, (ScheduleEntity) scheduleEntity);
                    return scheduleEntity.<ScheduleEntity>persistAndFlush();
                })
                .onItem().ifNull().failWith(() -> new IllegalArgumentException("Place not found with id: " + id))
                .onItem()
                .transform(x -> scheduleMapper.toDto(x));
    }

    @WithTransaction
    public Uni<List<ScheduleRequestDto>> findAll(int page, int size) {
        return ScheduleEntity.<ScheduleEntity>findAll().page(page, size).list()
                .onItem()
                .transform(x -> scheduleMapper.toDtoList(x));
    }

    @WithTransaction
    public Uni<ScheduleRequestDto> getScheduleById(@RestPath Long id) {
        return ScheduleEntity.<ScheduleEntity>find("id", id).firstResult()
                .onItem()
                .transform(x -> scheduleMapper.toDto(x));
    }

    @WithTransaction
    public Uni<Boolean> deleteScheduleById(Long id) {
        return ScheduleEntity.deleteById(id);
    }
}
