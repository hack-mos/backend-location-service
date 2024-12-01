package com.binocla.mappers;

import com.binocla.entities.TaxiEntity;
import com.binocla.models.TaxiRequestDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TaxiMapper {
    public TaxiEntity toEntity(TaxiRequestDto taxiRequestDto, String driver) {
        var taxiEntity = new TaxiEntity();
        taxiEntity.setName(taxiRequestDto.getName());
        taxiEntity.setCapacity(taxiRequestDto.getCapacity());
        taxiEntity.setMinutesToCharge(taxiRequestDto.getMinutesToCharge());
        taxiEntity.setBattery(taxiRequestDto.getBattery());
        taxiEntity.setDriver(driver);
        return taxiEntity;
    }

    public TaxiRequestDto toDto(TaxiEntity taxiEntity) {
        var taxiRequestDto = new TaxiRequestDto();
        taxiRequestDto.setName(taxiEntity.getName());
        taxiRequestDto.setCapacity(taxiEntity.getCapacity());
        taxiRequestDto.setMinutesToCharge(taxiEntity.getMinutesToCharge());
        taxiRequestDto.setBattery(taxiEntity.getBattery());
        taxiRequestDto.setDriver(taxiEntity.getDriver());
        taxiRequestDto.setId(taxiEntity.id);
        return taxiRequestDto;
    }

    public List<TaxiRequestDto> toDtoList(List<TaxiEntity> taxiEntities) {
        var list = new ArrayList<TaxiRequestDto>();
        for (var e : taxiEntities) {
            list.add(toDto(e));
        }
        return list;
    }
}
