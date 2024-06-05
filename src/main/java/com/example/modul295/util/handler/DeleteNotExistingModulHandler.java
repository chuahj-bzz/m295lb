package com.example.modul295.util.handler;

import com.example.modul295.util.expecton.ModulNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DeleteNotExistingModulHandler implements ExceptionMapper<ModulNotFoundException> {
    @Override
    public Response toResponse(ModulNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
