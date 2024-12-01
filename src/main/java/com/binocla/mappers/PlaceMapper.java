package com.binocla.mappers;

import com.binocla.entities.PlaceEntity;
import com.binocla.models.PlaceRequestDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PlaceMapper {
    public PlaceEntity toEntity(PlaceRequestDto placeRequestDto) {
        var placeEntity = new PlaceEntity();
        placeEntity.setName(placeRequestDto.getName());
        placeEntity.setAddress(placeRequestDto.getAddress());
        placeEntity.setRiver(placeRequestDto.getRiver());
        placeEntity.setLatitude(placeRequestDto.getLatitude());
        placeEntity.setLongitude(placeRequestDto.getLongitude());
        return placeEntity;
    }

    public PlaceRequestDto toDto(PlaceEntity placeEntity) {
        var placeRequestDto = new PlaceRequestDto();
        placeRequestDto.setPlaceId(placeEntity.getId());
        placeRequestDto.setName(placeEntity.getName());
        placeRequestDto.setAddress(placeEntity.getAddress());
        placeRequestDto.setRiver(placeEntity.getRiver());
        placeRequestDto.setLatitude(placeEntity.getLatitude());
        placeRequestDto.setLongitude(placeEntity.getLongitude());
        return placeRequestDto;
    }

    public List<PlaceRequestDto> toDtoList(List<PlaceEntity> placeEntities) {
        var list = new ArrayList<PlaceRequestDto>();
        for (var entity : placeEntities) {
            list.add(toDto(entity));
        }
        return list;
    }

    public void updateEntityFromDto(PlaceRequestDto dto, PlaceEntity entity) {
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRiver(dto.getRiver());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
    }
}
