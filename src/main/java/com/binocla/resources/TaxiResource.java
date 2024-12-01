package com.binocla.resources;

import com.binocla.models.MinTimePlace;
import com.binocla.models.TaxiRequestDto;
import com.binocla.services.TaxiService;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
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
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/api/v1/taxi")
@RolesAllowed({"HACK_ADMIN"})
public class TaxiResource {
    @Inject
    TaxiService taxiService;
    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Uni<List<TaxiRequestDto>> getAllTaxis(
            @RestQuery @DefaultValue("0") int page,
            @RestQuery @DefaultValue("10") int size
    ) {
        return taxiService.findAll(page, size);
    }

    @GET
    @Path("/min")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Uni<MinTimePlace> getMinTimeForPlaces(@RestQuery Integer fromDock, @RestQuery Integer toDock) {
        return taxiService.minTimeForPlaces(fromDock, toDock);
    }

    @GET
    @Path(("/{id}"))
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TaxiRequestDto> getTaxiById(@RestPath Long id) {
        return taxiService.getTaxiById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"HACK_DRIVER", "HACK_ADMIN"})
    public Uni<TaxiRequestDto> createTaxi(TaxiRequestDto taxiRequestDto) {
        var driverName = securityIdentity.getPrincipal(DefaultJWTCallerPrincipal.class).getClaim(Claims.given_name) + " " + securityIdentity.getPrincipal(DefaultJWTCallerPrincipal.class).getClaim(Claims.family_name);
        return taxiService.createTaxi(taxiRequestDto, driverName);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"HACK_DRIVER", "HACK_ADMIN"})
    public Uni<TaxiRequestDto> updateTaxiById(@RestPath Long id, TaxiRequestDto taxiRequestDto) {
        var driverName = securityIdentity.getPrincipal(DefaultJWTCallerPrincipal.class).getClaim(Claims.given_name) + " " + securityIdentity.getPrincipal(DefaultJWTCallerPrincipal.class).getClaim(Claims.family_name);
        return taxiService.updateTaxiById(id, taxiRequestDto, driverName);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteTaxiById(@RestPath Long id) {
        return taxiService.deleteTaxiById(id);
    }
}
