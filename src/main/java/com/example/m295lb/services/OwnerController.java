package com.example.m295lb.services;

import com.example.m295lb.repositorys.IOwnerRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.m295lb.models.Owner;

@Component
@Path("/api/owner")
public class OwnerController {

    Logger log = LoggerFactory.getLogger(OwnerController.class);

  //  @Autowired
    private IOwnerRepository ownerRepository;

    // getting all owners
    @GET
    @Path("/getOwners")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwners() {
        var owners = ownerRepository.findAll();
        if (owners.isEmpty()) return Response.noContent().entity("No owners found").build();
        return Response.ok().entity(owners).build();
    }

    // get owner by id
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found by id").build();
        }
        return Response.ok().entity(owner).build();
    }

    // create new owner
    @POST
    @Path("/postOwners")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOwner(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        if (savedOwner == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Owner not created").build();
        }
        return Response.status(Response.Status.CREATED).entity(savedOwner).build();
    }

    // update owner
    @PUT
    @Path("/putOwners/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOwner(@PathParam("id") int id, Owner updatedOwner) {
        try {
            updatedOwner.setOwnerID(id);
            ownerRepository.save(updatedOwner);
            return Response.ok().entity("Updated owner " + id).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // delete owner by id
    @DELETE
    @Path("/deleteOwner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOwnerById(@PathParam("id") int id) {
        var owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner with id " + id + " does not exist").build();
        }
        ownerRepository.deleteById(id);
        return Response.ok().entity("Owner with id " + id + " has been deleted").build();
    }
}