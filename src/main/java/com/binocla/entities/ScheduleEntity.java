package com.binocla.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@ToString
public class ScheduleEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "dock_id", referencedColumnName = "id")
    @ToString.Exclude
    private PlaceEntity placeEntity;
    @Column(name = "dock_name")
    private String dockName;
    @Column(name = "ship_name")
    private String shipName;
    @Column(name = "route_name")
    private String routeName;
    @Column(name = "berth_position")
    private String berthPosition;
    @Column(name = "start_date_utc")
    private LocalDateTime startDateUtc;
    @Column(name = "end_date_utc")
    private LocalDateTime endDateUtc;
    @Column(name = "arrival_time_minutes")
    private Integer arrivalTimeMinutes;
    @Column(name = "departure_time_minutes")
    private Integer departureTimeMinutes;
    @Column(name = "docking_duration")
    private Integer dockingDuration;
    @Column(name = "canceled_ship_name")
    private String canceledShipName;
    @Column(name = "change_type")
    private String changeType;
    @Column(name = "schedule_start_date")
    private LocalDate scheduleStartDate;
    @Column(name = "schedule_end_date")
    private LocalDate scheduleEndDate;
    @Column(name = "docking_arrival")
    private LocalTime dockingArrival;
    private LocalTime departure;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ScheduleEntity scheduleEntity = (ScheduleEntity) o;
        return id != null && Objects.equals(id, scheduleEntity.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
