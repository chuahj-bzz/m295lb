package com.example.m295lb.services;

import com.example.m295lb.repositorys.IOwnerRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.m295lb.models.Owner;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/api")
public class OwnerController {

    Logger log = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    private IOwnerRepository ownerRepository;

    // getting all owners
    @GET
    @Path("/getOwners")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwners() {
        log.info("getOwners");
        try {
            var owners = ownerRepository.findAll();
            if (owners.isEmpty()) return Response.noContent().entity("No owners found").build();
            return Response.ok().entity(owners).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // get owner by id
    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        log.warn("getById");
        log.error("getById-error");

        var owner = ownerRepository.findById(id);

        if (owner.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found by id").build();
        }

        return Response.ok().entity(owner).build();
    }
    // Check if a record exists by ID
    @GET
    @Path("/exists/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response existsById(@PathParam("id") int id) {
        var owner = ownerRepository.findById(id);
        return Response.ok().entity(owner.isPresent()).build();
    }

    // Read records based on a boolean filter (isActive)
    @GET
    @Path("/isActive/{isActive}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnersByIsActive(@PathParam("isActive") boolean is_active) {
        List<Owner> owners = ownerRepository.findByActive(is_active);
        if (owners.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No owners found with isActive " + is_active).build();
        }
        return Response.ok().entity(owners).build();
    }

    // Read records based on a text filter (firstname)
    @GET
    @Path("/firstname/{firstname}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnersByFirstname(@PathParam("firstname") String firstname) {
        List<Owner> owners = ownerRepository.findByFirstname(firstname);
        if (owners.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No owners found with firstname " + firstname).build();
        }
        return Response.ok().entity(owners).build();
    }

    // create new owner
    @POST
    @Path("/postOwners")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOwner(@Valid Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        if (savedOwner == null) {
            log.info("1");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Owner not created").build();
        }
        return Response.status(Response.Status.CREATED).entity(savedOwner).build();
    }

    // update owner
    @PUT
    @Path("/putOwners/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOwner(@PathParam("id") int id, @Valid Owner updatedOwner) {
        try {
            updatedOwner.setId_owner(id);
            ownerRepository.save(updatedOwner);
            return Response.ok().entity("Updated owner " + id).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

   // delete owner by id
   @DELETE
   @Path("/deleteOwner/{id}")
   @RolesAllowed("ADMIN")
   @Produces(MediaType.APPLICATION_JSON)
   public Response deleteOwnerById(@PathParam("id") int id) {
       var owner = ownerRepository.findById(id);
       if (owner.isEmpty()) {
           return Response.status(Response.Status.NOT_FOUND).entity("Owner with id " + id + " does not exist").build();
       }
       ownerRepository.deleteById(id);
       return Response.ok().entity("Owner with id " + id + " has been deleted").build();
   }

    // delete all owners
    @DELETE
    @Path("/deleteOwners")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllOwners() {
        ownerRepository.deleteAll();
        return Response.ok().entity("All owners have been deleted").build();
    }


}