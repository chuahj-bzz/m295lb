package com.example.modul295.service;

import com.example.modul295.repository.IModuleRepository;
import com.example.modul295.util.expecton.ModulNotCreatedException;
import com.example.modul295.util.expecton.ModulNotFoundException;
import com.example.modul295.util.expecton.FormatNotValidException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.modul295.model.Modul;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@Path("/api")
public class ModuleController {

    Logger log = LoggerFactory.getLogger(LoggingController.class);

    @Autowired
    private IModuleRepository moduleRepository;

    @GET
    @Path("/getModules")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModules() {
        log.info("getModules");
        try {
            var modules = moduleRepository.findAll();
            if (modules.isEmpty()) return Response.noContent().entity("No modules found").build();
            return Response.ok().entity(modules).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) throws ModulNotFoundException {
        log.warn("getById");
        log.error("getById-error");

            var module = moduleRepository.findById(id);

            if (module.isEmpty()) {
                throw new ModulNotFoundException("Modul not found by id");
            }


            return Response.ok().entity(module).build();

    }

    @POST
    @Path("/postModules")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addModule(@Validated Modul modul) throws ModulNotCreatedException, FormatNotValidException {
        if (modul.getName() == null) {
            throw new FormatNotValidException("Modul format is not valid");
        }
        Modul savedModul = moduleRepository.save(modul);
        if (savedModul == null) {
            log.info("1");
            throw new ModulNotCreatedException("Modul not created");
        }
        return Response.status(Response.Status.CREATED).entity(savedModul).build();

    }

    @PUT
    @Path("/putModules/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateModule(@PathParam("id") int id, @Valid Modul updatedModul) {
        try {
            updatedModul.setId(id);
            moduleRepository.save(updatedModul);
            return Response.ok().entity("Updated model " + id).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deleteModules/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModule(@PathParam("id") int id) throws ModulNotFoundException {
        Modul modul = moduleRepository.findById(id)
                .orElseThrow(() -> new ModulNotFoundException("Module with id " + id + " does not exist"));

        if (!modul.isActive()) {
            throw new ModulNotFoundException("Module with id " + id + " has already been deleted");
        }

        moduleRepository.delete(modul);
        return Response.ok().build();
    }

}




