package com.example.m295lb.services;

import com.example.m295lb.repositorys.IOwnerRepository;
import com.example.m295lb.repositorys.IPetRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.m295lb.models.Pet;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/api")
public class PetController {

    Logger log = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private IPetRepository petRepository;
 //   @Autowired
    private IOwnerRepository ownerRepository;

    // getting all pets
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

    // get pet by id
    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        log.warn("getById");
        log.error("getById-error");

        var pet = petRepository.findById(id);

        if (pet.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found by id").build();
        }

        return Response.ok().entity(pet).build();
    }

    // Check if a record exists by ID
    @GET
    @Path("/exists/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response existsById(@PathParam("id") int id) {
        var pet = petRepository.findById(id);
        return Response.ok().entity(pet.isPresent()).build();
    }

    // Read records based on a boolean filter (isAlive)
    @GET
    @Path("/isAlive")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetsByIsAlive() {
        List<Pet> pets = petRepository.findPetByAlive(true);
        if (pets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No pets found that are alive").build();
        }
        return Response.ok().entity(pets).build();
    }

    // Read records based on a text filter (name)
    @GET
    @Path("/name/{name}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetsByName(@PathParam("name") String name) {
        List<Pet> pets = petRepository.findPetByName(name);
        if (pets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No pets found with name " + name).build();
        }
        return Response.ok().entity(pets).build();
    }

    // create new pet
    @POST
    @Path("/postPets")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPet(@Valid Pet pet) {
        Pet savedPet = petRepository.save(pet);
        if (savedPet == null) {
            log.info("1");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Pet not created").build();
        }
        return Response.status(Response.Status.CREATED).entity(savedPet).build();
    }

    // update pet
    @PUT
    @Path("/putPets/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePet(@PathParam("id") int id, @Valid Pet updatedPet) {
        try {
            updatedPet.setPetID(id);
            petRepository.save(updatedPet);
            return Response.ok().entity("Updated pet " + id).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // delete pet by id
    @DELETE
    @Path("/deletePet/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePetById(@PathParam("id") int id) {
        var pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet with id " + id + " does not exist").build();
        }
        petRepository.deleteById(id);
        return Response.ok().entity("Pet with id " + id + " has been deleted").build();
    }

    // delete all pets
    @DELETE
    @Path("/deletePets")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllPets() {
        petRepository.deleteAll();
        return Response.ok().entity("All pets have been deleted").build();
    }

    @DELETE
    @Path("/deletePetsByDate/{date}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePetsByDate(@PathParam("date") String dateStr) {
        try {
            // Parse the date string into a LocalDate object
            LocalDate date = LocalDate.parse(dateStr);
            // Call the repository method to delete the records
            petRepository.deletePetsByDate(date);
            return Response.ok().entity("Pets with date " + dateStr + " have been deleted").build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}