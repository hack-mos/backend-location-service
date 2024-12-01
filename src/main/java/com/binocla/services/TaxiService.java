package com.binocla.services;

import com.binocla.entities.TaxiEntity;
import com.binocla.mappers.TaxiMapper;
import com.binocla.models.MinTimePlace;
import com.binocla.models.TaxiRequestDto;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestPath;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class TaxiService {
    @Inject
    TaxiMapper taxiMapper;

    @WithTransaction
    public Uni<TaxiRequestDto> createTaxi(TaxiRequestDto taxiRequestDto, String driver) {
        return taxiMapper.toEntity(taxiRequestDto, driver).<TaxiEntity>persistAndFlush()
                .onItem()
                .transform(x -> taxiMapper.toDto(x));
    }

    @WithTransaction
    public Uni<MinTimePlace> minTimeForPlaces(Integer fromDock, Integer toDock) {
        return Panache.getSession().onItem()
                .transformToUni(session -> session.createNativeQuery("""
                                    WITH dock_pairs AS (
                                        SELECT
                                            d1.id AS from_dock,
                                            d2.id AS to_dock
                                        FROM public.places d1
                                                 JOIN public.places d2
                                                      ON d1.id <> d2.id
                                    ),
                                    usage_stats AS (
                                        SELECT
                                            s.dock_id AS dock_id,
                                            s.berth_position,
                                            COUNT(*) AS usage_count
                                        FROM public.schedules s
                                        GROUP BY s.dock_id, s.berth_position
                                    ),
                                    combined_data AS (
                                        SELECT DISTINCT ON (dp.from_dock, dp.to_dock)
                                            dp.from_dock,
                                            dp.to_dock,
                                            s1.ship_name,
                                            AVG(EXTRACT(EPOCH FROM (s2.docking_arrival - s1.departure)) / 60) AS avg_travel_time_minutes,
                                            us_from.berth_position AS from_berth_position,
                                            us_from.usage_count AS from_usage_count,
                                            us_to.berth_position AS to_berth_position,
                                            us_to.usage_count AS to_usage_count
                                        FROM dock_pairs dp
                                                 JOIN public.schedules s1
                                                      ON s1.dock_id = dp.from_dock
                                                 JOIN public.schedules s2
                                                      ON s2.dock_id = dp.to_dock
                                                          AND s1.ship_name = s2.ship_name
                                                          AND s2.departure > s1.departure
                                                          AND DATE(s1.start_date_utc) = DATE(s2.start_date_utc)
                                                 LEFT JOIN usage_stats us_from
                                                      ON us_from.dock_id = dp.from_dock
                                                 LEFT JOIN usage_stats us_to
                                                      ON us_to.dock_id = dp.to_dock
                                        WHERE s1.ship_name IS NOT NULL
                                        GROUP BY dp.from_dock, dp.to_dock, s1.ship_name, us_from.berth_position, us_from.usage_count, us_to.berth_position, us_to.usage_count
                                    )
                                    SELECT
                                        from_dock,
                                        to_dock,
                                        ship_name,
                                        avg_travel_time_minutes,
                                        from_berth_position,
                                        from_usage_count,
                                        to_berth_position,
                                        to_usage_count
                                    FROM combined_data
                                    WHERE from_dock = :fromDock
                                      AND to_dock = :toDock
                                    ORDER BY from_dock, to_dock, avg_travel_time_minutes
                                    LIMIT 1;
                                """, Object[].class)
                        .setParameter("fromDock", fromDock)
                        .setParameter("toDock", toDock)
                        .getSingleResult()
                )
                .onItem().transform(result -> {
                    if (result == null) {
                        return null;
                    }

                    var row = result;
                    var minTimePlace = new MinTimePlace();
                    minTimePlace.setFromDock((Long) row[0]);
                    minTimePlace.setToDock((Long) row[1]);
                    minTimePlace.setMinutes(((BigDecimal) row[3]).doubleValue() * 0.7);
                    minTimePlace.setFromBerthPosition((String) row[4]);
                    minTimePlace.setFromUsageCount((Long) row[5]);
                    minTimePlace.setToBerthPosition((String) row[6]);
                    minTimePlace.setToUsageCount((Long) row[7]);
                    return minTimePlace;
                });
    }

    @WithTransaction
    public Uni<TaxiRequestDto> updateTaxiById(Long id, TaxiRequestDto taxiRequestDto, String driver) {
        return TaxiEntity.<TaxiEntity>findById(id)
                .onItem()
                .ifNotNull()
                .transformToUni(x -> taxiMapper.toEntity(taxiRequestDto, driver).<TaxiEntity>persistAndFlush())
                .onItem()
                .transform(x -> taxiMapper.toDto(x));
    }

    @WithTransaction
    public Uni<List<TaxiRequestDto>> findAll(int page, int size) {
        return TaxiEntity.<TaxiEntity>findAll().page(page, size).list()
                .onItem()
                .transform(x -> taxiMapper.toDtoList(x));
    }

    @WithTransaction
    public Uni<TaxiRequestDto> getTaxiById(@RestPath Long id) {
        return TaxiEntity.<TaxiEntity>findById(id)
                .onItem()
                .transform(x -> taxiMapper.toDto(x));
    }

    @WithTransaction
    public Uni<Boolean> deleteTaxiById(Long id) {
        return TaxiEntity.deleteById(id);
    }
}
