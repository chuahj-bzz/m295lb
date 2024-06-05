package com.example.modul295.util.handler;

import com.example.modul295.util.expecton.ModulNotCreatedException;
import com.example.modul295.util.expecton.ModulNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ModulNotCreatedExceptionHandler implements ExceptionMapper<ModulNotCreatedException> {

    @Override
    public Response toResponse(ModulNotCreatedException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
