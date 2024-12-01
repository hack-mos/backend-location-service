package com.binocla.services;

import com.binocla.entities.PlaceEntity;
import com.binocla.mappers.PlaceMapper;
import com.binocla.models.PlaceRequestDto;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@ApplicationScoped
public class PlaceService {
    @Inject
    PlaceMapper placeMapper;

    @WithTransaction
    public Uni<PlaceRequestDto> createPlace(PlaceRequestDto placeRequestDto) {
        return placeMapper.toEntity(placeRequestDto).persistAndFlush()
                .onItem()
                .transform(x -> placeMapper.toDto((PlaceEntity) x));
    }

    @WithTransaction
    public Uni<PlaceRequestDto> updatePlaceById(Long id, PlaceRequestDto placeRequestDto) {
        return PlaceEntity.findById(id)
                .onItem().ifNotNull().transformToUni(placeEntity -> {
                    placeMapper.updateEntityFromDto(placeRequestDto, (PlaceEntity) placeEntity);
                    return placeEntity.<PlaceEntity>persistAndFlush();
                })
                .onItem().ifNull().failWith(() -> new IllegalArgumentException("Place not found with id: " + id))
                .onItem()
                .transform(x -> placeMapper.toDto(x));
    }

    @WithTransaction
    public Uni<List<PlaceRequestDto>> findAll(int page, int size) {
        return PlaceEntity.<PlaceEntity>findAll().page(page, size).list()
                .onItem()
                .transform(x -> placeMapper.toDtoList(x));
    }

    @WithTransaction
    public Uni<PlaceRequestDto> getPlaceById(@RestPath Integer id) {
        return PlaceEntity.findById(id)
                .onItem()
                .transform(x -> placeMapper.toDto((PlaceEntity) x));
    }

    @WithTransaction
    public Uni<Boolean> deletePlaceById(Integer id) {
        return PlaceEntity.deleteById(id);
    }
}
