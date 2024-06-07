package com.example.m295lb.services;

import com.example.m295lb.repositories.IOwnerRepository;
import com.example.m295lb.repositories.IPetRepository;
import com.example.m295lb.utils.exceptions.ResourceNotFoundException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/api")
public class PetController {

    Logger log = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private IPetRepository petRepository;
 // @Autowired
    private IOwnerRepository ownerRepository;

    // getting all pets
    @GET
    @Path("/getPets")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPets() throws ResourceNotFoundException {
        log.info("all the records of pets are fetched");
        var pets = petRepository.findAll();
        if (pets.isEmpty()) {
            log.error("No pets found");
            throw new ResourceNotFoundException("No pets found");
        }
        return Response.ok().entity(pets).build();
    }

    // get pet by id
    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        log.info("getById");
        var pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            log.error("Pet with this ID not found");
            throw new ResourceNotFoundException("Pet not found by id");
        }
        return Response.ok().entity(pet).build();
    }

    // Check if a record exists by ID
    @GET
    @Path("/exists/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response existsById(@PathParam("id") int id) throws ResourceNotFoundException {
        var pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new ResourceNotFoundException("Pet not found by id");
        }
        return Response.ok().entity(true).build();
    }

    // Read records based on a boolean filter (isAlive)
    @GET
    @Path("/isAlive/{isAlive}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetsByIsAlive(@PathParam("isAlive") boolean isAlive) {
        List<Pet> pets = petRepository.findPetByAlive(isAlive);
        if (pets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No pets found that are " + (isAlive ? "alive" : "not alive")).build();
        }
        return Response.ok().entity(pets).build();
    }

    // Read records based on a text filter (name)
    @GET
    @Path("/name/{name}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetsByName(@PathParam("name") String name) throws ResourceNotFoundException {
        List<Pet> pets = petRepository.findPetByName(name);
        if (pets.isEmpty()) {
            throw new ResourceNotFoundException("No pets found with name " + name);
        }
        return Response.ok().entity(pets).build();
    }

    // create new pet
    @POST
    @Path("/postPets")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPet(@Valid Pet pet) throws InternalServerErrorException {
        Pet savedPet = petRepository.save(pet);
        if (savedPet == null) {
            throw new InternalServerErrorException("Pet not created");
        }
        return Response.status(Response.Status.CREATED).entity(savedPet).build();
    }
    // create new pets in bulk
    @POST
    @Path("/postPetsBulk")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPets(@Valid List<Pet> pets) throws InternalServerErrorException {
        List<Pet> savedPets = new ArrayList<>();
        for (Pet pet : pets) {
            Pet savedPet = petRepository.save(pet);
            if (savedPet == null) {
                throw new InternalServerErrorException("Pet not created");
            }
            savedPets.add(savedPet);
        }
        return Response.status(Response.Status.CREATED).entity(savedPets).build();
    }

    // update pet
    @PUT
    @Path("/putPets/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePet(@PathParam("id") int id, @Valid Pet updatedPet) throws ResourceNotFoundException, InternalServerErrorException {
        var pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new ResourceNotFoundException("Pet not found by id");
        }
        updatedPet.setPetID(id);
        Pet savedPet = petRepository.save(updatedPet);
        if (savedPet == null) {
            throw new InternalServerErrorException("Pet not updated");
        }
        return Response.ok().entity("Updated pet " + id).build();
    }

    // delete pet by id
    @DELETE
    @Path("/deletePet/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePetById(@PathParam("id") int id) throws ResourceNotFoundException, InternalServerErrorException {
        var pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            log.error("Pet with id " + id + " does not exist");
            throw new ResourceNotFoundException("Pet with id " + id + " does not exist");
        }
        petRepository.deleteById(id);
        // Check if the pet is still present in the database
        if (petRepository.findById(id).isPresent()) {
            log.error("Pet with id " + id + " not deleted");
            throw new InternalServerErrorException("Pet not deleted");
        }
        log.info("Pet with id " + id + " has been deleted");
        return Response.ok().entity("Pet with id " + id + " has been deleted").build();
    }

    // delete all pets
    @DELETE
    @Path("/deletePets")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllPets() throws InternalServerErrorException {
        petRepository.deleteAll();
        if (!petRepository.findAll().isEmpty()) {
            throw new InternalServerErrorException("Not all pets deleted");
        }
        return Response.ok().entity("All pets have been deleted").build();
    }
    // delete pets by date
    @DELETE
    @Path("/deletePetsByDate/{date}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePetsByDate(@PathParam("date") String dateStr) {
        // Create a DateTimeFormatter with your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");

        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);

            // Call the repository method to delete the records
            int rowsAffected = petRepository.deletePetsByDate(date);
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("No pets found with date " + dateStr);
            }
            return Response.ok().entity("Pets with date " + dateStr + " have been deleted").build();
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Please use 'yyyy-M-dd'.");
        }
    }

}