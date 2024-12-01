package com.binocla.resources;

import com.binocla.models.PlaceRequestDto;
import com.binocla.services.PlaceService;
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

@Path("/api/v1/place")
@RolesAllowed({"HACK_ADMIN", "HACK_CLIENT", "HACK_DRIVER"})
public class PlaceResource {
    @Inject
    PlaceService placeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PlaceRequestDto>> getAllPlaces(
            @RestQuery @DefaultValue("0") int page,
            @RestQuery @DefaultValue("10") int size
    ) {
        return placeService.findAll(page, size);
    }

    @GET
    @Path(("/{id}"))
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PlaceRequestDto> getPlaceById(@RestPath Integer id) {
        return placeService.getPlaceById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<PlaceRequestDto> createPlace(PlaceRequestDto placeRequestDto) {
        return placeService.createPlace(placeRequestDto);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<PlaceRequestDto> updatePlaceById(@RestPath Long id, PlaceRequestDto placeRequestDto) {
        return placeService.updatePlaceById(id, placeRequestDto);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deletePlaceById(@RestPath Integer id) {
        return placeService.deletePlaceById(id);
    }
}