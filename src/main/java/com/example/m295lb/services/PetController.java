package com.example.m295lb.services;

import com.example.m295lb.models.Pet;
import com.example.m295lb.repositorys.IPetRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Path("/api")
public class PetController {

    Logger log = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private IPetRepository petRepository;

    @GET
    @Path("/getPets")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPets() {
        log.info("getPets");
        try {
            var pets = petRepository.findAll();
            if (pets.isEmpty()) return Response.noContent().entity("No pets found").build();
            return Response.ok().entity(pets).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getPet/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPet(@PathParam("id") int id) {
        var pet = petRepository.findById(id);

        if (pet.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found by id").build();
        }

        return Response.ok().entity(pet).build();
    }

    @POST
    @Path("/postPets")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPet(@Validated Pet pet) {
        if (pet.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Pet format is not valid").build();
        }
        Pet savedPet = petRepository.save(pet);
        if (savedPet == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Pet not created").build();
        }
        return Response.status(Response.Status.CREATED).entity(savedPet).build();
    }

    @PUT
    @Path("/putPets/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePet(@PathParam("id") int id, @Valid Pet updatedPet) {
        try {
            updatedPet.setId_pet(id);
            petRepository.save(updatedPet);
            return Response.ok().entity("Updated pet " + id).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deletePets/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePet(@PathParam("id") int id) {
        var pet = petRepository.findById(id);

        if (pet.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet with id " + id + " does not exist").build();
        }

        petRepository.delete(pet.get());
        return Response.ok().build();
    }
}