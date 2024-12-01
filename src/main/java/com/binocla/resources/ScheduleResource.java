package com.binocla.resources;

import com.binocla.models.ScheduleRequestDto;
import com.binocla.services.ScheduleService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/api/v1/schedules")
@RolesAllowed({"HACK_ADMIN"})
public class ScheduleResource {
    @Inject
    ScheduleService scheduleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<ScheduleRequestDto>> getAllSchedules(
            @RestQuery @DefaultValue("0") int page,
            @RestQuery @DefaultValue("10") int size
    ) {
        return scheduleService.findAll(page, size);
    }

    @GET
    @Path(("/{id}"))
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ScheduleRequestDto> getScheduleById(@RestPath Long id) {
        return scheduleService.getScheduleById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<ScheduleRequestDto> createSchedule(ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<ScheduleRequestDto> updateScheduleById(@RestPath Long id, ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.updateScheduleById(id, scheduleRequestDto);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteScheduleById(@RestPath Long id) {
        return scheduleService.deleteScheduleById(id);
    }
}
