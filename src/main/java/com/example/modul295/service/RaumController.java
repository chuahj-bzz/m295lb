package com.example.modul295.service;

import com.example.modul295.repository.IRaumRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.modul295.model.Raum;
import org.springframework.validation.annotation.Validated;

@Component
@Path("/api/raum")
public class RaumController {

    Logger log = LoggerFactory.getLogger(RaumController.class);

    @Autowired
    private IRaumRepository raumRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getRooms() {
        log.info("getRooms");
        var rooms = raumRepository.findAll();
        if (rooms.isEmpty()) return Response.noContent().entity("No rooms found").build();
        return Response.ok().entity(rooms).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        log.warn("getById");
        log.error("getById-error");

        var raum = raumRepository.findById(id);

        if (raum.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Raum not found by id").build();
        }

        return Response.ok().entity(raum).build();
    }

    @POST
    @Path("/postRooms")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(@Validated Raum raum) {
        if (raum.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Raum format is not valid").build();
        }
        Raum savedRaum = raumRepository.save(raum);
        if (savedRaum == null) {
            log.info("1");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Raum not created").build();
        }
        return Response.status(Response.Status.CREATED).entity(savedRaum).build();
    }

    @PUT
    @Path("/putRooms/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateRoom(@PathParam("id") int id, @Valid Raum updatedRaum) {
        updatedRaum.setId(id);
        raumRepository.save(updatedRaum);
        return Response.ok().entity("Updated raum " + id).build();
    }

    @DELETE
    @Path("/deleteRooms/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") int id) {
        var raum = raumRepository.findById(id);

        if (raum.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Raum with id " + id + " does not exist").build();
        }

        raumRepository.delete(raum.get());
        return Response.ok().build();
    }
}